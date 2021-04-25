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
	zavrsiPregled : function(){
		
		let text = $("input[name=textArea]").val();
		let noviPregled = {izvjestaj: text, StatusPregleda:  StatusPregleda.ZAVRSEN, TipPregleda: TipPregleda.PREGLED, LocalDate : LocalDate.parse("2020-04-07"), LocalTime: LocalTime.parse("09:00"), trajanje: 45, cijena: 5000, zaposleni:d1 ,spec , a1};
		
		axios.post("/api/dermatolog/zavrsi/"+this.pregled.id).then(data => {
			if(data.data == "OK") {
				alert("Uspesno ste se zavrsili pregled!");
			}
		});
	},
		zavrsiZakazivanje : function(){
			let datum = $("input[name=datum]").val();
			let vrijeme = $("input[name=vrijeme]").val();
			let noviPregled = {izvjestaj:"", datum : datum, vrijeme: vrijeme, trajanje: 45, cijena: 5000 };
			
			axios.post("/api/dermatolog/zakaziNovi/"+this.pregled.apoteka.id+"/"+this.pregled.zaposleni.id+"/"+this.pacijent.korisnik.id, noviPregled).then(data => {
				alert(data.data);
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
			<tr> <button type="button" class="button1" data-toggle="modal" data-target="#exampleModalCenter">Zakazi novi termin
</button> </tr>
			<br>
			<tr>Terapija: <input type="button" class="button1" v-on:click="dodajTerapiju" value="Izaberi" /></tr>				
		</th>
	</thead>
	</table>
	<input type="button" class="button1" v-on:click="otkaziPregled(this.spec)" value="Otkazi" />
	<input type="button" class="button1" v-on:click="zavrsiPregled" value="Zavrsi" />
	


	<!-- Modal -->
	<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle">Modal title</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        	
			<table class="table table-hover">
			 <thead>
				<th>
			
				<br>
					<tr>Datum: <input type="date" name="datum"/></tr>
				<br>
					<tr>Vrijeme: <input type="time" name="vrijeme"/></tr>				
				</th>
			</thead>
			</table>
			
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Otkazi</button>
	        <button type="button" class="btn btn-primary " v-on:click="zavrsiZakazivanje()">Zakazi</button>
	      </div>
	    </div>
	  </div>
	</div>
	
</div>		  
`,
	mounted: function() {
		axios
			.get("api/dermatolog/pregledi/" + this.$route.params.spec)
			.then(response => {
				this.pregled = response.data;
				console.log(response.data);
				axios
				.get("api/pacijenti/spec/" + this.pregled.pacijent.id)
				.then(response => {
					this.pacijent = response.data;
				});	
			});		
	}
});