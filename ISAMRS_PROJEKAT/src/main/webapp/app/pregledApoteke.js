Vue.component("pregled-apoteke", {
	data: function () {
		    return {
				apoteka : {
					id: 0,
					naziv: "",
                    lokacija: {ulica: ""},
                    opis: "",
                    ocena: 0.0
                },
                farmaceuti: [],
                dermatolozi: [],
                pregledi: [],
				lekovi: [],
                korisnik: {zaposlenjeKorisnika: "GOST"},
                ocena: 0,
                ocenjivo: false
		    }
	},
	template: ` 
<div align = center style="width:75% sm">
		
		<h1>Pregled apoteke</h1>
		<input type="button" class="button1" value="Pretplati se" v-bind:hidden="korisnik.zaposlenjeKorisnika != 'PACIJENT'" v-on:click="pretplatiSe()">
		<br/>
        <table>
            <tr><td><h2>Naziv: </h2></td><td><h2>{{apoteka.naziv}}</h2></td></tr>
            <tr><td><h2>Opis: </h2></td><td><h2>{{apoteka.opis}}</h2></td></tr>
            <tr><td><h2>Adresa: </h2></td><td><h2>{{apoteka.lokacija.ulica}}</h2></td></tr>
            <tr><td><h2>Ocena: </h2></td><td><h2 v-bind:hidden="apoteka.ocena == 0" >{{apoteka.ocena.toFixed(2)}}</h2><h2 v-bind:hidden="apoteka.ocena != 0">Nije ocenjivano</h2></td></tr>
            <tr>
            	<td> <h2 v-bind:hidden="ocenjivo == false" >Moja ocena: </h2></td>
	            <td style="padding: 20px">
	            	<div class="star-rating" v-on:click="clickStar()" v-bind:hidden="ocenjivo == false">
				        <span class="fa fa-star-o" data-rating="1" ></span>
				        <span class="fa fa-star-o" data-rating="2"></span>
				        <span class="fa fa-star-o" data-rating="3"></span>
				        <span class="fa fa-star-o" data-rating="4"></span>
				        <span class="fa fa-star-o" data-rating="5"></span>
				        <input type="hidden" name="whatever1" class="rating-value" v-model="ocena">
				    </div>
	            </td>
            </tr>
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
				<th>Ocena</th>
			</tr>
		</thead>
		<tbody>
		<tr v-for="l in lekovi">
			<td>{{l.naziv}}</td>
			<td>{{l.proizvodjac}}</td>
			<td>{{l.ocena.toFixed(2)}}</td>
		</tr>
		</tbody>
	</table>
	
	<br/><br/>
	
	<h2 v-bind:hidden=" pregledi.length == 0">Slobodni termini pregleda</h2>
	<div class="dropdown" v-bind:hidden=" pregledi.length == 0" >
		  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    Sortiraj po:
		  </button>
		  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		    <a class="dropdown-item" v-on:click="sortiraj('cena')">CENA</a>
		    <a class="dropdown-item" v-on:click="sortiraj('ocena')">OCENA DERMATOLOGA</a>
		  </div>
	</div>
	<br/>
     <table class="table table-hover" style="width: 60%" v-bind:hidden="pregledi.length == 0">
		 <thead>
			<tr bgcolor="#90a4ae">
				<th>Dermatolog</th>
				<th>Datum termina</th>
				<th>Vreme</th>
				<th>Cena</th>
				<th>Ocena dermatologa</th>
				<th v-bind:hidden="!korisnik">Akcija</th>
			</tr>
		</thead>
		<tbody>
		<tr v-for="p in pregledi">
			<td>{{p.zaposleni.ime}} {{p.zaposleni.prezime}}</td>
			<td>{{p.datum}}</td>
			<td>{{p.vrijeme}}</td>
			<td>{{p.cijena}}</td>
			<td>{{p.ocena.toFixed(2)}} </td>
			<td v-bind:hidden="!korisnik"><input type="button" class="button1" value="Zakazi pregled" v-on:click="zakazi(p)" v-bind:disabled="!korisnik"/></td>
		</tr>
		</tbody>
	</table>

	
</div>		  
`
    ,
    methods: {
		pretplatiSe : function() {
			axios
			.put("api/pacijenti/updateApoteke/" + this.korisnik.id + "/" + this.apoteka.id)
			.then(response => {
				if (response.data == "OK"){
					alert("Uspesno ste se pretplatili na akcije i promocije apoteke.");
				}
			})
		},
    	SetRatingStar: function(){
    		let self = this;
    		var $star_rating = $('.star-rating .fa');
    		return $star_rating.each(function() {
    			$star_rating.siblings('input.rating-value').val(self.ocena);
			    if (parseInt($star_rating.siblings('input.rating-value').val()) >= parseInt($(this).data('rating'))) {
			      return $(this).removeClass('fa-star-o').addClass('fa-star');
			    } else {
			      return $(this).removeClass('fa-star').addClass('fa-star-o');
			    }
			});
    	},
    	clickStar: function() {
    	  	axios
		        .put("/api/ocene/oceniApoteku/" + this.$route.params.id + "/" + this.ocena)
		        .then(response => {
		        	axios
			        .get("/api/apoteke/" + this.$route.params.id)
			        .then(response => {
			            this.apoteka = response.data;
			        });
		        });
		},
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
			            	alert(response.data);
			            } else {
			            	alert("Doslo je do greske prilikom zakazivanja.");
			            }
                        axios
                            .get("/api/apoteke/pregledi/" + this.$route.params.id + "?criteria=none")
                            .then(response => {
                                this.pregledi = response.data;
                            });
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
    	let self = this;
    	var $star_rating = $('.star-rating .fa');
    	$star_rating.on('click', function() {
    	  self.ocena = $(this).data('rating');
		  $star_rating.siblings('input.rating-value').val($(this).data('rating'));
		  return self.SetRatingStar();
		});
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
        })
        .catch(response => { console.log("a"); });
        axios
        .get("/api/users/currentUser")
        .then(response => {
            this.korisnik = response.data;
            if(this.korisnik.zaposlenjeKorisnika == "PACIJENT"){
            	axios
		        .get("/api/ocene/apoteka/" + this.$route.params.id)
		        .then(response => {
		        	if(response.data != -1){
		        		this.ocena = response.data;
		        		this.ocenjivo = true;
		        		this.SetRatingStar();
		        	}else{
		        		this.ocena = 0;
		        		this.ocenjivo = false;
		        		this.SetRatingStar();
		        	}
		        });
            }
        });
		axios
		.get("/api/preparat/apoteka/" + this.$route.params.id)
		.then(response => {
			this.lekovi = response.data;
		})
    }
});