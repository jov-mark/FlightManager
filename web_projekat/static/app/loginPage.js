Vue.component("login-page",{
    data: function (){
        return{
            username: "",
            password: ""
        }
    },
    methods :{
        login: function (){
            let params = "username="+this.username+"&password="+this.password
            axios
                .get('/rest/user/login?'+params)
                .then(function (response){
                    let data = response.data
                    localStorage.setItem('user_id',data.id)
                    localStorage.setItem('username',data.username)
                    localStorage.setItem('user',(data.type)?'admin':'user')
                    window.location.replace("/")
                })
                .catch(function (error){
                    if(error.response)
                        parseResponse(error.response.data.type,error.response.data.message)
                    else{
                        alert("Unknown error occurred.")
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