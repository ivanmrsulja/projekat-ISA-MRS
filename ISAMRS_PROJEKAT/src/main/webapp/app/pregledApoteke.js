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
        <table class="table table-hover">
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
        <table class="table table-hover">
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
    }
});