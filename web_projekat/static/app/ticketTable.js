Vue.component("ticket-table",{
    data: function (){
        return{
            main: false,
            tablePage: 1,
            tableFilter: {
                originCity: 0,
                destCity: 0,
                departDate: new Date(),
                returnDate: new Date(),
                way: "all",
                active: false
            },
            user: "",
            userId: "",
            cityList: null,
            table: null,
            date1: "",
            date2: "",
            way: "all"
        }
    },
    methods:{
        filterTable: function (){
            if(Date.parse(this.date1)-Date.parse(this.date2)>=0) {
                alert("Return date must be after departure date!")
                return
            }
            this.tableFilter.active = true
            if(this.date1!=="")
                this.tableFilter.departDate = this.date1
            this.tableFilter.returnDate = (this.date2==="" || this.tableFilter.way==="oneWay")?new Date():this.date2
            axios
                .post('/rest/ticket/filterTable',this.tableFilter)
            axios
                .get('/rest/ticket/getFilteredTable/'+1)
                .then(response => this.table = response.data)
        },
        filterWay: function (){
            if(this.tableFilter.way!==this.way){
                this.tableFilter.way = this.way
                axios
                    .post('/rest/ticket/filterTable',this.tableFilter)
                axios
                    .get('/rest/ticket/getFilteredTable/'+1)
                    .then(response => this.table = response.data)
            }
        },
        clearSearch: function (){
            this.tablePage = 1
            this.date1 = ""
            this.date2 = ""
            this.way = "all"
            this.tableFilter = {
                originCity: 0,
                destCity: 0,
                departDate: new Date(),
                returnDate: new Date(),
                way: "all",
                active: false
            }
            axios
                .post('/rest/ticket/filterTable',this.tableFilter)
            axios
                .get('/rest/ticket/getFilteredTable/'+1)
                .then(response => this.table = response.data)
        },
        nextPage: function (){
            this.tablePage++
            axios
                .get('/rest/ticket/getFilteredTable/'+this.tablePage)
                .then(response => (this.table = response.data))
        },
        prevPage: function (){
            this.tablePage--
            axios
                .get('/rest/ticket/getFilteredTable/'+this.tablePage)
                .then(response => (this.table = response.data))
        },
        company: function(company_id){
            router.push({path: `/airline/${(company_id)}`})
        },
        editTicket: function (ticket_id){
            router.push({ path: `/ticket/${ticket_id}` })
        },
        bookTicket: function(ticket){
            let new_reservation = Object.assign({}, ticket);
            new_reservation.departDate = new Date()
            if(!new_reservation.oneWay) new_reservation.returnDate = new Date()
            axios
                .post('/rest/reservation/create/'+this.userId,new_reservation)
                .then(response => console.log(response.data))
        },
        deleteTicket: function (ticket_id){
            axios
                .delete('/rest/ticket/delete/'+ticket_id)
                .then(response => console.log(response.data))
            location.reload()
        },
        checkLast: function (){
            if(this.table===null)   return true
            return this.table.length<5
        }
    },
    mounted() {
        if(this.$route.path==='/')
            this.main = true
        if(localStorage.getItem('user_id')!=null){
            this.userId = localStorage.getItem('user_id');
        }
        if(localStorage.getItem('user')!=null){
            this.user = localStorage.getItem('user')
        }
        axios
            .post('/rest/ticket/filterTable',this.tableFilter)
        axios
            .get('/rest/ticket/table/'+this.tablePage)
            .then(response => (this.table = response.data))
        axios
            .get('/rest/city/getAll')
            .then(response => (this.cityList=response.data))
    },
    template:`
<div>
    <div v-if="this.main">
    <table v-if="this.user==='user'">
    <tr><th>Origin</th><th>Destination</th><th>Departure</th><th>Return</th></tr>
    <tr>
        <td>
            <select name="origin" v-model="tableFilter.originCity">
                <option v-for="c in cityList" :value="c.id">{{c.name}}</option>
                <option value="0" selected>All</option>
            </select>
        </td>    
        <td>
             <select name="dest" v-model="tableFilter.destCity">
                <option v-for="c in cityList" :value="c.id">{{c.name}}</option>
                <option value="0" selected>All</option>
            </select>
        </td>
        <td>
            <input type="date" name="departure" v-model="date1">
        </td>    
        <td>
            <input type="date" name="return" v-model="date2" :disabled="tableFilter.way==='oneWay'">
        </td>
        <td>
            <button v-on:click="filterTable()">Search</button>
        </td>    
        <td>
            <button v-on:click="clearSearch()">Clear</button>
        </td>
    </tr>
    </table>
    <label for="filter">Filter tickets:</label>
    <select name="filter" v-model="way" v-on:click="filterWay()">
        <option value="all" selected="selected">All tickets</option>
        <option value="oneWay">One way</option>
        <option value="round">Round trip</option>
    </select>
    </div>
    <table border="1">
    <tr>
        <td>One-way</td>
        <td>Origin</td>
        <td>Destination</td>
        <td>Depart</td>
        <td>Return</td>
        <td>Company</td>
        <td>Count</td>
    </tr>
    <tbody v-if="this.user==='admin'">
        <tr v-for="t in table">
        <td v-if="t.oneWay">Yes</td>
        <td v-else>No</td>
        <td>{{t.origin.name}}</td>
        <td>{{t.destination.name}}</td>
        <td >{{t.departDate}}</td>
        <td>{{t.returnDate}}</td>
        <td v-on:click="company(t.company.id)">{{t.company.name}}</td>
        <td>{{t.count}}</td>
        <td v-on:click="deleteTicket(t.ticketId)">Delete</td>
        <td v-on:click="editTicket(t.ticketId)">Edit</td>
    </tr>
    </tbody>
    <tbody v-else>
        <tr v-for="t in table">
        <td v-if="t.oneWay">Yes</td>
        <td v-else>No</td>
        <td>{{t.origin.name}}</td>
        <td>{{t.destination.name}}</td>
        <td>{{t.departDate}}</td>
        <td>{{t.returnDate}}</td>
        <td v-on:click="company(t.company.id)">{{t.company.name}}</td>
        <td>{{t.count}}</td>
        <td v-on:click="bookTicket(t)">Book</td>
        </tr>
    </tbody>
    <button v-on:click="prevPage()" :disabled="tablePage===1"> < </button>
    <label>{{tablePage}}</label>
    <button v-on:click="nextPage()" :disabled="checkLast()"> > </button>
    </table>
     </div>
    `
});