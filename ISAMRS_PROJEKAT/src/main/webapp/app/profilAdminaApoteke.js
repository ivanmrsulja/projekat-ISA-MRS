Vue.component("profil-admina-apoteke", {
	data: function () {
		    return {
				admin_apoteke: {korisnik: { lokacija: ""}},
		    }
	},
	template: ` 
<div align = center class="col">
		
		<h1>Moj profil</h1>
		<br/>
		<div style="display: inline-block; margin-right: 10px">
		<table>
			<tr>
				<td> <h2>Username:</h2> </td>
				<td> <input type="text" v-model="admin_apoteke.korisnik.username" /> </td>
			</tr>
			<tr>
				<td> <h2>Stari password:</h2> </td>
				<td> <input type="password" v-model="admin_apoteke.korisnik.stariPassw" /> </td>
			</tr>
			<tr>
				<td> <h2>Novi password:</h2> </td>
				<td> <input type="password" v-model="admin_apoteke.korisnik.noviPassw" /> </td>
			</tr>
			<tr>
				<td> <h2>Ime:</h2> </td>
				<td> <input type="text" v-model="admin_apoteke.korisnik.ime" /> </td>
			</tr>
			<tr>
				<td> <h2>Prezime:</h2> </td>
				<td> <input type="text" v-model="admin_apoteke.korisnik.prezime" /> </td>
			</tr>
			<tr>
				<td> <h2>Email:</h2> </td>
				<td> <input type="text" v-model="admin_apoteke.korisnik.email" disabled/> </td>
			</tr>
			<tr>
				<td> <h2>Broj telefona:</h2> </td>
				<td> <input type="text" v-model="admin_apoteke.korisnik.telefon" /> </td>
			</tr>
			<tr>
				<td> <h2>Adresa:</h2> </td>
				<td> <input type="text" v-model="admin_apoteke.korisnik.lokacija.ulica" disabled/> </td>
			</tr>
			<tr>
				<td colspan="2" align=center> <input type="button" class="button1" value="Posalji" v-on:click="update()" /> </td>
			</tr>
		</table>
		</div>
		
		<div id="map" class="map-right"></div>
		
</div>		  
`
    ,
    methods: {
		update: function(){
			
			if (this.admin_apoteke.korisnik.username.trim() == "" || this.admin_apoteke.korisnik.ime.trim() == "" || this.admin_apoteke.korisnik.prezime.trim() == "" || this.admin_apoteke.korisnik.email.trim() == "" || this.admin_apoteke.korisnik.telefon.trim() == ""){
				toast("Popunite sva polja.");
				return;
			}
			
			axios
				.put("api/users/updateAdmin/" + this.admin_apoteke.korisnik.id, this.admin_apoteke.korisnik)
				.then(response => {
					toast(response.data);
				})
				.catch(err => {
					toast(err.data);
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
			            		self.admin_apoteke.korisnik.lokacija.ulica = json.address.road + ", " + json.address.city;
			            	}else{
			            		self.admin_apoteke.korisnik.lokacija.ulica = json.address.road + ", " + json.address.building + ", " + json.address.city;
			            	}
			            }else{
			            	self.admin_apoteke.korisnik.lokacija.ulica = json.address.road + " " + json.address.house_number + ", " + json.address.city;
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
		          center: ol.proj.fromLonLat([self.admin_apoteke.korisnik.lokacija.duzina, self.admin_apoteke.korisnik.lokacija.sirina]),
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
				self.admin_apoteke.korisnik.lokacija.sirina = parseFloat(position.toString().split(",")[1]).toFixed(6);
				self.admin_apoteke.korisnik.lokacija.duzina = parseFloat(position.toString().split(",")[0]).toFixed(6);
				vectorSource.clear();
				setMarker(position);
				self.reverseGeolocation(position);
			});
			
		},
    },
    mounted: function() {
		axios.get("/api/users/currentUser").then(data => {
            if(data.data){
				axios
					.get("api/users/admin_apoteke/" + data.data.id)
					.then(response => {
						this.admin_apoteke = response.data;
						this.showMap();
					});
            }else{
            	this.$router.push({ path: "/" });
            }
        });
    }
});