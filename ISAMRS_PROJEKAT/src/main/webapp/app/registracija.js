Vue.component("register-user", {
	data: function () {
		    return {
		    	currentPosition: {lat: 45.252600, lon: 19.830002, adresa: "Cirpanova 51, Novi Sad"}
		    }
	},
	template: ` 
<div>
		<h1>Registracija korisnika: </h1>
		
		
		<div style="display: inline-block; margin-right: 50px">
		<table>
			<tr>
				<td> <h2>Username:</h2> </td> <td> <input type="text" name="username"/> </td>
			</tr>
			<tr>
				<td> <h2>Password:</h2> </td> <td> <input type="password" name="pass"/> </td>
			</tr>
			<tr>
				<td> <h2>Confirm password:</h2> </td> <td> <input type="password" name="passConf"/> </td>
			</tr>
			<tr>
				<td> <h2>Ime:</h2> </td> <td> <input type="text" name="ime"/> </td>
			</tr>
			<tr>
				<td> <h2>Prezime:</h2> </td> <td> <input type="text" name="prezime"/> </td>
			</tr>
			<tr>
				<td> <h2>Email:</h2> </td> <td> <input type="text" name="email"/> </td>
			</tr>
			<tr>
				<td> <h2>Broj telefona:</h2> </td> <td> <input type="text" name="telefon"/> </td>
			</tr>
			<tr>
				<td> <h2>Geografska sirina:</h2> </td> <td> <input type="number" name="sirina" v-model="currentPosition.lat" disabled/> </td>
			</tr>
			<tr>
				<td> <h2>Geografska duzina:</h2> </td> <td> <input type="number" name="duzina" v-model="currentPosition.lon" disabled/> </td>
			</tr>
			<tr>
				<td> <h2>Adresa:</h2> </td> <td> <input type="text" name="adresa" v-model="currentPosition.adresa" disabled/> </td>
			</tr>
			<tr>
				<td align=center colspan=2> 
					<input value="Registruj se" type="button" name="regBtn" v-on:click="registerUser()"/> 
				</td>
			</tr>
		</table>
		</div>
		
		<div id="map" class="map-right" ></div>
		
</div>		  
`
	, 
	methods : {
		registerUser : function () {
			let usr = $("input[name=username]").val();
			let pas = $("input[name=pass]").val();
			let pasConf = $("input[name=passConf]").val();
			let ime = $("input[name=ime]").val();
			let prz = $("input[name=prezime]").val();
			let email = $('input[name=email]').val();
			let tel = $("input[name=telefon]").val();
			
			let sir = $("input[name=sirina]").val();
			let duz = $("input[name=duzina]").val();
			let adr = $("input[name=adresa]").val();
			
			if (usr.trim() == "" || pas.trim() == "" || ime.trim() == "" || prz.trim() == "" || email.trim() == "" || tel.trim() == ""){
				alert("Popunite sva polja.");
				return;
			}
			
			if(!email.match(/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/)){
				alert("Email je u neispravnom formatu.");
				return;
			}
			
			if(pas != pasConf){
				alert("Password-i moraju da se podudaraju.");
				return;
			}
			
			let lok = {sirina: sir, duzina: duz, ulica: adr};
			
			let newUser = {username: usr, noviPassw: pas, ime: ime, prezime : prz, email: email, telefon: tel, lokacija: lok};
			
			console.log(newUser);
			axios.post("/api/users/register", newUser).then(data => {
				if(data.data == "OK") {
					alert("Uspesno ste se registrovali! Mozete se ulogovati");
				}
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
			            		self.currentPosition.adresa = json.address.road + ", " + json.address.city;
			            	}else{
			            		self.currentPosition.adresa = json.address.road + ", " + json.address.building + ", " + json.address.city;
			            	}
			            }else{
			            	self.currentPosition.adresa = json.address.road + " " + json.address.house_number + ", " + json.address.city;
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
		          center: ol.proj.fromLonLat([19.8335, 45.2671]),
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
				self.currentPosition.lat = parseFloat(position.toString().split(",")[1]).toFixed(6);
				self.currentPosition.lon = parseFloat(position.toString().split(",")[0]).toFixed(6);
				vectorSource.clear();
				setMarker(position);
				self.reverseGeolocation(position);
			});
			
		} 
	},
	mounted () {
        this.showMap();
    }
});