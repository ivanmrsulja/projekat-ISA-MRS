Vue.component("pocetna-strana", {
	data: function () {
		    return {
				user : {}
		    }
	},
	template: ` 
<div align = center>
		<h1>Login korisnika: </h1>
		<table>
			<tr>
				<td> <h2>Username:</h2> </td> <td> <input type="text" name="username" /> </td>
			</tr>
			<tr>
				<td> <h2>Password:</h2> </td> <td> <input type="password" name="pass" /> </td>
			</tr>
			<tr>
				<td align=center colspan=2>
					<input type="button" value="Posalji" v-on:click="logUserIn()"/>
				</td>
			</tr>
		</table>
	
	
</div>		  
`
	, 
	methods : { 
		logUserIn : function () {
			let us = $("input[name=username]").val();
			let pass = $("input[name=pass]").val();
			
			let u = {username: us, password: pass};
			let temp = this;
			
			axios
    		.post("api/users/login", u)
    		.then(function(response){
				if(response.data == "OK"){
					axios
					.get("/api/users/currentUser")
					.then(function(resp){
						temp.$root.$emit('sendingUser', resp.data);
						if(resp.data.zaposlenjeKorisnika == "ADMIN_SISTEMA"){
							window.location.href = "#/";
						}else if(resp.data.zaposlenjeKorisnika == "FARMACEUT"){
							window.location.href = "#/";
						}else{
							window.location.href = "#/";
						}
					});
				}else{
					alert(response.data);
				}
    		});
		}  
	},
	mounted () {
		this.user = { uloga: "GOST"};
		let self = this;
        this.$root.$on('sendingUser', (data) => {
			self.user = data;
		});
    }
});