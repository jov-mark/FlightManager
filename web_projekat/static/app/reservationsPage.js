Vue.component("reservation-page",{
    data: function (){
        return{
            userId: null,
            reservationTable: null,
            currDate: new Date(),
            millisInDay: 86400000
        }
    },
    methods:{
        checkDate: function (resId){
            let resDate = (this.reservationTable.find(res => res.reservationId === resId)).departDate
            return !(Date.parse(resDate)-Date.parse(this.currDate)<this.millisInDay)
        },
        checkAvailability: function (resId){
            let resDate = (this.reservationTable.find(res => res.reservationId === resId)).departDate
            return !(Date.parse(resDate)-Date.parse(this.currDate)<(-this.millisInDay))
        },
        deleteReservation: function (resId){
            axios
                .delete('/rest/reservation/delete/'+resId)
                .then(function (){location.reload()})
                .catch(function (error){
                    if(error.response)
                        parseResponse(error.response.data.type,error.response.data.message)
                    else{
                        alert("Unknown error occurred.")
                    }
                })
        }
    },
    mounted() {
        if(localStorage.getItem('user')===null){
            window.location.replace('/#/login')
        }else if(localStorage.getItem('user_id')!==null){
            this.userId = localStorage.getItem('user_id');
        }
        axios
            .get('/rest/reservation/table/'+this.userId)
            .then(response => (this.reservationTable=response.data))
    },
    template:`
    <div>
	<user-menu></user-menu>
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
    <tr v-for="reservation in reservationTable">
        <td v-if="reservation.oneWay">Yes</td>
        <td v-else>No</td>
        <td>{{reservation.origin.name}}</td>
        <td>{{reservation.destination.name}}</td>
        <td>{{reservation.departDate}}</td>
        <td>{{reservation.returnDate}}</td>
        <td>{{reservation.company.name}}</td>
        <td>{{reservation.count}}</td>
        <td v-if="checkAvailability(reservation.reservationId)">Yes</td>
        <td v-else>No</td>
        <td v-if="checkDate(reservation.reservationId)" v-on:click="deleteReservation(reservation.reservationId)">Delete</td>
    </tr>
    </table> 
    </div>
    `
});