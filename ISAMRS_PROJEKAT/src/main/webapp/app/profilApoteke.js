Vue.component("profil-apoteke", {
	data: function () {
		    return {
				korisnik: {},
				apoteka : {
					naziv: "",
                    lokacija: {ulica: ""},
                    opis: "",
					ocena: 0
                },
				farmaceuti: [],
				dermatolozi: [],
				searchParams: {idApoteke: 0, ime : "", prezime: "", startOcena: 1, endOcena: 5, kriterijumSortiranja: "IME", opadajuce: false},
				pregledi: [],
		    }
	},
	template: ` 
	<div align = center style="width: 75% sm;">
		
		<h1>Profil apoteke</h1>
		<div id="mySidebar" class="sidebar">
		  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
		  <table>
			  <tr><td colspan=2 ><input type="text" name="naziv" placeholder="Ime farmaceuta" v-model="searchParams.ime" /></td></tr>
			  <tr><td colspan=2 ><input type="text" name="lokacija" placeholder="Prezime farmaceuta" v-model="searchParams.prezime" /></td></tr>
			  <tr><td style="color:white">Ocena od:</td> <td><input type="number" min="0" max="5" name="startOcena" v-model="searchParams.startOcena" ></td></tr>
			  <tr><td style="color:white">Ocena do:</td> <td><input type="number" min="1" max="5" name="endOcena" v-model="searchParams.endOcena" ></td></tr>
			  <tr><td style="color:white">Sortiraj po:</td> 
			  		<td>
				  		<select name="tip" id="kriterijum" v-model="searchParams.kriterijumSortiranja" >
						  <option value="IME">IME</option>
						  <option value="PREZIME">PREZIME</option>
						  <option value="OCENA">OCENA</option>
						</select>
					</td></tr>
			  <tr><td style="color:white">Sortiraj opadajuce:</td> <td><input type="checkbox" name="opadajuce" v-model="searchParams.opadajuce" ></td></tr>
			  <tr><td colspan=2 align=center ><input type="button" name="search" value="Pretrazi" v-on:click="searchPharmacists()" /></td></tr>
		  </table>
		</div>
		
		<br/>
        <div style="display: inline-block; margin-right: 50px">
	        <table>
	            <tr><td><h2>Naziv: </h2></td><td><input type="text" v-model="apoteka.naziv"/></td></tr>
	            <tr><td><h2>Opis: </h2></td><td><textarea rows="6" name="opis">{{apoteka.opis}}</textarea></td></tr>
	            <tr><td><h2>Adresa: </h2></td><td><textarea rows="3" disabled>{{apoteka.lokacija.ulica}}</textarea></td></tr>
				<tr><td><h2>Cena savetovanja: </h2></td><td><input type="number" v-model="apoteka.cena"/></td></tr>
				<tr><td><h2>Ocena: </h2></td><td><input type="text" v-model="apoteka.ocena.toFixed(2)" disabled/></td></tr>
	        </table>
	        <br/>
			<input type="button" value="Sacuvaj" v-on:click="saveData()"/>
			<br/>
			<br/>
		</div>
        <div id="map" class="map"></div>

		<br><br>
		<h2>Zaposleni farmaceuti</h2>
		
		<div id="main">
		  <button class="openbtn" onclick="openNav()">&#9776; Pretraga</button>
		</div>

		<br>
		<br>

	<table class="table table-hover" style="width: 50%" v-bind:hidden="farmaceuti.length == 0">
	 <thead>
		<tr bgcolor="#90a4ae">
			<th>Ime</th>
			<th>Prezime</th>
			<th>Ocena</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
	<tr v-for="s in farmaceuti">
		<td>{{s.ime}}</td>
		<td>{{s.prezime}}</td>
		<td>{{s.ocena}}</td>
		<td><input type="button" class="button1" value="Ukloni" v-on:click="removePharmacist(s)"/></td>
	</tr>
	</tbody>
	</table>

	<br>
	<br>

	<h2>Zaposleni dermatolozi</h2>
	<table class="table table-hover" style="width: 50%" >
	 <thead>
		<tr bgcolor="#90a4ae">
			<th>Ime</th>
			<th>Prezime</th>
			<th>Ocena</th>
			<td></td>
		</tr>
	</thead>
	<tbody>
	<tr v-for="s in dermatolozi">
		<td>{{s.ime}}</td>
		<td>{{s.prezime}}</td>
		<td>{{s.ocjena.toFixed(2)}}</td>
		<td><input type="button" class="button1" value="Ukloni" v-on:click="removeDermatologist(s)"/></td>
	</tr>
	</tbody>
	</table>

	<br>
	<br>

	<div align = center style="width: 75% sm;" v-bind:hidden="pregledi.length == 0">
	<h2>Slobodni termini pregleda</h2>
	<table class="table table-hover" style="width: 60%">
		 <thead>
			<tr bgcolor="#90a4ae">
				<th>Dermatolog</th>
				<th>Datum termina</th>
				<th>Vreme</th>
				<th>Cena</th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		<tr v-for="p in pregledi">
			<td>{{p.zaposleni.ime}} {{p.zaposleni.prezime}}</td>
			<td>{{p.datum}}</td>
			<td>{{p.vrijeme}}</td>
			<td><input type="number" v-model="p.cijena"/></td>
			<td><input type="button" class="button1" value="Azuriraj" v-on:click="saveExamination(p)"/></td>
			<td><input type="button" class="button1" value="Ukloni" v-on:click="removeExamination(p)"/></td>
		</tr>
		</tbody>
	</table>
	</div>

	
	</div>		  
	`
	,
    methods : {
		saveExamination: function(e) {
			axios
			.put("api/admin/updateExaminationPrice", e)
			.then(response => {
				if (response.data == "OK") {
					toast("Uspesno azuriranje cene pregleda.");
				}
			});
		},
		removeExamination: function(e) {
			axios
			.delete("api/admin/deleteExamination/" + e.id)
			.then(response => {
				if (response.data == "OK") {
					toast("Uspesno brisanje termina.");
				}
				axios
				.get("api/admin/openExaminations/" + this.apoteka.id)
				.then(response => {
					this.pregledi = response.data;
				});
			});
		},
        reverseGeolocation: function(coords){
			let self = this;
		   	fetch('http://nominatim.openstreetmap.org/reverse?format=json&lon=' + coords[0] + '&lat=' + coords[1])
			     .then(function(response) {
			            return response.json();
			        }).then(function(json) {
			            console.log(json);
			            if (json.address.house_number == undefined){
			            	if (json.address.building == undefined){
			            		self.apoteka.lokacija.ulica = json.address.road + ", " + json.address.city;
			            	}else{
			            		self.apoteka.lokacija.ulica = json.address.road + ", " + json.address.building + ", " + json.address.city;
			            	}
			            }else{
			            	self.apoteka.lokacija.ulica = json.address.road + " " + json.address.house_number + ", " + json.address.city;
			            }
			            
			        });
		},
		showMap : function(){
			let self = this;
			
			var vectorSource = new ol.source.Vector({});
		    var vectorLayer = new ol.layer.Vector({source: vectorSource});
			
			var map = new ol.Map({
		        target: 'map',
		        layers: [
		          new ol.layer.Tile({
		            source: new ol.source.OSM()
		          }),vectorLayer
		        ],
		        view: new ol.View({
		          center: ol.proj.fromLonLat([self.apoteka.lokacija.duzina, self.apoteka.lokacija.sirina]),
		          zoom: 11
		        })
		      });
		      
			var marker;
			  
			setMarker = function(position) {
				marker = new ol.Feature(new ol.geom.Point(ol.proj.fromLonLat(position)));
				vectorSource.addFeature(marker);
			}
			
			map.on("click", function(event){
				let position = ol.proj.toLonLat(event.coordinate);
				self.apoteka.lokacija.sirina = parseFloat(position.toString().split(",")[1]).toFixed(6);
				self.apoteka.lokacija.duzina = parseFloat(position.toString().split(",")[0]).toFixed(6);
				vectorSource.clear();
				setMarker(position);
				self.reverseGeolocation(position);
			});
		},
        saveData : function(){
            let opis = $("textarea[name=opis]").val();
            this.apoteka.opis = opis;
            axios
                .put("api/apoteke/update", this.apoteka)
                .then(response => {
                    this.apoteka = response.data;
                    toast("Uspesno azurirano.");
            }).catch(err => {
            	toast("Azuriranje nije uspelo.");
            });
        },
		searchPharmacists : function(){
			axios
				.get("api/farmaceut/searchAdmin/" + this.searchParams.idApoteke + "/?ime=" + this.searchParams.ime + "&prezime=" + this.searchParams.prezime 
				+ "&startOcena=" + this.searchParams.startOcena + "&endOcena=" + this.searchParams.endOcena + "&kriterijumSortiranja=" + this.searchParams.kriterijumSortiranja +
				 "&opadajuce=" + this.searchParams.opadajuce)
				.then(response => {
					this.farmaceuti = response.data;
				});
		},
		removePharmacist : function(f){
			axios
				.delete("api/farmaceut/brisanjeFarmaceuta/" + f.id + "/" + this.apoteka.id)
				.then(response => {
					if (response.data == "ERR") {
						toast("Nije moguce obrisati farmaceuta jer ima zakazana savetovanja.");
					} else {
						toast("Uspesno brisanje farmaceuta.");
						axios
		  				.get("api/farmaceut/apoteka/" + this.apoteka.id)
		  				.then(response => {
			  				this.farmaceuti = response.data;
		  				});
					}
				});
		},
		removeDermatologist: function(d) {
			axios
			.delete("api/admin/removeDermatologist/" + this.apoteka.id + "/" + d.id)
			.then(response => {
				if (response.data != "OK") {
					toast("Nemoguce obrisati dermatologa jer ima zakazane termine");
				} else {
					toast("Dermatolog uspesno uklonjen.");
					axios
		  			.get("api/dermatolog/apoteka/admin/" + this.apoteka.id)
		  			.then(response => {
			  			this.dermatolozi = response.data;
		  			});
				}
			});
		},
	},
	mounted: function() {
		axios
		.get("api/users/currentUser")
		.then(response => {
			this.korisnik = response.data;
		  	axios
			.get("api/apoteke/admin/" + response.data.id)
			.then(response => {
				this.apoteka = response.data;
				this.searchParams.idApoteke = this.apoteka.id;
				this.showMap();
				axios
				.get("api/admin/openExaminations/" + this.apoteka.id)
				.then(response => {
					this.pregledi = response.data;
				});
		  	});
		  	axios
		  	.get("api/farmaceut/apoteka/" + response.data.id)
		  	.then(response => {
			  	this.farmaceuti = response.data;
		  	});
		  	axios
		  	.get("api/dermatolog/apoteka/admin/" + response.data.id)
		  	.then(response => {
			  	this.dermatolozi = response.data;
		  	});
		});
    }
});