Vue.component("main-page",{
    data: function (){
        return{user: ""}
    },
    methods: {},
    mounted(){
        if(localStorage.getItem('user')===null){
            window.location.replace('/#/login')
        }else {
            this.user = localStorage.getItem('user')
        }
    },

    template: `	
	<div>
	<user-menu></user-menu>
    <ticket-table></ticket-table>
    <div v-if="this.user==='admin'">
        <hr>
        <br>
        <user-form></user-form>
        <hr>
        <br>
	    <create-ticket></create-ticket>
	</div>
    </div>
    `
});