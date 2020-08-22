Vue.component("user-logout-form",{
    data: function (){
        return{
            username: "",
            type: ""
        }
    },
    methods:{
        main: function (){
            window.location.replace('/#/')
        },
        reservations: function (){
            const user_id = localStorage.getItem('user_id')
            router.push({path: `/reservation/${(user_id)}`})
        },
        logout: function (){
            localStorage.setItem('username',null)
            localStorage.setItem('user',null)
            window.location.replace('/#/login')
        }
    },
    mounted() {
        this.username = localStorage.getItem('username')
        this.type = localStorage.getItem('user')
    },
    template:`
    <div class="topcorner">
    <label v-on:click="main()">{{this.username}}</label>
    <label>{{this.type}}</label>
    <label v-if="this.type==='user'" v-on:click="reservations()">Reservations</label>
    <button v-on:click="logout()">Log out</button>
    </div>
    `
});