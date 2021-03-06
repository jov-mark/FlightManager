Vue.component("ticket-table",{
    data: function (){
        return{
            user: "",
            userId: "",
            main: false,
            tablePage: 1,
            checkValue: false,
            checkDate: new Date('December 31, 2018'),
            tableFilter: {
                originCity: 0,
                destCity: 0,
                departDate: new Date('December 31, 2018'),
                returnDate: new Date('December 31, 2018'),
                way: "all",
                active: false
            },
            cityList: null,
            table: null,
            date1: "",
            date2: "",
            way: "all"
        }
    },
    methods:{
        filterTable: function (){
            this.checkValue=false
            if(Date.parse(this.date1)-Date.parse(this.date2)>=0) {
                alert("Return date must be after departure date!")
                return
            }
            this.tableFilter.active = true
            if(this.date1!=="")
                this.tableFilter.departDate = this.date1
            this.tableFilter.returnDate = (this.date2==="" || this.tableFilter.way==="oneWay")?this.tableFilter.returnDate:this.date2
            axios
                .post('/rest/ticket/filterTable',this.tableFilter)
                .then(response => console.log(response.status))
            axios
                .get('/rest/ticket/getFilteredTable/'+1)
                .then(response => this.table = response.data)
        },
        filterWay: function (){
            this.checkValue=false
            if(this.tableFilter.way!==this.way){
                this.tableFilter.way = this.way
                console.log(this.tableFilter)
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
                departDate: new Date('December 31, 2018'),
                returnDate: new Date('December 31, 2018'),
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
                .catch(error => this.handleResponse("0",error.response.data))
        },
        prevPage: function (){
            this.checkValue = false;
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
            let newReservation = Object.assign({}, ticket);
            newReservation.departDate = new Date()
            if(!newReservation.oneWay) newReservation.returnDate = new Date()
            axios
                .post('/rest/reservation/create/'+this.userId,newReservation)
                .then(response => this.handleResponse("book",response.data))
                .catch(function (error){
                    if(error.response)
                        parseResponse(error.response.data.type,error.response.data.message)
                    else{
                        alert("Unknown error occurred.")
                    }
                })
        },
        deleteTicket: function (ticket_id){
            axios
                .delete('/rest/ticket/delete/'+ticket_id)
                .catch(function (error){
                    if(error.response)
                        parseResponse(error.response.data.type,error.response.data.message)
                    else{
                        alert("Unknown error occurred.")
                    }
                })
            location.reload()
        },
        handleResponse(type,data){
            switch (type){
                case "book":
                    localStorage.setItem('res',(parseInt(localStorage.getItem('res'))+1).toString())
                    location.reload()
                    break
                case "0":
                    this.tablePage--
                    this.checkValue = true
                    this.checkLast()
                    break
            }
        },
        checkLast: function (){
            if(this.table===null)   return true
            if(this.checkValue)  return true
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
        <th>One-way</th>
        <th>Origin</th>
        <th>Destination</th>
        <th>Depart</th>
        <th>Return</th>
        <th>Company</th>
        <th>Count</th>
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