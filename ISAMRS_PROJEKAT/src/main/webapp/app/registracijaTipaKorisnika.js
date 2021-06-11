Vue.component("register-type", {
	data: function () {
		    return {
		    	currentPosition: {lat: 45.252600, lon: 19.830002, adresa: "Cirpanova 51, Novi Sad"}
		    }
	},
	template: ` 
<div>
		<h1>Registracija tipa korisnika: </h1>
		
		
		<div style="display: inline-block; margin-right: 50px">
		<table>
			<tr>
				<td> <h2>Naziv:</h2> </td> <td> <input type="text" name="username"/> </td>
			</tr>
			<tr>
				<td> <h2>Broj bodova:</h2> </td> <td> <input type="number" name="points"/> </td>
			</tr>
			<tr>
				<td> <h2>Popust:</h2> </td> <td> <input type="number" name="percent"/> </td>
			</tr>
			<tr>
				<td align=center colspan=2> 
					<input value="Registruj tip" type="button" class="button1" name="regBtn" v-on:click="registerUser()"/> 
				</td>
			</tr>
		</table>
		</div>
		
		
</div>		  
`
	, 
	methods : {
		registerUser : function () {
			let naziv = $("input[name=username]").val();
            let bod = parseInt($("input[name=points]").val());
            let pop = parseFloat($("input[name=percent]").val());
            if (naziv.trim() == "" || isNaN(bod) || isNaN(pop)) {
                toast("Popunite sva polja.");
                return;
            }
            if (bod < 0) {
                toast("Ne sme biti negativan broj bodova.");
                return;
            }
            if (pop < 0 || pop > 100) {
                toast("Popust mora biti izmedju 0 i 100 posto");
                return;
            }

            let newUser = { naziv: naziv, bodovi: bod, popust: pop};
            console.log(newUser);
            axios.post("/api/admin/registerType", newUser).then(data => {
                if (data.data == "OK") {
                    toast("Uspesno ste registrovali novog tipa!");
                } else {
                	toast("Tip sa tim brojem bodova vec postoji!");
                }
            });
		}
	},
	mounted () {
		let temp = this;
		axios
			.get("/api/users/currentUser")
			.then(function(resp){
				if(resp.data.zaposlenjeKorisnika == "ADMIN_APOTEKE"){
							if (resp.data.loggedBefore) {
								temp.$router.push({ path: "/profileApoteke" });
							} else {
								temp.$router.push({ path: "/promeniSifru" });
							}
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
						}else if(resp.data.zaposlenjeKorisnika == "ADMIN_SISTEMA") {
							if(!resp.data.loggedBefore) {
								temp.$router.push({ path: "/promeniSifru" });
							}
						}else {
							temp.$router.push({ path: "/" });
						}
						
					});
        //this.showMap();
        //api/admin/registerType
    }
});