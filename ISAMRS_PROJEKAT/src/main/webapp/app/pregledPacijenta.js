Vue.component("pojedinacni-pacijent", {
	data: function () {
		return {
			spec : {korisnik:{lokacija:{}}},
			korisnik:{},
		}
},
methods:{
	zavrsiZakazivanje : function(){
	let datum = $("input[name=datum]").val();
	let vrijeme = $("input[name=vrijeme]").val();
	let noviPregled = {izvjestaj:"", datum : datum, vrijeme: vrijeme, trajanje: 45, cijena: 5000 };
	
	axios.post("/api/dermatolog/zakaziNovi/"+this.pregled.apoteka.id+"/"+this.pregled.zaposleni.id+"/"+this.pacijent.korisnik.id, noviPregled).then(data => {
		toast(data.data);
	});	
},
},
template: ` 
<div align = center style="width:50%">
	
	<h1>Pregled pacijenta id: {{$route.params.spec}}</h1>
	<br/>
	<table class="table table-hover">
		<thead>
			<th>
			<tr scope="col">Id:\t{{spec.korisnik.id}}</tr>
			<tr scope="col">Ime:\t{{spec.korisnik.ime}}</tr>
			<tr scope="col">Prezime:\t{{spec.korisnik.prezime}}</tr>
			<tr scope="col">Email:\t{{spec.korisnik.email}}</tr>
			<tr scope="col">Telefon:\t{{spec.korisnik.telefon}}</tr>
			<tr scope="col">Lokacija:\t{{spec.korisnik.lokacija.ulica}}</tr>
			</th>
		   </thead>
	 </table>		
	</div>		  
`
,
mounted: function() {
	axios
		.get("api/pacijenti/spec/" + this.$route.params.spec)
		.then(response => {
			this.spec = response.data;
		
			axios
			.get("api/users/currentUser")
			.then(response => {
				this.korisnik = response.data;
				
			});
	});
}
});