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
                    console.log(data)
                }
            })
        }
    },
    mounted () {},
    template:`
<div>
    <table>
    <tr>
        <td><label for="username">Username:</label></td>
        <td><input type="text" name="username" v-model="username"/></td>
    </tr>
    <tr>
        <td><label for="password">Password:</label></td>
        <td><input type="text" name="password" v-model="password"/></td>
    </tr>
    </table>
    <button v-on:click="login()">Login</button>
</div>
    `
});