Vue.component("promena-sifre", {
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
				<td> <h2>New password:</h2> </td> <td> <input type="password" name="username" /> </td>
			</tr>
			<tr>
				<td> <h2>Confirm Password:</h2> </td> <td> <input type="password" name="pass" /> </td>
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
			let noviPass = $("input[name=username]").val();
			let pass = $("input[name=pass]").val();
			
			let temp = this;
			if (noviPass != pass) {
                toast("Password-i moraju da se podudaraju.");
                return;
            }
            
            axios
					.get("/api/users/currentUser")
					.then(function(resp){
						newUser = resp.data;
						newUser.noviPassw = noviPass;
						newUser.loggedBefore = true;
						axios.post("/api/users/changePass", newUser).then(data => {
							if(data.data == "OK") {
								toast("Uspesno ste promenili sifru!");
								if(resp.data.zaposlenjeKorisnika == "DOBAVLJAC") {
									temp.$router.push({ path: "/tab" });
								}
								else if(resp.data.zaposlenjeKorisnika == "ADMIN_SISTEMA") {
									temp.$router.push({ path: "/regDerm" });
								} else if (resp.data.zaposlenjeKorisnika == "ADMIN_APOTEKE") {
									temp.$router.push({ path: "/profileApoteke" });
								}
							}
						});
						
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