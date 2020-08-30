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
            filterWay: "all",
            origin: "",
            dest: "",
            departDate: null,
            returnDate: null,
            cityList: null,
            table: null,
            tableSize: null,
            date1: "",
            date2: "",
            backupTable: [],
            backed: false,
            userFilter: false
        }
    },
    methods:{
        filterTable: function (){
            this.tableFilter.active = true
            if(this.date1!=="")
                this.tableFilter.departDate = this.date1
            this.tableFilter.returnDate = (this.date2==="" || this.tableFilter.way==="oneWay")?new Date():this.date2
            console.log(this.tableFilter)
            axios
                .post('/rest/ticket/filterTable',this.tableFilter)
            axios
                .get('/rest/ticket/getFilteredTable')
                .then(response => console.log(response.data))
        },
        clearSearch: function (){
            this.tablePage = 1
            this.date1 = ""
            this.date2 = ""
            this.tableFilter = {
                originCity: 0,
                destCity: 0,
                departDate: new Date(),
                returnDate: new Date(),
                active: false
            }
            axios
                .get('/rest/ticket/table/'+this.tablePage)
                .then(response => this.table = response.data)
        },
        reset: function (){
            if(!this.backed){
                this.backupTable = [...this.table]
                this.backed = true
            }
            this.table = [...this.backupTable]
        },
        search: function (){
            this.userFilter = true
            this.reset()

            if(Date.parse(this.departDate)-Date.parse(this.returnDate)>=0) {
                console.log("NOPE MATORI")
                return
            }

            if(this.origin!=""){
                this.table = this.table.filter(ticket => ticket.origin.id===this.origin);
            }
            if(this.dest!="") {
                this.table = this.table.filter(ticket => ticket.destination.id === this.dest);
            }
            if(this.departDate!=null){
                this.table = this.table.filter(ticket =>
                    Date.parse(ticket.departDate) >= Date.parse(this.departDate));
            }
            if(this.returnDate!=null){
                this.table = this.table.filter(ticket =>
                    Date.parse((ticket.returnDate==null)?((this.returnDate<ticket.departDate)?ticket.departDate:this.returnDate):ticket.returnDate) <= Date.parse(this.returnDate));
            }
            this.tableSize = this.table.length
        },
        filter_table: function (){
            this.reset()
            if(this.filterWay=="oneWay"){
                this.table = this.table.filter(ticket => ticket.oneWay)
            }
            else if(this.filterWay=="round"){
                this.table = this.table.filter(ticket => !ticket.oneWay)
            }
            this.tableSize = this.table.length
        },
        edit: function (ticket_id){
            router.push({ path: `/ticket/${ticket_id}` })
        },
        company: function(company_id){
            router.push({path: `/airline/${(company_id)}`})
        },
        book: function(ticket){
            let new_reservation = Object.assign({}, ticket);
            new_reservation.departDate = new Date()
            if(!new_reservation.oneWay) new_reservation.returnDate = new Date()
            axios
                .post('/rest/reservation/create/'+this.userId,new_reservation)
                .then(response => console.log(response.data))
        },
        del: function (ticket_id){
            axios
                .delete('/rest/ticket/delete/'+ticket_id)
                .then(response => console.log(response.data))
            location.reload()
        },
        nextPage: function (){
            this.tablePage++
            axios
                .get('/rest/ticket/table/'+this.tablePage)
                .then(response => (this.table = response.data))
            this.tableSize = this.table.length

        },
        prevPage: function (){
            this.tablePage--
            axios
                .get('/rest/ticket/table/'+this.tablePage)
                .then(response => (this.table = response.data))
            this.tableSize = this.table.length
        }
    },
    mounted() {
        if(this.$route.path==='/')
            this.main = true
        if(localStorage.getItem('userId')!=null){
            this.userId = localStorage.getItem('userId');
        }
        if(localStorage.getItem('user')!=null){
            this.user = localStorage.getItem('user')
        }
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
    <select name="filter" v-model="tableFilter.way">
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
        <td>{{t.oneWay}}</td>
        <td>{{t.origin.name}}</td>
        <td>{{t.destination.name}}</td>
        <td >{{t.departDate}}</td>
        <td>{{t.returnDate}}</td>
        <td v-on:click="company(t.company.id)">{{t.company.name}}</td>
        <td>{{t.count}}</td>
        <td v-on:click="del(t.ticketId)">Delete</td>
        <td v-on:click="edit(t.ticketId)">Edit</td>
    </tr>
    </tbody>
    <tbody v-else>
        <tr v-for="t in table">
        <td>{{t.oneWay}}</td>
        <td>{{t.origin.name}}</td>
        <td>{{t.destination.name}}</td>
        <td>{{t.departDate}}</td>
        <td>{{t.returnDate}}</td>
        <td v-on:click="company(t.company.id)">{{t.company.name}}</td>
        <td>{{t.count}}</td>
        <td v-on:click="book(t)">Book</td>
        </tr>
    </tbody>
    <button v-on:click="prevPage()" :disabled="tablePage===1"> < </button>
    <label>{{tablePage}}</label>
    <button v-on:click="nextPage()" :disabled="5===tableSize"> > </button>
    </table>
     </div>
    `
});