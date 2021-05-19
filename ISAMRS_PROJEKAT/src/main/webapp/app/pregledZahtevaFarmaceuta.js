Vue.component("pregled-zahteva-farmaceuta", {
	data: function () {
		    return {
				apoteka : {
					naziv: "",
                    lokacija: {ulica: ""},
                    opis: "",
					ocena: 0
                },
				farmaceuti: [],
                farmaceutiIds: [],
                zahtevi: [],
				odbijen: false,
				obrazlozenje: "",
				aktuelniZahtev: "",
		    }
	},
	template: ` 
<div align = center style="width: 75% sm;">
		
		<h1 v-bind:hidden="odbijen">Pregled zahteva</h1>

    <table class="table table-hover" style="width: 50%" v-bind:hidden="zahtevi.length == 0 || odbijen">
	<thead>
		<tr bgcolor="#90a4ae">
			<th>Farmaceut</th>
			<th>Tip</th>
			<th>Status</th>
			<th>Pocetak</th>
			<th>Kraj</th>
			<th></th>
            <th></th>
		</tr>
	</thead>
	<tbody>
	<tr v-for="z in zahtevi">
		<td>{{z.korisnik.ime + ' ' + z.korisnik.prezime}}</td>
		<td>{{z.tip}}</td>
		<td>{{z.status}}</td>
		<td>{{z.pocetak}}</td>
		<td>{{z.kraj}}</td>
		<td v-bind:hidden="z.status != 'CEKANJE'"><input type="button" class="button1" value="Odobri" v-on:click="approveRequest(z)"/></td>
		<td v-bind:hidden="z.status != 'CEKANJE'"><input type="button" class="button1" value="Odbij" v-on:click="rejectRequest(z)"/></td>
	</tr>
	</tbody>
	</table>

	<div align = center style ="width: 75% sm;" v-bind:hidden="!this.odbijen">
		<h2>Obrazlozenje: </h2>
		<textarea rows="6" cols="25" id="obrazlozenje" name="obrazlozenje" v-model="obrazlozenje"></textarea>
		<br>
		<input type="button" class="button1" value="Posalji" v-bind:hidden="obrazlozenje == ''" v-on:click="sendRejection(aktuelniZahtev)"/>
	</div>

</div>		  
`
	,
    methods : {
		approveRequest : function(z){
			z.status = 0;
			razlog = "we";
			axios
			.put("api/zahtev/update/" + razlog, z)
			.then(response => {
				axios
                .get("api/zahtev/apoteka/" + this.apoteka.id)
                .then(response => {
                    this.zahtevi = response.data;
                });
			}).catch(err => {
            	alert("Azuriranje nije uspelo.");
            });
		},
		rejectRequest : function(z){
			this.aktuelniZahtev = z;
			this.odbijen = true;
		},
		sendRejection : function(z){
			z.status = 1;
            let razlog = $("textarea[name=obrazlozenje]").val();
			axios
			.put("api/zahtev/update/" + razlog, z)
			.then(response => {
				axios
                .get("api/zahtev/apoteka/" + this.apoteka.id)
                .then(response => {
                    this.zahtevi = response.data;
                });
			}).catch(err => {
            	alert("Azuriranje nije uspelo.");
            });
			this.odbijen = false;
		}
    },
    mounted: function() {
        axios
		.get("api/users/currentUser")
		.then(response => {
		  axios
			.get("api/apoteke/admin/" + response.data.id)
			.then(response => {
				this.apoteka = response.data;
                axios
                .get("api/zahtev/apoteka/" + response.data.id)
                .then(response => {
                    this.zahtevi = response.data;
                });
		  });
		  axios
		  .get("api/farmaceut/apoteka/" + response.data.id)
		  .then(response => {
			  this.farmaceuti = response.data;
              this.farmaceutiIds = this.farmaceuti.map(function (temp_farmaceut) {
                  return temp_farmaceut.id;
              });
		  });
		});
    }
});