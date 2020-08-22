Vue.component("main-page",{
    data: function (){
        return{
            user: "",
            filter_way: "all",
            origin: "",
            dest: "",
            depart_date: null,
            return_date: null,
            city_list: null,
            ticket_table: null,
            backup_table: [],
            backed: false
        }
    },
    methods: {
        reset: function (){
            if(!this.backed){
                this.backup_table = [...this.ticket_table]
                this.backed = true
            }
            this.ticket_table = [...this.backup_table]
        },
        search: function (){
            this.reset()

            if(Date.parse(this.depart_date)-Date.parse(this.return_date)>=0) {
                console.log("NOPE MATORI")
                return
            }

            if(this.origin!=""){
                this.ticket_table = this.ticket_table.filter(ticket => ticket.origin.id===this.origin);
            }
            if(this.dest!="") {
                this.ticket_table = this.ticket_table.filter(ticket => ticket.destination.id === this.dest);
            }
            if(this.depart_date!=null){
                this.ticket_table = this.ticket_table.filter(ticket =>
                    Date.parse(ticket.departDate) >= Date.parse(this.depart_date));
            }
            if(this.return_date!=null){
                this.ticket_table = this.ticket_table.filter(ticket =>
                    Date.parse((ticket.returnDate==null)?((this.return_date<ticket.departDate)?ticket.departDate:this.return_date):ticket.returnDate) <= Date.parse(this.return_date));
            }
       },
        filter_table: function (){
            this.reset()
            if(this.filter_way=="one_way"){
                this.ticket_table = this.ticket_table.filter(ticket => ticket.oneWay)
            }
            else if(this.filter_way=="round"){
                this.ticket_table = this.ticket_table.filter(ticket => !ticket.oneWay)
            }
        },
        edit: function (ticket_id){
            router.push({ path: `/ticket/${ticket_id}` })
        },
        company: function(company_id){
            router.push({path: `/airline/${(company_id)}`})
        },
        book: function(ticket_id){

        },
        del: function (ticket_id){
            axios
                .delete('/rest/ticket/delete/'+ticket_id)
                .then(response => console.log(response.data))
            // location.reload()
        }
    },
    mounted(){
        if(localStorage.getItem('user')!=null){
            this.user = localStorage.getItem('user')
        }
        axios
            .get('/rest/ticket/table')
            .then(response => (this.ticket_table = response.data))
        axios
            .get('/rest/city/getAll')
            .then(response => (this.city_list=response.data))
    },

    template: `	
	<div>
	<user-logout-form></user-logout-form>
    <div v-if="this.user==='user'">
    <label for="origin">Origin:</label>
     <select name="origin" v-model="origin">
        <option v-for="c in city_list" :value="c.id">{{c.name}}</option>
        <option value="">All</option>
    </select>
    <label for="dest">Destination:</label>
     <select name="dest" v-model="dest">
        <option v-for="c in city_list" :value="c.id">{{c.name}}</option>
        <option value="">All</option>
    </select>
    <label for="departure">Departure:</label>
    <input type="date" name="departure" v-model="depart_date">
    <label for="return">Return:</label>
    <input type="date" name="return" v-model="return_date">
    <button v-on:click="search()">Search</button>
    </div>
    <div>
    <label for="filter">Filter tickets:</label>
    <select name="filter" v-model="filter_way" v-on:click="filter_table()">
        <option value="all" selected="selected">All tickets</option>
        <option value="one_way">One way</option>
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
        <tr v-for="t in ticket_table">
        <td>{{t.oneWay}}</td>
        <td>{{t.origin.name}}</td>
        <td>{{t.destination.name}}</td>
        <td>{{t.departDate}}</td>
        <td>{{t.returnDate}}</td>
        <td v-on:click="company(t.company.id)">{{t.company.name}}</td>
        <td>{{t.count}}</td>
        <td v-on:click="del(t.ticketId)">Delete</td>
        <td v-on:click="edit(t.ticketId)">Edit</td>
    </tr>
    </tbody>
    <tbody v-else>
        <tr v-for="t in ticket_table">
        <td>{{t.oneWay}}</td>
        <td>{{t.origin.name}}</td>
        <td>{{t.destination.name}}</td>
        <td>{{t.departDate}}</td>
        <td>{{t.returnDate}}</td>
        <td v-on:click="company(t.company.id)">{{t.company.name}}</td>
        <td>{{t.count}}</td>
        <td>Book</td>
        </tr>
</tbody>
    
    </table> 
    <div v-if="this.user==='admin'">
        <hr>
        <br>
        <user-form></user-form>
        <hr>
        <br>
	    <create-ticket></create-ticket>
	</div>
    </div>
    `
});