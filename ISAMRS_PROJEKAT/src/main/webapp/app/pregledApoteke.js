Vue.component("pregled-apoteke", {
	data: function () {
		    return {
				apoteka : {
					naziv: "",
                    lokacija: {ulica: ""},
                    opis: ""
                },
                farmaceuti: [],
                dermatolozi: [],
                pregledi: [],
				lekovi: [],
                korisnik: null
		    }
	},
	template: ` 
<div align = center style="width:75%">
		
		<h1>Pregled apoteke</h1>
		<br/>
        <table>
            <tr><td><h2>Naziv: </h2></td><td><h2>{{apoteka.naziv}}</h2></td></tr>
            <tr><td><h2>Opis: </h2></td><td><h2>{{apoteka.opis}}</h2></td></tr>
            <tr><td><h2>Adresa: </h2></td><td><h2>{{apoteka.lokacija.ulica}}</h2></td></tr>
            <tr><td><h2>Ocena: </h2></td><td><h2>{{apoteka.ocena}}</h2></td></tr>
        </table>
        <br/>
        <div id="map" class="map"></div>

        <br><br>

     <h2>Zaposleni farmaceuti</h2>
     <table class="table table-hover" style="width: 50%" >
		 <thead>
			<tr bgcolor="#90a4ae">
				<th>Ime</th>
				<th>Prezime</th>
				<th>Email</th>
			</tr>
		</thead>
		<tbody>
		<tr v-for="f in farmaceuti">
			<td>{{f.ime}}</td>
			<td>{{f.prezime}}</td>
			<td>{{f.email}}</td>
		</tr>
		</tbody>
	</table>
	
	<br/><br/>
	
     <h2>Zaposleni dermatolozi</h2>
     <table class="table table-hover" style="width: 50%" >
		 <thead>
			<tr bgcolor="#90a4ae">
				<th>Ime</th>
				<th>Prezime</th>
				<th>Email</th>
			</tr>
		</thead>
		<tbody>
		<tr v-for="d in dermatolozi">
			<td>{{d.ime}}</td>
			<td>{{d.prezime}}</td>
			<td>{{d.email}}</td>
		</tr>
		</tbody>
	</table>
	
	<br/><br/>

	<h2>Dostupni lekovi</h2>
     <table class="table table-hover" style="width: 50%" >
		 <thead>
			<tr bgcolor="#90a4ae">
				<th>Ime preparata</th>
				<th>Proizvodjac</th>
			</tr>
		</thead>
		<tbody>
		<tr v-for="l in lekovi">
			<td>{{l.naziv}}</td>
			<td>{{l.proizvodjac}}</td>
		</tr>
		</tbody>
	</table>
	
	<br/><br/>
	
	<h2>Slobodni termini pregleda</h2>
	<div class="dropdown" v-bind:hidden="pregledi.length == 0" >
		  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    Sortiraj po:
		  </button>
		  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		    <a class="dropdown-item" v-on:click="sortiraj('cena')">CENA</a>
		    <a class="dropdown-item" v-on:click="sortiraj('ocena')">OCENA DERMATOLOGA</a>
		  </div>
	</div>
	<br/>
     <table class="table table-hover" style="width: 60%" >
		 <thead>
			<tr  bgcolor="#90a4ae">
				<th>Dermatolog</th>
				<th>Datum termina</th>
				<th>Vreme</th>
				<th>Cena</th>
				<th>Ocena dermatologa</th>
				<th>Akcija</th>
			</tr>
		</thead>
		<tbody>
		<tr v-for="p in pregledi">
			<td>{{p.zaposleni.ime}} {{p.zaposleni.prezime}}</td>
			<td>{{p.datum}}</td>
			<td>{{p.vrijeme}}</td>
			<td>{{p.cijena}}</td>
			<td>{{p.ocena}} </td>
			<td><input type="button" value="Zakazi pregled" v-on:click="zakazi(p)" v-bind:disabled="!korisnik" /></td>
		</tr>
		</tbody>
	</table>

	
</div>		  
`
    ,
    methods: {
        showMap: function(){
            let self = this;
            var map = new ol.Map({
                target: 'map',
                layers: [
                  new ol.layer.Tile({
                    source: new ol.source.OSM()
                  })
                ],
                view: new ol.View({
                  center: ol.proj.fromLonLat([self.apoteka.lokacija.duzina, self.apoteka.lokacija.sirina]),
                  zoom: 16
                })
              });
             var layer = new ol.layer.Vector({
                 source: new ol.source.Vector({
                     features: [
                         new ol.Feature({
                             geometry: new ol.geom.Point(ol.proj.fromLonLat([self.apoteka.lokacija.duzina, self.apoteka.lokacija.sirina]))
                         })
                     ]
                 })
             });
             map.addLayer(layer);
        },
        zakazi: function(p){
        	axios
	        .get("/api/users/currentUser")
	        .then(response => {
	            if(response.data == null){
	            	alert("Niste ulogovani");
	            }else{
	            console.log(response.data);
	            	axios
			        .put("/api/apoteke/zakaziPregled/" + p.id + "/" + response.data.id)
			        .then(response => {
			        	if(response.data){
			            	this.pregledi = response.data;
			            } else {
			            	alert("Doslo je do greske prilikom zakazivanja.");
			            	axios
					        .get("/api/apoteke/pregledi/" + this.$route.params.id)
					        .then(response => {
					            this.pregledi = response.data;
					        });
			            }
			        });
	            }
	        });
        },
        sortiraj: function(criteria){
        	axios
	        .get("/api/apoteke/pregledi/" + this.$route.params.id + "?criteria=" + criteria)
	        .then(response => {
	            this.pregledi = response.data;
	        });
        }
    },
    mounted: function(){
        axios
        .get("/api/apoteke/" + this.$route.params.id)
        .then(response => {
            this.apoteka = response.data;
            this.showMap();
        });
        axios
        .get("/api/farmaceut/apoteka/" + this.$route.params.id)
        .then(response => {
            this.farmaceuti = response.data;
        });
        axios
        .get("/api/dermatolog/apoteka/" + this.$route.params.id)
        .then(response => {
            this.dermatolozi = response.data;
        });
        axios
        .get("/api/apoteke/pregledi/" + this.$route.params.id + "?criteria=none")
        .then(response => {
            this.pregledi = response.data;
        });
        axios
        .get("/api/users/currentUser")
        .then(response => {
            this.korisnik = response.data;
        });
		axios
		.get("/api/preparat/apoteka/" + this.$route.params.id)
		.then(response => {
			this.lekovi = response.data;
		})
    }
});