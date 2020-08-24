Vue.component("airline-page",{
    data: function (){
        return{
            user: "",
            companyId: this.$route.params.id,
            ticket_table: null,
            company_name: "",
            company: {},
            new_company:{
                name: "",
                version: 1
            },
            mode: "BROWSE"
        }
    },
    methods:{
        edit_company: function (){
            this.mode="EDIT"
            this.company_name = this.company.name
        },
        save_edit: function (){
            if(this.company.name!=="") {
                if (this.company_name === this.company.name)    return

                this.mode = "BROWSE"
                axios
                    .post('/rest/company/update',this.company)
                    .then(response => console.log(response.data))
            }
            else
                console.log("Name can't be null!")
        },
        cancel_update: function (){
            this.company.name = this.company_name
            this.mode="BROWSE"
        },
        delete_company: function (){
            axios
                .delete('/rest/company/delete/'+this.companyId)
                .then(response => console.log(response.data))
            router.push({path: `/`})
        },
        create_company: function (){
            if(this.new_company.name==="") {
                console.log("Name can't be null!")
            }
            else{
                axios
                    .post('/rest/company/create',this.new_company)
                    .then(response => console.log(response.data))
            }
        },
        edit_ticket: function (ticket_id){
            router.push({ path: `/ticket/${ticket_id}` })
        }
    },
    mounted() {
        if(localStorage.getItem('user')!=null){
            this.user = localStorage.getItem('user')
        }
        axios
            .get('/rest/ticket/company/'+this.companyId)
            .then(response => (this.ticket_table = response.data))
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
        <tr v-for="t in ticket_table">
        <td>{{t.oneWay}}</td>
        <td>{{t.origin.name}}</td>
        <td>{{t.destination.name}}</td>
        <td>{{t.departDate}}</td>
        <td>{{t.returnDate}}</td>
        <td>{{t.company.name}}</td>
        <td>{{t.count}}</td>
        <td>Delete</td>
        <td v-on:click="edit_ticket(t.ticketId)">Edit</td>
    </tr>
    </tbody>
    <tbody v-else>
        <tr v-for="t in ticket_table">
        <td>{{t.oneWay}}</td>
        <td>{{t.origin.name}}</td>
        <td>{{t.destination.name}}</td>
        <td>{{t.departDate}}</td>
        <td>{{t.returnDate}}</td>
        <td>{{t.company.name}}</td>
        <td>{{t.count}}</td>
        <td>Book</td>
        </tr>
</tbody>
    
    </table>
    <div v-if="this.user==='admin'">
    <hr>
    <br>
    <label for="new_company">New company:</label>
    <input type="text" name="new_company" v-model="new_company.name">
    <button v-on:click="create_company()">Create</button>
    </div> 
</div>    
`
});