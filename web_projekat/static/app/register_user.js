Vue.component("user-form",{
    data: function (){
        return{
            user: {
                username:"",
                password:"",
                type:""
            }
        }
    },
    methods:{
        register: function (){
            if(this.validateInput())
                axios
                    .post('/rest/user/register',this.user)
                    .then(response => console.log(response.data))
            else
                console.log("Input is invalid!")
        },
        validateInput: function () {
            const isName = this.user.username!==""
            const isPass = /\d/.test(this.user.password) && /[a-zA-Z]/.test(this.user.password) && this.user.password.length>=6
            const isType = this.user.type!==""
            return  isName && isPass && isType;
        }
    },
    mounted() {},
    template:`
    <div>
<!--    <h3>Register new User:</h3>-->
    <table>
        <tr>
            <td><label for="username">Username</label></td>
            <td><input type="text" name="username" v-model="user.username"></td>
        </tr>
        <tr>
            <td><label for="password">Password</label></td>
            <td><input type="text" name="password" v-model="user.password"></td>
        </tr>
        <tr>
            <td><label for="user_type">User type:</label></td>
            <td>
                <select name="user_type" id="type" v-model="user.type">
                  <option value="false">Plain user</option>
                  <option value="true">Admin</option>
                </select>
            </td>
        </tr>
    </table>
    <button v-on:click="register()">Register</button>
        </div>

    `
});