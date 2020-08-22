Vue.component("ticket-table",{
    data: function (){
        return{
            ticket_table: null,}
    },
    methods:{
    },
    mounted() {
        axios
            .get('/rest/ticket/table')
            .then(response => (this.ticket_table = response.data))
    },
    template:`
    
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
    <tr v-for="t in ticket_table">
        <td>{{t.oneWay}}</td>
        <td>{{t.origin.name}}</td>
        <td>{{t.destination.name}}</td>
        <td>{{t.departDate}}</td>
        <td>{{t.returnDate}}</td>
        <td v-on:click="company(t.company.id)">{{t.company.name}}</td>
        <td>{{t.count}}</td>
        <td>Delete</td>
        <td v-on:click="edit(t.ticketId)">Edit</td>
        <td>Book</td>
    </tr>
    </table> 
    `
});