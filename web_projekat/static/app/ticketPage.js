Vue.component("ticket-page",{
    data: function (){
        return{
            ticketId: this.$route.params.id,
            companyList: null,
            flightList: null,
            ticket: {
                id: this.ticketId,
                oneWay: false,
                company: {},
                departureDate: "",
                returnDate: "",
                flight: {},
                count: "",
                version: 0
            }
        }
    },
    methods:{
        updateTicket: function (){
            if(this.validateInput()) {
                axios
                    .post('/rest/ticket/update', this.ticket)
                    .then(response => this.handleResponse(response.data))
                    .catch(function (error){
                        if(error.response)
                            parseResponse(error.response.data.type,error.response.data.message)
                        else{
                            alert("Unknown error occurred.")
                        }
                    })
            }
        },
        cancelUpdate: function () {
            router.push({path: `/`})
        },
        handleResponse: function (data){
            this.ticket.version++
            parseResponse(data.type,data.message)
        },
        validateInput: function (){
            if(this.ticket.count<1) {
                console.log("Count must be at least 1")
                return false;
            }
            return true;
        }
    },
    mounted() {
        if(localStorage.getItem('user')===null){
            window.location.replace('/#/login')
        }
        axios
            .get('/rest/company/getAll')
            .then(response => (this.companyList = response.data))
        axios
            .get('/rest/flight/getAll')
            .then(response => (this.flightList = response.data))
        axios
            .get('/rest/ticket/get/'+this.ticketId)
            .then(response => (this.ticket = response.data))
            .catch(function (error){
                if(error.response){
                    parseResponse(error.response.data.type,error.response.data.message)
                }else{
                    alert("Unknown error occurred.")
                }
            })
    },
    template:`
    
    <div>
	<user-menu></user-menu>
	<table>
	<tr>
        <td>
        <label for="oneWay">One way:</label>
        </td>
        <td>
        <select name="oneWay" v-model="ticket.oneWay">
            <option value="true" :selected="ticket.oneWay">Yes</option>
            <option value="false" :selected="!ticket.oneWay">No</option>
        </select>
        </td>
    </tr>
	<tr>
        <td>
            <label for="company">Company:</label>
        </td>
        <td>
            <select name="company" v-model="ticket.company">
                <option v-for="c in companyList" :value="c"
                 :selected="ticket.company.id===c.id">{{c.name}}</option>
            </select>
        </td>
    </tr>
	<tr>
        <td>
            <label for="flight">Flight:</label> 
        </td>
        <td>
            <select name="flight" v-model="ticket.flight">
                <option v-for="f in flightList" :value="f"
                :selected="ticket.flight.id===f.id">{{f.name}}</option>        
            </select>
        </td>
    </tr>
	<tr>
        <td>
            <label for="departure">Departure:</label>
        </td>
        <td>
             <input type="date" name="departure" v-model="ticket.departureDate">
        </td>
    </tr>
	<tr>
        <td>
            <label for="return">Return:</label>
        </td>
        <td>
             <input type="date" name="return" :disabled="ticket.oneWay==='true'" v-model="ticket.returnDate">
        </td>
    </tr>
	<tr>
        <td>
            <label for="count">Count:</label>
        </td>
        <td>
             <input type="text" name="count" v-model="ticket.count">
        </td>
    </tr>
    </table>
    
    <br>
    <button v-on:click="updateTicket()">Update</button>
    <button v-on:click="cancelUpdate()">Cancel</button>

</div>
    `
});