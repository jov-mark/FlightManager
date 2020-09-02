Vue.component("airline-page",{
    data: function (){
        return{
            main: false,
            checkValue: false,
            tablePage: 1,
            user: "",
            userId: "",
            companyId: this.$route.params.id,
            table: null,
            company: {},
            companyName: "",
            bckupName: "",
            newCompany:{
                name: "",
                version: 1
            },
            mode: "BROWSE"
        }
    },
    methods:{
        editCompany: function (){
            this.mode="EDIT"
        },
        saveEdit: function (){
            this.bckupName = this.company.name
            if(this.companyName!=="") {
                this.mode = "BROWSE"
                if (this.companyName === this.company.name){
                    return
                }
                this.company.name = this.companyName
                axios
                    .post('/rest/company/update',this.company)
                    .then(response => this.handleResponse("update",response.data))
                    .catch(error => this.handleResponse("error", error.response.data))
            }
            else
                alert("Name can't be null!")
        },
        updateVersion: function (data){
            this.company.version++
            parseResponse(data.type,data.message)
        },
        cancelUpdate: function (){
            this.companyName = ""
            this.mode="BROWSE"
        },
        deleteCompany: function (){
            axios
                .delete('/rest/company/delete/'+this.companyId)
                .then(response => this.handleResponse("delete",response.data))
                .catch(function (error){
                    if(error.response){
                        parseResponse(error.response.data.type,error.response.data.message)
                    }else{
                        parseResponse("object","ER")
                    }
                })
        },
        createCompany: function (){
            if(this.newCompany.name==="") {
                console.log("Name can't be null!")
            }
            else{
                axios
                    .post('/rest/company/create',this.newCompany)
                    .then(response => this.handleResponse("create",response.data))
                    .catch(function (error){
                        if(error.response){
                            parseResponse(error.response.data.type,error.response.data.message)
                        }else{
                            parseResponse("object","ER")
                        }
                    })
            }
        },
        handleResponse(type,data){
            switch (type){
                case "update":
                    this.company.version++
                    this.company.name = this.companyName
                    this.companyName = ""
                    break
                case "create":
                    this.newCompany.name = ""
                    break
                case "delete":
                    router.push({path: `/`})
                    break
                case "book":
                    localStorage.setItem('res',(parseInt(localStorage.getItem('res'))+1).toString())
                    location.reload()
                    break
                case "error":
                    this.company.name = this.bckupName
                    break
                case "0":
                    this.tablePage--
                    this.checkValue = true
                    this.checkLast()
                    break
            }
            parseResponse(data.type,data.message)
        },
        nextPage: function (){
            this.tablePage++
            axios
                .get('/rest/ticket/company?companyId='+this.companyId+'&page='+this.tablePage)
                .then(response => (this.table = response.data))
                .catch(error => this.handleResponse("0",error.response.data))
        },
        prevPage: function (){
            this.checkValue = false;
            this.tablePage--
            axios
                .get('/rest/ticket/company?companyId='+this.companyId+'&page='+this.tablePage)
                .then(response => (this.table = response.data))
        },
        checkLast: function (){
            if(this.table===null)   return true
            if(this.checkValue)  return true
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
                .then(response => this.handleResponse("book",response.data))
                .catch(function (error){
                    if(error.response)
                        parseResponse(error.response.data.type,error.response.data.message)
                    else{
                        alert("Unknown error occurred.")
                    }
                })
        },
        deleteTicket: function (ticketId){
            axios
                .delete('/rest/ticket/delete/'+ticketId)
                .catch(function (error){
                    if(error.response)
                        parseResponse(error.response.data.type,error.response.data.message)
                    else{
                        alert("Unknown error occurred.")
                    }
                })
            location.reload()
        }
    },
    mounted() {
        if(localStorage.getItem('user')===null){
            window.location.replace('/#/login')
        }else{
            this.user = localStorage.getItem('user')
            this.userId = localStorage.getItem('user_id');
        }
        axios
            .get('/rest/ticket/company?companyId='+this.companyId+'&page='+this.tablePage)
            .then(response => (this.table = response.data))
        axios
            .get('/rest/company/get/'+this.companyId)
            .then(response => (this.company = response.data))
            .catch(function (error){
                if(error.response.data){
                    parseResponse(error.response.data.type,error.response.data.message)
                }else{
                    alert("Unknown error occurred.")
                }
            })
    },
    template:`
<div>
	<user-menu></user-menu>
	<h3>{{this.company.name}}</h3>
    <br>
    <table border="1">
    <tr>
        <th>One-way</th>
        <th>Origin</th>
        <th>Destination</th>
        <th>Depart</th>
        <th>Return</th>
        <th>Count</th>
    </tr>
    <tbody v-if="this.user==='admin'">
        <tr v-for="t in table">
        <td v-if="t.oneWay">Yes</td>
        <td v-else>No</td>
        <td>{{t.origin.name}}</td>
        <td>{{t.destination.name}}</td>
        <td>{{t.departDate}}</td>
        <td>{{t.returnDate}}</td>
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
        <td>{{t.count}}</td>
        <td v-on:click="bookTicket(t)">Book</td>
        </tr>
    </tbody>
    <button v-on:click="prevPage()" :disabled="tablePage===1"> < </button>
    <label>{{tablePage}}</label>
    <button v-on:click="nextPage()" :disabled="checkLast()"> > </button>
    </table>
    <div v-if="this.user==='admin'">
    <br>
    <h4>Update company:</h4>
    <input type="text" name="com_name" v-model="companyName" v-bind:disabled="mode=='BROWSE'">
    <button v-on:click="editCompany()" v-bind:disabled="mode=='EDIT'">Edit</button>
    <button v-on:click="saveEdit()" v-bind:disabled="mode=='BROWSE'">Save</button>
    <button v-on:click="cancelUpdate()" v-bind:disabled="mode=='BROWSE'">Cancel</button>
    <button v-on:click="deleteCompany()" v-bind:disabled="mode=='EDIT'">Delete company</button>
    <hr>
    <br>
    <h4>Create new company:</h4>
    <label for="newCompany">New company:</label>
    <input type="text" name="newCompany" v-model="newCompany.name">
    <button v-on:click="createCompany()">Create</button>
    </div> 
</div>    
`
});