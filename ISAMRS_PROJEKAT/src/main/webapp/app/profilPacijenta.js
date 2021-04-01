Vue.component("profil-pacijenta", {
	data: function () {
		    return {
				penali : {},
				pacijent: {korisnik:"", tip:""}
		    }
	},
	template: ` 
<div align = center style="width:75%">
		
		<h1>Moj profil</h1>
		<br/>
		<div style="display: inline-block; margin-right: 50px">
		<table>
			<tr>
				<td> <h2>Username:</h2> </td>
				<td> <input type="text" v-model="pacijent.korisnik.username" /> </td>
			</tr>
			<tr>
				<td> <h2>Stari password:</h2> </td>
				<td> <input type="password" v-model="pacijent.korisnik.stariPassw" /> </td>
			</tr>
			<tr>
				<td> <h2>Novi password:</h2> </td>
				<td> <input type="password" v-model="pacijent.korisnik.noviPassw" /> </td>
			</tr>
			<tr>
				<td> <h2>Ime:</h2> </td>
				<td> <input type="text" v-model="pacijent.korisnik.ime" /> </td>
			</tr>
			<tr>
				<td> <h2>Prezime:</h2> </td>
				<td> <input type="text" v-model="pacijent.korisnik.prezime" /> </td>
			</tr>
			<tr>
				<td> <h2>Email:</h2> </td>
				<td> <input type="text" v-model="pacijent.korisnik.email" disabled/> </td>
			</tr>
			<tr>
				<td> <h2>Broj telefona:</h2> </td>
				<td> <input type="text" v-model="pacijent.korisnik.telefon" /> </td>
			</tr>
			<tr>
				<td> <h2>Broj poena:</h2> </td>
				<td> <input type="text" v-model="pacijent.brojPoena" disabled/> </td>
			</tr>
			<tr>
				<td> <h2>Tip korisnika:</h2> </td>
				<td> <input type="text" v-model="pacijent.tip.naziv" disabled/> </td>
			</tr>
			<tr>
				<td colspan="2" align=center> <input type="button" value="Posalji"/> </td>
			</tr>
		</table>
		</div>
		
		<div class="penali">
		<h2>Broj penala: {{penali.length}} </h2>
		<table class="table table-hover" v-bind:hidden="penali.length == 0" >
            <thead>
            	<tr>
                <th scope="col">Redni broj</th>
                <th scope="col">Dobijen dana</th>
                </tr>
           	</thead>
            <tbody>
                <tr v-for="p in penali">
                    <td>{{p.id}}</td>
                    <td>{{p.datum}}</td>
            	</tr>           
            </tbody>
     	</table>
     	</div>
</div>		  
`
	,
	methods: {
		
	},
	mounted: function() {
		axios.get("/api/users/currentUser").then(data => {
            if(data.data){
                axios
					.get("api/users/penali/" + data.data.id)
					.then(response => {
						this.penali = response.data;
					});
				axios
					.get("api/users/pacijent/" + data.data.id)
					.then(response => {
						this.pacijent = response.data;
					});
            }
        });
		
    }
});