Vue.component("airline-page",{
    data: function (){
        return{
            main: false,
            tablePage: 1,
            user: "",
            companyId: this.$route.params.id,
            table: null,
            companyName: "",
            company: {},
            newCompany:{
                name: "",
                version: 1
            },
            mode: "BROWSE"
        }
    },
    methods:{
        edit_company: function (){
            this.mode="EDIT"
            this.companyName = this.company.name
        },
        save_edit: function (){
            if(this.company.name!=="") {
                if (this.companyName === this.company.name)    return

                this.mode = "BROWSE"
                axios
                    .post('/rest/company/update',this.company)
                    .then(response => console.log(response.data))
            }
            else
                console.log("Name can't be null!")
        },
        cancel_update: function (){
            this.company.name = this.companyName
            this.mode="BROWSE"
        },
        delete_company: function (){
            axios
                .delete('/rest/company/delete/'+this.companyId)
                .then(response => console.log(response.data))
            router.push({path: `/`})
        },
        create_company: function (){
            if(this.newCompany.name==="") {
                console.log("Name can't be null!")
            }
            else{
                axios
                    .post('/rest/company/create',this.newCompany)
                    .then(response => console.log(response.data))
            }
        },
        nextPage: function (){
            this.tablePage++
            axios
                .get('/rest/ticket/company?companyId='+this.companyId+'&page='+this.tablePage)
                .then(response => (this.table = response.data))
        },
        prevPage: function (){
            this.tablePage--
            axios
                .get('/rest/ticket/company?companyId='+this.companyId+'&page='+this.tablePage)
                .then(response => (this.table = response.data))
        },
        checkLast: function (){
            if(this.table===null)   return true
            return this.table.length<5
        },
        editTicket: function (ticketId){
            router.push({ path: `/ticket/${ticketId}` })
        },
        bookTicket: function(ticket){
            let newReservation = Object.assign({}, ticket);
            newReservation.departDate = new Date()
            if(!newReservation.oneWay) newReservation.returnDate = new Date()
            axios
                .post('/rest/reservation/create/'+this.userId,newReservation)
                .then(response => console.log(response.data))
        },
        deleteTicket: function (ticketId){
            axios
                .delete('/rest/ticket/delete/'+ticketId)
                .then(response => console.log(response.data))
            location.reload()
        }
    },
    mounted() {
        if(localStorage.getItem('user')!=null){
            this.user = localStorage.getItem('user')
        }
        axios
            .get('/rest/ticket/company?companyId='+this.companyId+'&page='+this.tablePage)
            .then(response => (this.table = response.data))
        axios
            .get('/rest/company/get/'+this.companyId)
            .then(response => (this.company = response.data))
    },
    template:`
<div>
	<user-logout-form></user-logout-form>
    <input type="text" name="com_name" v-model="company.name" v-bind:disabled="mode=='BROWSE'">
    <button v-if="this.user==='admin'" v-on:click="edit_company()" v-bind:disabled="mode=='EDIT'">Edit</button>
    <button v-if="this.user==='admin'" v-on:click="save_edit()" v-bind:disabled="mode=='BROWSE'">Save</button>
    <button v-if="this.user==='admin'" v-on:click="cancel_update()" v-bind:disabled="mode=='BROWSE'">Cancel</button>
    <button v-if="this.user==='admin'" v-on:click="delete_company()" v-bind:disabled="mode=='EDIT'">Delete company</button>
    <br>
    
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
        <td>{{t.departDate}}</td>
        <td>{{t.returnDate}}</td>
        <td>{{t.company.name}}</td>
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
        <td>{{t.company.name}}</td>
        <td>{{t.count}}</td>
        <td v-on:click="bookTicket(t)">Book</td>
        </tr>
    </tbody>
    <button v-on:click="prevPage()" :disabled="tablePage===1"> < </button>
    <label>{{tablePage}}</label>
    <button v-on:click="nextPage()" :disabled="checkLast()"> > </button>
    </table>

    <div v-if="this.user==='admin'">
    <hr>
    <br>
    <label for="newCompany">New company:</label>
    <input type="text" name="newCompany" v-model="newCompany.name">
    <button v-on:click="create_company()">Create</button>
    </div> 
</div>    
`
});