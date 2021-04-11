Vue.component("pojedinacni-preparat", {
	data: function () {
		    return {
				spec : {},
				apoteke: [],
				mode: "BROWSE",
				selected: {},
				datum: {}
		    }
	},
	template: ` 
<div align = center style="width:50%">
		
		<h1>Specifikacija preparata id: {{$route.params.spec}}</h1>
		<br/>
		<table class="table table-hover">
            <thead>
            	<th>
                <tr scope="col">Id:\t{{spec.id}}</tr>
                <tr scope="col">Naziv:\t{{spec.naziv}}</tr>
                <tr scope="col">Kontraindikacije:\t{{spec.kontraindikacije}}</tr>
                <tr scope="col">Sastav:\t{{spec.sastav}}</tr>
                <tr scope="col">Preporuceni unos:\t{{spec.preporuceniUnos}}</tr>
                <tr scope="col">Oblik:\t{{spec.oblik}}</tr>
                <tr scope="col">Proizvodjac:\t{{spec.proizvodjac}}</tr>
                <tr scope="col">Rezim:\t{{spec.rezim}}</tr>
                <tr scope="col">Ocena:\t{{spec.ocena}}</tr>
                </th>
           	</thead>
     	</table>
		
		<h2 v-bind:hidden="apoteke.length == 0" >Dostupno u:</h2>
		
		<table class="table table-hover" v-bind:hidden="apoteke.length == 0">
			<thead>
				<tr bgcolor="#90a4ae">
					<th>Naziv</th>
					<th>Adresa</th>
					<th>Cena</th>
					<th>Ocena apoteke</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="a in apoteke"" v-on:click="select(a)">
		                <td>{{a.apoteka.naziv}}</td>
		                <td>{{a.apoteka.lokacija.ulica}}</td> 
		                <td>{{a.cena}}</td>
		                <td>{{a.apoteka.ocena}}</td>                               
				</tr>
			</tbody>
		</table>
		
		</br>
		
		<table v-bind:hidden="this.mode == 'BROWSE'">
			<tr><td><h4>Rezervisi u:</h4></td><td>{{selected.naziv}}</td></tr>
			<tr><td><h4>Preuzmi do:</h4></td><td><input type="date" v-model="datum" /></td></tr>
			<tr><td align=center ><input type="button" class="button1" v-on:click="rezervisi()" value="Rezervisi" /></td><td align=center ><input class="button1" type="button" value="Odustani" v-on:click="cancel()" /></td></tr>
		</table>
</div>		  
`
	,
	methods: {
		select: function(apoteka){
			this.mode = "ORDER";
			this.selected = apoteka.apoteka;
		},
		rezervisi: function(){
			axios
				.get("api/preparat/rezervisi/" + this.$route.params.spec + "/" + this.selected.id + "?datum=" + this.datum)
				.then(response => {
					alert(response.data);
				}).catch(response => {
					alert("Morate uneti datum preuzimanja.");
				});
		},
		cancel: function(){
			this.mode = "BROWSE";
			this.selected = {};
			this.datum = {}
		}
	}
	,
	mounted: function() {
		axios
			.get("api/preparat/spec/" + this.$route.params.spec)
			.then(response => {
				this.spec = response.data;
				axios
				.get("api/users/currentUser")
				.then(response => {
					if(response.data.zaposlenjeKorisnika == "PACIJENT"){
						axios
						.get("api/preparat/dostupnost/" + this.$route.params.spec)
						.then(response => {
							this.apoteke = response.data;
						});
					}
				});
			});
    }
});