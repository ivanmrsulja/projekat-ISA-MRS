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
			let user = $("input[name=username]").val();
			let pass = $("input[name=pass]").val();
			
			let temp = this;
			
			axios
    		.get("api/users/login?user="+user+"&pass="+pass)
    		.then(function(response){
				if(response.data == "OK"){
					axios
					.get("/api/users/currentUser")
					.then(function(resp){
						temp.$root.$emit('sendingUser', resp.data);
						if(resp.data.zaposlenjeKorisnika == "ADMIN_APOTEKE"){
							temp.$router.push({ path: "/profileApoteke" });
						}else if(resp.data.zaposlenjeKorisnika == "FARMACEUT"){
							temp.$router.push({ path: "/farmaceuti" });
						}else if(resp.data.zaposlenjeKorisnika == "DOBAVLJAC"){
							if(resp.data.loggedBefore) {
								temp.$router.push({ path: "/tab" });
							} else {
								temp.$router.push({ path: "/promeniSifru" });
							}
						}else if(resp.data.zaposlenjeKorisnika == "DERMATOLOG"){
							temp.$router.push({ path: "/dermatolozi" });
						}else if(resp.data.zaposlenjeKorisnika == "PACIJENT"){
							temp.$router.push({ path: "/apoteke/0" });
						}else {
							if(resp.data.loggedBefore) {
								temp.$router.push({ path: "/regDerm" });
							} else {
								temp.$router.push({ path: "/promeniSifru" });
							}
						}
						
					});
				}else{
					alert(response.data);
				}
    		});
		}  
	},
	mounted () {
		this.user = { zaposlenjeKorisnika: "GOST"};
		let self = this;
        this.$root.$on('sendingUser', (data) => {
			self.user = data;
		});
    }
});