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
					<input value="Registruj tip" type="button" name="regBtn" v-on:click="registerUser()"/> 
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
                alert("Popunite sva polja.");
                return;
            }
            if (bod < 0) {
                alert("Ne sme biti negativan broj bodova.");
                return;
            }
            if (pop < 0 || pop > 100) {
                alert("Popust mora biti izmedju 0 i 100 posto");
                return;
            }

            let newUser = { naziv: naziv, bodovi: bod, popust: pop};
            console.log(newUser);
            axios.post("/api/admin/registerType", newUser).then(data => {
                if (data.data == "OK") {
                    alert("Uspesno ste registrovali novog tipa!");
                }
            });
		}
	},
	mounted () {
        //this.showMap();
        //api/admin/registerType
    }
});