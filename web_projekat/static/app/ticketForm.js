Vue.component("create-ticket",{
    data: function (){
        return{
            companyList: null,
            flightList: null,
            selectedCompany: null,
            selectedFlight: null,
            ticket: {
                oneWay: "",
                company: {},
                departureDate: "",
                returnDate: "",
                flight: {},
                count: ""
            }
        }
    },
    methods:{
        createTicket: function (){
            if(this.validateInput()){
                alert("Input is invalid!")
                return
            }
            this.ticket.company = this.companyList.find(x => x.id === this.selectedCompany)
            this.ticket.flight = this.flightList.find(x => x.id === this.selectedFlight)
            if(this.ticket.returnDate==="" || this.ticket.oneWay==="true"){
                this.ticket.returnDate=this.ticket.departureDate
            }
            axios
                .post('/rest/ticket/create', this.ticket)
                .then(response => this.handleResponse(response.data))
                .catch(function (error){
                    if(error.response)
                        parseResponse(error.response.data.type,error.response.data.message)
                    else{
                        alert("Unknown error occurred.")
                    }
                })
        },
        handleResponse: function (data){
            this.selectedCompany = null
            this.selectedFlight = null
            this.ticket = {
                oneWay: "",
                company: {},
                departureDate: "",
                returnDate: "",
                flight: {},
                count: ""
            }
            parseResponse(data.type,data.message)
        },
        validateInput: function (){
            const isOneWay = this.ticket.oneWay!==""
            const isDeparture = this.ticket.departureDate!==""
            const isReturn = (this.ticket.oneWay==="false" && this.ticket.returnDate!=="") || this.ticket.oneWay==="true"
            const isComSelected = this.selectedCompany != null
            const isFlightSelected = this.selectedFlight != null
            const isDate = (Date.parse(this.ticket.departureDate)-Date.parse(this.ticket.returnDate)>=0)
            const isCount = this.ticket.count!=="" && parseInt(this.ticket.count)>0
            return isOneWay && isDeparture && isReturn && isComSelected && isFlightSelected && isCount && isDate
        }
    },
    mounted() {
        axios
            .get('/rest/company/getAll')
            .then(response => (this.companyList=response.data))
        axios
            .get('rest/flight/getAll')
            .then(response => (this.flightList=response.data))
    },
    template:`
    <div>
    <h3>Create new Ticket:</h3>
    <table>
        <tr>
            <td><label for="oneWay">One way:</label></td>
            <td>
                <select name="oneWay" v-model="ticket.oneWay">
                    <option value="true">Yes</option>
                    <option value="false">No</option>
                </select>
            </td>
        </tr>
        <tr>
            <td><label for="company">Company:</label></td>
            <td>
                <select name="company" v-model="selectedCompany">
                    <option v-for="c in companyList" :value="c.id">{{c.name}}</option>
                </select>
            </td>
        </tr>
        <tr>
            <td><label for="flight">Flight:</label></td>
            <td>
                <select name="flight" v-model="selectedFlight">
                    <option v-for="f in flightList" :value="f.id">{{f.name}}</option>        
                </select>
            </td>
        </tr>
        <tr>
            <td><label for="departure">Departure:</label></td>
            <td><input type="date" name="departure" v-model="ticket.departureDate"></td>
        </tr>
        <tr>
            <td><label for="return">Return:</label></td>
            <td><input type="date" name="return" v-bind:disabled="ticket.oneWay==='true'" v-model="ticket.returnDate"></td>
        </tr>
        <tr>
            <td><label for="count">Count:</label></td>
            <td><input type="text" name="count" v-model="ticket.count"></td>
        </tr>
    </table>
    <button v-on:click="createTicket()">Create</button>

</div>
    `
});