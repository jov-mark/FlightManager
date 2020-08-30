Vue.component("main-page",{
    data: function (){
        return{
            user: "",
            user_id: "",
        }
    },
    methods: {
    },
    mounted(){
        if(localStorage.getItem('user')!=null){
            this.user = localStorage.getItem('user')
        }
    },

    template: `	
	<div>
	<user-logout-form></user-logout-form>
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