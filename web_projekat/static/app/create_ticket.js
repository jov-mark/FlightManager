Vue.component("create-ticket",{
    data: function (){
        return{
            company_list: null,
            flight_list: null,
            selectedCompany: null,
            selectedFlight: null,
            response: {
                type: "",
                message: "",
            },
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
        select_oneway: function (){

        },
        create_ticket: function (){
            if(!this.validateInput()){
                console.log("Input is invalid!")
                return
            }
            this.ticket.company = this.company_list.find(x => x.id === this.selectedCompany)
            this.ticket.flight = this.flight_list.find(x => x.id === this.selectedFlight)
            if(this.ticket.returnDate==="" || this.ticket.oneWay==="true"){
                this.ticket.returnDate=this.ticket.departureDate
            }
            axios
                .post('/rest/ticket/create', this.ticket)
                .then(response => this.response = response)
            parseResponse(this.response.type,this.response.message)
        },
        validateInput: function (){
            const isOneWay = this.ticket.oneWay!==""
            const isDeparture = this.ticket.departureDate!==""
            const isReturn = (this.ticket.oneWay==="false" && this.ticket.returnDate!=="") || this.ticket.oneWay==="true"
            const isComSelected = this.selectedCompany != null
            const isFlightSelected = this.selectedFlight != null
            const isCount = this.ticket.count!=="" && parseInt(this.ticket.count)>0
            return isOneWay && isDeparture && isReturn && isComSelected && isFlightSelected && isCount
        }
    },
    mounted() {
        axios
            .get('/rest/company/getAll')
            .then(response => (this.company_list=response.data))
        axios
            .get('rest/flight/getAll')
            .then(response => (this.flight_list=response.data))
    },
    template:`
    <div>
<!--    <h3>Create new Ticket:</h3>-->
    <table>
        <tr>
            <td><label for="one_way">One way:</label></td>
            <td>
                <select name="one_way" v-model="ticket.oneWay" v-on:click="select_oneway()">
                    <option value="true">Yes</option>
                    <option value="false">No</option>
                </select>
            </td>
        </tr>
        <tr>
            <td><label for="company">Company:</label></td>
            <td>
                <select name="company" v-model="selectedCompany">
                    <option v-for="c in company_list" :value="c.id">{{c.name}}</option>
                </select>
            </td>
        </tr>
        <tr>
            <td><label for="flight">Flight:</label></td>
            <td>
                <select name="flight" v-model="selectedFlight">
                    <option v-for="f in flight_list" :value="f.id">{{f.name}}</option>        
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
    <button v-on:click="create_ticket()">Create</button>

</div>
    `
});