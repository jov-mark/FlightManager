Vue.component("ticket-page",{
    data: function (){
        return{
            ticket_id: this.$route.params.id,
            company_list: null,
            flight_list: null,
            ticket: {
                id: this.ticket_id,
                oneWay: false,
                company: {},
                departureDate: "",
                returnDate: "",
                flight: {},
                count: ""
            }
        }
    },
    methods:{
        update_ticket: function (){
            if(this.validateInput()) {
                axios
                    .post('/rest/ticket/update', this.ticket)
                    .then(response => console.log(response.data))
            }
        },
        available_return: function (){
            return
        },
        cancel_update: function () {
            router.push({path: `/`})
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
        axios
            .get('/rest/company/getAll')
            .then(response => (this.company_list = response.data))
        axios
            .get('/rest/flight/getAll')
            .then(response => (this.flight_list = response.data))
        axios
            .get('/rest/ticket/get/'+this.ticket_id)
            .then(response => (this.ticket = response.data))
    },
    template:`
    
    <div>
	<user-logout-form></user-logout-form>
	<table>
	<tr>
        <td>
        <label for="one_way">One way:</label>
        </td>
        <td>
        <select name="one_way" v-model="ticket.oneWay">
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
                <option v-for="c in company_list" :value="c"
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
                <option v-for="f in flight_list" :value="f"
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
    <button v-on:click="update_ticket()">Update</button>
    <button v-on:click="cancel_update()">Cancel</button>

</div>
    `
});