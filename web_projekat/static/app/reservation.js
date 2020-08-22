Vue.component("reservation-page",{
    data: function (){
        return{
            user_id: null,
            reservation_table: null,
            currDate: new Date(),
            milisInDay: 86400000
        }
    },
    methods:{
        check: function (res_id){
            var resDate = (this.reservation_table.find(res => res.reservationId === res_id)).departDate
            return !(Date.parse(resDate)-Date.parse(this.currDate)<this.milisInDay)
        },
        del: function (res_id){
            axios
                .delete('/rest/reservation/delete/'+res_id)
                .then(response => console.log(response.data))

        }
    },
    mounted() {
        if(localStorage.getItem('user_id')){
            this.user_id = localStorage.getItem('user_id')
        }
        axios
            .get('/rest/reservation/table/'+this.user_id)
            .then(response => (this.reservation_table=response.data))
    },
    template:`
    <div>
	<user-logout-form></user-logout-form>
    <table border="1">
    <tr>
        <td>One-way</td>
        <td>Origin</td>
        <td>Destination</td>
        <td>Depart</td>
        <td>Return</td>
        <td>Company</td>
        <td>Count</td>
        <td>Available</td>
    </tr>
    <tr v-for="reservation in reservation_table">
        <td>{{reservation.oneWay}}</td>
        <td>{{reservation.origin.name}}</td>
        <td>{{reservation.destination.name}}</td>
        <td>{{reservation.departDate}}</td>
        <td>{{reservation.returnDate}}</td>
        <td>{{reservation.company.name}}</td>
        <td>{{reservation.count}}</td>
        <td>{{reservation.isAvailable}}</td>
        <td v-if="check(reservation.reservationId)"
        v-on:click="del(reservation.reservationId)">Delete</td>
    </tr>
    </table> 
    </div>
    `
});