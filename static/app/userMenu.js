Vue.component("user-menu",{
    data: function (){
        return{
            username: "",
            type: "",
            resNum: 0
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
            localStorage.setItem('type',null)
            localStorage.setItem('res',0)
            window.location.replace('/#/login')
        }
    },
    mounted() {
        this.username = localStorage.getItem('username')
        this.type = localStorage.getItem('user')
        this.resNum = localStorage.getItem('res')
    },
    template:`
    <div class="topcorner">
    <label v-on:click="main()">{{this.username}}</label>
    <label>{{this.type}}</label>
    <i v-if="this.type==='user'"><img src="resIcon.png" width="20px" height="20px"  v-on:click="reservations()"/>{{this.resNum}}</i>
    <button v-on:click="logout()">Log out</button>
    </div>
    `
});