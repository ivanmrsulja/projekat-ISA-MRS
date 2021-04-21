Vue.component("profil-apoteke", {
	data: function () {
		    return {
				apoteka : {
					naziv: "",
                    lokacija: {ulica: ""},
                    opis: ""
                },
				farmaceuti: [],
				dermatolozi: [],
		    }
	},
	template: ` 
<div align = center style="width: 75% sm;">
		
		<h1>Profil apoteke</h1>
		<br/>
        <div style="display: inline-block; margin-right: 50px">
	        <table>
	            <tr><td><h2>Naziv: </h2></td><td><input type="text" v-model="apoteka.naziv"/></td></tr>
	            <tr><td><h2>Opis: </h2></td><td><textarea rows="6" name="opis">{{apoteka.opis}}</textarea></td></tr>
	            <tr><td><h2>Adresa: </h2></td><td><textarea rows="3" disabled>{{apoteka.lokacija.ulica}}</textarea></td></tr>
	        </table>
	        <br/>
			<input type="button" value="Sacuvaj" v-on:click="saveData()"/>
			<br/>
			<br/>
		</div>
        <div id="map" class="map"></div>

		<br><br>
		<h2>Zaposleni farmaceuti</h2>
	<table class="table table-hover" style="width: 50%" >
	 <thead>
		<tr bgcolor="lightgrey">
			<th>Ime</th>
			<th>Prezime</th>
		</tr>
	</thead>
	<tbody>
	<tr v-for="s in farmaceuti">
		<td>{{s.ime}}</td>
		<td>{{s.prezime}}</td>
	</tr>
	</tbody>
	</table>

	<h2>Zaposleni dermatolozi</h2>
	<table class="table table-hover" style="width: 50%" >
	 <thead>
		<tr bgcolor="lightgrey">
			<th>Ime</th>
			<th>Prezime</th>
		</tr>
	</thead>
	<tbody>
	<tr v-for="s in dermatolozi">
		<td>{{s.ime}}</td>
		<td>{{s.prezime}}</td>
	</tr>
	</tbody>
	</table>

	
</div>		  
`
	,
    methods : {
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
                    alert("Uspesno azurirano.");
            }).catch(err => {
            	alert("Azuriranje nije uspelo.");
            });
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
				this.showMap();
		  });
		  axios
		  .get("api/farmaceut/apoteka/" + response.data.id)
		  .then(response => {
			  this.farmaceuti = response.data;
		  });
		  axios
		  .get("api/dermatolog/apoteka/" + response.data.id)
		  .then(response => {
			  this.dermatolozi = response.data;
		  });
		});
    }
});