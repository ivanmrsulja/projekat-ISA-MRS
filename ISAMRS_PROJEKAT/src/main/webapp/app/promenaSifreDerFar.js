Vue.component("promena-sifre-derfar", {
	data: function () {
		    return {
				user : {}
		    }
	},
	template: ` 
<div align = center>
		<h1>Promena Lozinke: </h1>
		<table>
			<tr>
				<td> <h2>Nova lozinka:</h2> </td> <td> <input type="password" name="username" /> </td>
			</tr>
			<tr>
				<td> <h2>Potvrdi lozinku:</h2> </td> <td> <input type="password" name="pass" /> </td>
			</tr>
			<tr>
				<td align=center colspan=2>
					<input type="button" class="button1" v-on:click="logUserIn()" value="Posalji" />
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
								temp.$router.push({ path: "/pregledi" });
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