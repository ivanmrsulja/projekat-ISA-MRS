Vue.component("dermatolozi-admin-apoteke", {
	data: function () {
		    return {
                korisnik: {},
                apoteka: {},
				dermatolozi: [],
				searchParams: {ime : "", prezime: "", startOcena: 1, endOcena: 5, kriterijumSortiranja: "IME", opadajuce: false},
                selected: false,
                pocetakRadnogVremena: null,
                krajRadnogVremena: null,
                hidden: false,
                aktuelniDermatolog: {},
		    }
	},
	template: ` 
	<div align = center style="width: 75% sm;">
        <div id="mySidebar" class="sidebar">
		  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
		  <table>
			  <tr><td colspan=2 ><input type="text" name="ime" placeholder="Ime" v-model="searchParams.ime" /></td></tr>
			  <tr><td colspan=2 ><input type="text" name="prezime" placeholder="Prezime" v-model="searchParams.prezime" /></td></tr>
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
			  <tr><td colspan=2 align=center ><input type="button" name="search" value="Pretrazi dermatologe" v-on:click="searchDermatologists()" /></td></tr>
		  </table>
		</div>
	    <h2>Dermatolozi</h2>
	
	        <div id="main">
			  <button class="openbtn" onclick="openNav()">&#9776; Pretraga</button>
			</div>
	
		<table class="table table-hover" style="width: 50%" v-bind:hidden="dermatolozi.length == 0 || hidden">
		 <thead>
			<tr bgcolor="lightgrey">
				<th>Ime</th>
				<th>Prezime</th>
				<th>Ocena</th>
	            <th>Apoteke</th>
                <th></th>
			</tr>
		</thead>
		<tbody>
		<tr v-for="s in dermatolozi">
			<td>{{s.ime}}</td>
			<td>{{s.prezime}}</td>
			<td>{{s.ocjena.toFixed(2)}}</td>
	        <td>{{s.naziviApoteka.join(', ')}}</td>
            <td><input type="button" class="button1" value="Zaposli" v-on:click="employDermatologist(s)"/></td>
		</tr>
		</tbody>
		</table>
        <br>
        <br>
        <div v-bind:hidden="dermatolozi.length == 0">
        <label>Pocetak radnog vremena</label>
        <br>
        <input type="time" v-model="pocetakRadnogVremena"/>
        <br>
        <label>Kraj radnog vremena</label>
        <br>
        <input type="time" v-model="krajRadnogVremena"/>
        <br>
        <br>
        <input type="button" class="button1" value="Posalji" v-on:click="registerDermatologist()"/>
        </div>
	
	    </div>		  
    `
    ,
    methods: {
        employDermatologist: function(d) {
            hidden = true;
            selected = true;
            this.aktuelniDermatolog = d;
            alert("Unesite pocetak i kraj radnog vremena ispod tabele.");
        },
        registerDermatologist: function() {
            if (this.aktuelniDermatolog == null) {
                alert("Niste odabrali dermatologa.");
                return;
            }
            this.aktuelniDermatolog.pocetakRadnogVremena = this.pocetakRadnogVremena;
            this.aktuelniDermatolog.krajRadnogVremena = this.krajRadnogVremena;
            axios
            .post("api/admin/employDermatologist/" + this.apoteka.id, this.aktuelniDermatolog)
            .then(response => {
                if (response.data != "OK") {
                    alert("Neuspesno zaposljavanje dermatologa(radno vreme se poklapa sa drugima ili se ne poklapa sa radnim vremenom apoteke).");
                } else {
                    alert("Uspesno zaposljavanje dermatologa.");
                    axios
                    .get("api/admin/dermatologistsOutsidePharmacy/" + this.apoteka.id)
                    .then(response => {
                        this.dermatolozi = response.data;
                    });
                }
                hidden = false;
                selected = false;
                this.aktuelniDermatolog = null;
            });
        },
        searchDermatologists: function(){
            axios
                .get("api/admin/dermatologistsOutsidePharmacy/" + this.apoteka.id)
                .then(response => {
                    this.dermatolozi = response.data;
                    this.dermatolozi = this.dermatolozi.filter(dermatolog => dermatolog.ime.includes(this.searchParams.ime) && 
                    dermatolog.prezime.includes(this.searchParams.prezime) && dermatolog.ocjena >= this.searchParams.startOcena && 
                    dermatolog.ocjena <= this.searchParams.endOcena);

                    if (this.searchParams.kriterijumSortiranja == "IME"){
                        this.dermatolozi.sort((a, b) => (a.ime > b.ime) ? 1 : -1);
                    }
                    else if (this.searchParams.kriterijumSortiranja == "PREZIME"){
                        this.dermatolozi.sort((a, b) => (a.prezime > b.prezime) ? 1 : -1);
                    } 
                    else if (this.searchParams.kriterijumSortiranja == "OCENA"){
                        this.dermatolozi.sort((a, b) => (a.ocjena > b.ocjena) ? 1 : -1);
                    }

                    if (this.searchParams.opadajuce)
                        this.dermatolozi = this.dermatolozi.reverse();
                });
        },
    },
    mounted: function(){
        axios
		.get("api/users/currentUser")
		.then(response => {
			this.korisnik = response.data;
		  	axios
			.get("api/apoteke/admin/" + response.data.id)
			.then(response => {
				this.apoteka = response.data;
                axios
                .get("api/admin/dermatologistsOutsidePharmacy/" + response.data.id)
                .then(response => {
                    this.dermatolozi = response.data;
                });
            });
        });
    }
});