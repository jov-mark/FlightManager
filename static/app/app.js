const Main = { template: '<main-page></main-page>'};
const Login = { template: '<login-page></login-page>'};
const Ticket = { template: '<ticket-page></ticket-page>'}
const Airline = { template: '<airline-page></airline-page>'}
const Reservation = { template: '<reservation-page></reservation-page>'};

const router = new VueRouter({
    mode:'hash',
    routes:[
        { path: '/', component: Main},
        { path: '/login',component: Login},
        { path: '/reservation/:user_id', component: Reservation},
        { path: '/airline/:id', component: Airline},
        { path: '/ticket/:id', component: Ticket}
    ]
})

var app = new Vue({
    router,
    el:'#app'
});