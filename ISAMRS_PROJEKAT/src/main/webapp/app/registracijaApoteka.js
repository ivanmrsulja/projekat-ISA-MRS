Vue.component("register-apoteka", {
    data: function() {
        return {
            currentPosition: { lat: 45.252600, lon: 19.830002, adresa: "Cirpanova 51, Novi Sad" }
        }
    },
    template: ` 
<div>
		<h1>Registracija admina sistema: </h1>
		
		
		<div style="display: inline-block; margin-right: 50px">
		<table>
			<tr>
				<td> <h2>Naziv:</h2> </td> <td> <input type="text" name="name"/> </td>
			</tr>
			<tr>
				<td> <h2>Opis:</h2> </td> <td> <input type="text" name="opis"/> </td>
			</tr>
			<tr>
				<td> <h2>Cena:</h2> </td> <td> <input type="text" name="cena"/> </td>
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
					<input value="Registruj se" type="button" name="regBtn" v-on:click="registerPhar()"/> 
				</td>
			</tr>
		</table>
		</div>
		
		<div id="map" class="map-right" ></div>
		
</div>		  
`,
    methods: {
        registerPhar: function() {
            let naz = $("input[name=name]").val();
            let op = $("input[name=opis]").val();
            let cen = $("input[name=cena]").val();

            let sir = $("input[name=sirina]").val();
            let duz = $("input[name=duzina]").val();
            let adr = $("input[name=adresa]").val();

            if (naz.trim() == "" || op.trim() == "" || cen.trim() == "") {
                alert("Popunite sva polja.");
                return;
            }

            let lok = { sirina: sir, duzina: duz, ulica: adr };

            let newPhar = { naziv: naz, opis: op, cena: parseFloat(cen), lokacija: lok, ocena: 0.0};

            console.log(newPhar);
            axios.post("/api/apoteke/register", newPhar).then(data => {
                if (data.data == "OK") {
                    alert("Uspesno ste registrovali admina sistema! Moze se ulogovati");
                }
            });
        },
        reverseGeolocation: function(coords) {
            let self = this;
            fetch('http://nominatim.openstreetmap.org/reverse?format=json&lon=' + coords[0] + '&lat=' + coords[1])
                .then(function(response) {
                    return response.json();
                }).then(function(json) {
                    console.log(json);
                    if (json.address.house_number == undefined) {
                        if (json.address.building == undefined) {
                            self.currentPosition.adresa = json.address.road + ", " + json.address.city;
                        } else {
                            self.currentPosition.adresa = json.address.road + ", " + json.address.building + ", " + json.address.city;
                        }
                    } else {
                        self.currentPosition.adresa = json.address.road + " " + json.address.house_number + ", " + json.address.city;
                    }

                });
        },
        showMap: function() {
            let self = this;

            var vectorSource = new ol.source.Vector({});
            var vectorLayer = new ol.layer.Vector({ source: vectorSource });

            var map = new ol.Map({
                target: 'map',
                layers: [
                    new ol.layer.Tile({
                        source: new ol.source.OSM()
                    }), vectorLayer
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

            map.on("click", function(event) {
                let position = ol.proj.toLonLat(event.coordinate);
                self.currentPosition.lat = parseFloat(position.toString().split(",")[1]).toFixed(6);
                self.currentPosition.lon = parseFloat(position.toString().split(",")[0]).toFixed(6);
                vectorSource.clear();
                setMarker(position);
                self.reverseGeolocation(position);
            });

        }
    },
    mounted() {
        this.showMap();
    }
});