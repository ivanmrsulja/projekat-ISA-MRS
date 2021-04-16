Vue.component("pacijent-pregled", {
	data: function () {
	    return {
	    	pregled: {},
	    	pacijent:{korisnik:{id:{},lokacija:{}}},
			spec : {korisnik:{lokacija:{}}}
	    }
},
methods: {	
	zakaziTermin : function(r) {
		window.location.href = "#/pacijenti/zapocniNoviPregled/" + r.id;
	},   
	dodajTerapiju : function(r){
		
	},
	zavrsiPregled : function(r){
		
		let text = $("input[name=textArea]").val();
		let noviPregled = {izvjestaj: text, StatusPregleda:  StatusPregleda.ZAVRSEN, TipPregleda: TipPregleda.PREGLED, LocalDate : LocalDate.parse("2020-04-07"), LocalTime: LocalTime.parse("09:00"), trajanje: 45, cijena: 5000, zaposleni:d1 ,spec , a1};
		
		axios.post("/api/apoteke/kreira").then(data => {
			if(data.data == "OK") {
				alert("Uspesno ste se zavrsili pregled!");
			}
		});
	},
	
	otkaziPregled : function(r){
		this.$router.push({ path: "/pacijenti" });
	},
},
	template: ` 
	
<div align = center style="width:75%">
		
	<table class="table table-hover">
	 <thead>
		<tr bgcolor="lightgrey">
			<th>Id</th>
			<th>Ime</th>
			<th>Prezime</th>
			<th>Email</th>
			<th>Telefon</th>
			<th>Lokacija</th>
		</tr>
	</thead>
	<tbody>
	<tr>
		<td>{{pacijent.korisnik.id }}</td>
		<td>{{pacijent.korisnik.ime }}</td>
		<td>{{pacijent.korisnik.prezime }}</td>
		<td>{{pacijent.korisnik.email }}</td>
		<td>{{pacijent.korisnik.telefon}}</td> 
		<td>{{pacijent.korisnik.lokacija.ulica}}</td>
	</tr>
	</tbody>
	</table>	
	
	<br>
	
	<table class="table table-hover">
	 <thead>
		<th>
			<tr scope="col">Izvestaj o pregledu:</tr>
			 <textarea rows="4" cols="80" name="textArea"></textarea> 
			 <br>		
			<tr>Zakazi novi termin: <input type="button" class="button1" v-on:click="zakaziTermin" value="Zakazi" /> </tr>
			<br>
			<tr>Terapija: <input type="button" class="button1" v-on:click="dodajTerapiju" value="Izaberi" /></tr>				
		</th>
	</thead>
	</table>
	<input type="button" class="button1" v-on:click="otkaziPregled(this.spec)" value="Otkazi" />
	<input type="button" class="button1" v-on:click="zavrsiPregled(this.spec)" value="Zavrsi" />
	
	
</div>		  
`,
	mounted: function() {
		axios
			.get("api/dermatolog/pregledi/" + this.$route.params.spec)
			.then(response => {
				this.pregled = response.data;
				axios
				.get("api/pacijenti/spec/" + this.pregled.pacijent.id)
				.then(response => {
					this.pacijent = response.data;
				});	
			});		
	}
});