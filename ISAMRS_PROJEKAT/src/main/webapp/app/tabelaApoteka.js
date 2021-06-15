Vue.component("tabela-apoteka", {
	data: function () {
		    return {
				lekovi : [],
				user: {},
				indZalba: {},
				modalShow: false
		    }
	},
	template: ` 
<div align = center style="width:75%">
		
		<h1>Lekovi</h1>
		<br/>
	</br>
		<table>
			<td><h2>Sortiraj po:</h2></td>
			<td><select name="g1" id="select_g1" v-on:change="sorting()">
		    <option value="standard"></option>
	        <option value="cena">Sortiraj po ceni</option>
	        <option value="naziv">Sortiraj po nazivu</option>
	        <option value="ocena">Sortiraj po oceni</option>
	        <option value="mesto">Sortiraj po mestu</option>
    	</select></td>
    	<td> <input type="radio" value="value1" name="group1" @change="sorting()">Opadajuce
    		  <input type="radio" value="value2" name="group1" @change="sorting()" checked>Rastuce</td>
		</table>
		<table class="table table-hover">
            <thead>
            	<tr>
                <th scope="col">Naziv apoteke</th>
                <th scope="col">Ulica apoteke</th>
                <th scope="col">Ocena apoteke</th>
                <th scope="col">Naziv preparata</th>
                <th scope="col">Cena preparata</th>

                </tr>
           	</thead>
            <tbody>
                <tr v-for="l in lekovi" v-bind:key="l.id" v-bind:id="l.id">
                                <td class="naziv">{{l.apoteka.naziv}}</td>
                                <td class="ulica">{{l.apoteka.lokacija.ulica}}</td>
                                <td class="ocena">{{l.apoteka.ocena.toFixed(2)}}</td>
                                <td>{{l.nazivLekova}}</td>
                                <td class="cena">{{l.cena}}</td>
                                <td><input type="button" class="button1" value="Posalji" v-on:click="kupi(l.apoteka.id)"/></td>
                          
            	</tr>           
            </tbody>
     	</table>

	
	
</div>		  
`
	,
	methods: {	
    	pregledajBtn : function(event) {
    		let sifra = event.target.id;
    		var i;
			for (i = 0; i < this.zalbe.length; i++) {
			  if(this.zalbe[i].id == sifra) {
			  	this.indZalba.id = this.zalbe[i].id;
			  	this.indZalba.tekst = this.zalbe[i].tekst;
			  	console.log("BOOYA");
			  }
			}
			console.log(this.indZalba);
    	},
    	sorting : function() {
    		let user = $("#select_g1").val();
    		let v = $("input[name='group1']:checked").val();
    		console.log(v);
    		console.log(user);
    		var self = this;
    		axios
			.get("/api/apoteke/sorting/"+self.$route.params.id+"/"+user+"/"+v)
			.then(function(resp){
				self.lekovi = resp.data;
				console.log(resp.data);
				
			});
    	},
    	prik : function() {
    		let v = $("input[name='group1']:checked").val();
    		console.log(v);
    	},
    	kupi : function(sifra) {
    		var self = this;
        	var newRacun = {cenaId: parseInt(sifra), lekoviId: self.$route.params.id};
        	axios
			.put("/api/apoteke/buy", newRacun )
			.then(function(resp){
				console.log(resp.data);
				toast("Uspesno ste kupili lekove!");
				self.$router.push({ path: "/apoteke/0" });
				
				
			});
    	}
    },
	mounted: function() {
		let temp = this;
		axios
			.get("/api/users/currentUser")
			.then(function(resp){
				if(resp.data.zaposlenjeKorisnika == "ADMIN_APOTEKE"){
							if (resp.data.loggedBefore) {
								temp.$router.push({ path: "/profileApoteke" });
							} else {
								temp.$router.push({ path: "/promeniSifru" });
							}
						}else if(resp.data.zaposlenjeKorisnika == "FARMACEUT"){
							temp.$router.push({ path: "/farmaceuti" });
						}else if(resp.data.zaposlenjeKorisnika == "DOBAVLJAC"){
							if(resp.data.loggedBefore) {
								temp.$router.push({ path: "/tab" });
							} else {
								temp.$router.push({ path: "/promeniSifru" });
							}
						}else if(resp.data.zaposlenjeKorisnika == "DERMATOLOG"){
							temp.$router.push({ path: "/dermatolozi" });
						}else if(resp.data.zaposlenjeKorisnika == "PACIJENT"){
							
						}else if(resp.data.zaposlenjeKorisnika == "ADMIN_SISTEMA") {
							if(resp.data.loggedBefore) {
								temp.$router.push({ path: "/regDerm" });
							} else {
								temp.$router.push({ path: "/promeniSifru" });
							}
						}else {
							temp.$router.push({ path: "/" });
						}
						
					});
		var self = this;
		axios
		.get("/api/users/currentUser")
		.then(function(resp){
			self.user = resp.data;
			console.log(self.user);
		});
        axios
		.get("/api/apoteke/test/"+self.$route.params.id)
		.then(function(resp){
			self.lekovi = resp.data;
			console.log(resp.data);
			
		});
		console.log(this.lekovi);
	  	
    }
});