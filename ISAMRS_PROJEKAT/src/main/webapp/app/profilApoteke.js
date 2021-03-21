Vue.component("profil-apoteke", {
	data: function () {
		    return {
				apoteka : {
                    lokacija: {ulica: ""}
                }
		    }
	},
	template: ` 
<div align = center style="width:75%">
		
		<h1>Profil apoteke</h1>
		<br/>
        <table>
            <tr><td><h2>Naziv: </h2></td><td><input type="text" v-model="this.apoteka.naziv"/></td></tr>
            <tr><td><h2>Opis: </h2></td><td><textarea rows="6">{{this.apoteka.opis}}</textarea></td></tr>
            <tr><td><h2>Adresa: </h2></td><td><textarea rows="3" disabled>{{this.apoteka.lokacija.ulica}}</textarea></td></tr>
        </table>
        <br/>
		<input type="button" value="Sacuvaj" v-on:click="saveData()"/>
		<br/>
		<br/>
        <div id="map" class="map"></div>
	
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
            let self = this;
            alert("AAAAAAAAAA");
            axios
                .put("api/apoteke/update", self.apoteka)
                .then(response => {
                    self.apoteka = response.data;
            });
        } 
	},
	mounted: function() {
		axios
			.get("api/apoteke/1")
			.then(response => {
				this.apoteka = response.data;
                this.showMap()
			});
    }
});