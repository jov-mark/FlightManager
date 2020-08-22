Vue.component("login-page",{
    data: function (){
        return{
            username: "",
            password: ""
        }
    },
    methods :{
        login: function (){
            $.ajax({
                url: "/rest/user/login",
                type: "GET",
                data: {
                    username: this.username,
                    password: this.password
                },
                contentType: "application/json",
                dataType: "json",
                success: function (data){
                    localStorage.setItem('username',data.username)
                    localStorage.setItem('user_id',data.id)
                    localStorage.setItem('user','user')
                    if(data.type){
                        localStorage.setItem('user','admin')
                    }
                    window.location.replace("/")
                },
                error: function (data){
                    console.log("Error:")
                    console.log(data)
                }
            })
        }
    },
    mounted () {},
    template:`
<div>
    <label for="username">Username</label>
    <input type="text" name="username" v-model="username"/>
    <label for="password">Password</label>
    <input type="text" name="password" v-model="password"/>
    <button v-on:click="login()">Login</button> 
</div>
    `
});