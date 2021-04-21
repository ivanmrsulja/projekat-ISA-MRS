Vue.component("profil-pacijenta", {
	data: function () {
		    return {
				penali: [],
				pacijent: {korisnik: { lokacija: ""}, tip:""},
				alergije: [],
				searchPhrase: "",
				preparati: []
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
				<td> <input type="text" v-model="pacijent.korisnik.username" /> </td>
			</tr>
			<tr>
				<td> <h2>Stari password:</h2> </td>
				<td> <input type="password" v-model="pacijent.korisnik.stariPassw" /> </td>
			</tr>
			<tr>
				<td> <h2>Novi password:</h2> </td>
				<td> <input type="password" v-model="pacijent.korisnik.noviPassw" /> </td>
			</tr>
			<tr>
				<td> <h2>Ime:</h2> </td>
				<td> <input type="text" v-model="pacijent.korisnik.ime" /> </td>
			</tr>
			<tr>
				<td> <h2>Prezime:</h2> </td>
				<td> <input type="text" v-model="pacijent.korisnik.prezime" /> </td>
			</tr>
			<tr>
				<td> <h2>Email:</h2> </td>
				<td> <input type="text" v-model="pacijent.korisnik.email" disabled/> </td>
			</tr>
			<tr>
				<td> <h2>Broj telefona:</h2> </td>
				<td> <input type="text" v-model="pacijent.korisnik.telefon" /> </td>
			</tr>
			<tr>
				<td> <h2>Broj poena:</h2> </td>
				<td> <input type="text" v-model="pacijent.brojPoena" disabled/> </td>
			</tr>
			<tr>
				<td> <h2>Tip korisnika:</h2> </td>
				<td> <input type="text" v-model="pacijent.tip.naziv" disabled/> </td>
			</tr>
			<tr>
				<td> <h2>Popust:</h2> </td>
				<td> <input type="text" :value="(1 - pacijent.tip.popust).toFixed(2) * 100" disabled/> % </td>
			</tr>
			<tr>
				<td> <h2>Adresa:</h2> </td>
				<td> <input type="text" v-model="pacijent.korisnik.lokacija.ulica" disabled/> </td>
			</tr>
			<tr>
				<td colspan="2" align=center> <input type="button" class="button1" value="Posalji" v-on:click="update()" /> </td>
			</tr>
		</table>
		</div>
		
		<div id="map" class="map-right"></div>
		
		<div class="penali">
		<h2>Broj penala: {{penali.length}} </h2>
		<table class="table table-hover" v-bind:hidden="penali.length == 0" >
            <thead>
            	<tr>
                <th scope="col">Redni broj</th>
                <th scope="col">Dobijen dana</th>
                </tr>
           	</thead>
            <tbody>
                <tr v-for="p in penali">
                    <td>{{p.id}}</td>
                    <td>{{p.datum}}</td>
            	</tr>           
            </tbody>
     	</table>
     	</div>
     	
     	<br/>
     	<br/>
     	<br/>
     	<br/>
     	
     	<h2> Alergije </h2>
     	
     	<table class="table table-hover" style="width: 60%">
            <thead>
            	<tr>
                <th scope="col">Naziv</th>
                <th scope="col">Proizvodjac</th>
                <th scope="col">Sastav</th>
                <th scope="col">Akcija</th>
                </tr>
           	</thead>
            <tbody>
                <tr v-for="a in alergije">
                    <td>{{a.naziv}}</td>
                    <td>{{a.proizvodjac}}</td>
                    <td>{{a.sastav}}</td>
                    <td><input type="button" class="button1" value="Ukloni" v-on:click="ukloniAlergiju(a)" /></td>
            	</tr>           
            </tbody>
     	</table>
     	
     	<br/>
     	<div class="dropdown">
		    <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdown_coins" data-toggle="dropdown" aria-haspopup="true"
		        aria-expanded="false">
		        Dodaj alergiju
		    </button>
		    <div id="menu" class="dropdown-menu" aria-labelledby="dropdown_coins">
		        <form class="px-4 py-2">
		            <input type="search" class="form-control" id="searchPhrase" placeholder="Pretraga" autofocus="autofocus" v-on:input="filter()" v-model="searchPhrase" />
		        </form>
		        <div id="menuItems">
		        	<input v-for="p in preparati" type="button" class="dropdown-item" :value="p.naziv + ' - ' + p.proizvodjac" v-on:click="dodajAlergiju(p)" />
		        </div>
		        <div id="empty" class="dropdown-header">No coins found</div>
		    </div>
		</div>
</div>		  
`
	,
	methods: {
		update: function(){
			
			if (this.pacijent.korisnik.username.trim() == "" || this.pacijent.korisnik.ime.trim() == "" || this.pacijent.korisnik.prezime.trim() == "" || this.pacijent.korisnik.email.trim() == "" || this.pacijent.korisnik.telefon.trim() == ""){
				alert("Popunite sva polja.");
				return;
			}
			
			axios
				.put("api/users/" + this.pacijent.korisnik.id, this.pacijent.korisnik)
				.then(response => {
					alert(response.data);
				})
				.catch(err => {
					alert(err.data);
				});
		},
		filter: function(){
			console.log(this.searchPhrase);
		    let collection = [];
		    let hidden = 0;
		    let items = document.getElementsByClassName("dropdown-item")
		    let length = items.length;
		    let word = $("#searchPhrase").val();
		    
		    for (let i = 0; i < length; i++) {
		    if (items[i].value.toLowerCase().includes(word)) {
		        $(items[i]).show();
		    }
		    else {
		        $(items[i]).hide()
		        hidden++;
		    }
		    }
		
		    //If all items are hidden, show the empty view
		    if (hidden === length) {
		    	$('#empty').show();
		    }
		    else {
		    	$('#empty').hide();
		    }
		},
		dodajAlergiju: function(p){
			axios
				.get("api/users/dodajAlergije/" + parseInt(this.pacijent.korisnik.id) + "/" + parseInt(p.id))
				.then(response => {
					this.alergije = response.data;
				});
		},
		ukloniAlergiju: function(a){
			axios
				.delete("api/users/alergije/" + parseInt(this.pacijent.korisnik.id) + "/" + parseInt(a.id))
				.then(response => {
					this.alergije = response.data;
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
			            		self.pacijent.korisnik.lokacija.ulica = json.address.road + ", " + json.address.city;
			            	}else{
			            		self.pacijent.korisnik.lokacija.ulica = json.address.road + ", " + json.address.building + ", " + json.address.city;
			            	}
			            }else{
			            	self.pacijent.korisnik.lokacija.ulica = json.address.road + " " + json.address.house_number + ", " + json.address.city;
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
		          center: ol.proj.fromLonLat([self.pacijent.korisnik.lokacija.duzina, self.pacijent.korisnik.lokacija.sirina]),
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
				self.pacijent.korisnik.lokacija.sirina = parseFloat(position.toString().split(",")[1]).toFixed(6);
				self.pacijent.korisnik.lokacija.duzina = parseFloat(position.toString().split(",")[0]).toFixed(6);
				vectorSource.clear();
				setMarker(position);
				self.reverseGeolocation(position);
			});
			
		}
	},
	mounted: function() {
		axios.get("/api/users/currentUser").then(data => {
            if(data.data){
                axios
					.get("api/users/penali/" + data.data.id)
					.then(response => {
						this.penali = response.data;
					});
				axios
					.get("api/users/pacijent/" + data.data.id)
					.then(response => {
						this.pacijent = response.data;
						this.showMap();
					});
				axios
					.get("api/users/alergije/" + data.data.id)
					.then(response => {
						this.alergije = response.data;
					});
            }else{
            	this.$router.push({ path: "/" });
            }
        });
        
        axios
			.get("api/preparat")
			.then(response => {
				this.preparati = response.data;
			});
		
		$('#empty').hide();
    }
});