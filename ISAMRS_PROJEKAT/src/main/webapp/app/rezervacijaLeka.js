Vue.component("rezervacija-leka", {
	data: function () {
		    return {
				korisnik: {},
				rezervacija:{}
		    }
	},
	methods : { 
		Pretrazi : function () {
			let rezId = $("input[name=brojRezervacije]").val();
			
            axios
            .get("api/farmaceut/rezervacijaLeka/" + this.korisnik.id+"/"+rezId )
            .then(response => {
                this.rezervacija = response.data;
                if(this.rezervacija.id==rezId)
                	toast("trazena rezervacija postoji");
            });
            }
		},
	template: ` 
	<div align = center>
		<h1>Provera rezervacije leka: </h1>
		<br>
		<table>
			<tr>
				<td> <h2>Jedinstveni broj rezervacije: </h2> </td> <td> <input type="text" name="brojRezervacije" /> </td>
			</tr>
			
			<tr>
				<td align=center colspan=2>
					<input type="button" value="Pretrazi" v-on:click="Pretrazi"/>
				</td>
			</tr>
		</table>	
</div>		
`
	,
	mounted: function() {
		axios.get("/api/users/currentUser").then(response => {
            if(response.data){
            	this.korisnik = response.data;
            }
        });
    }
});