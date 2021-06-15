Vue.component("pocetna-stranas", {
	data: function () {
		    return {
				offers : []
				//let tip = $("#kriterijum").children("option:selected").val();
		    }
	},
	template: ` 
<div align = center style="width:75%">
		
		<h1>Moje ponude</h1>
		<br/>
		<div id="mySidebar" class="sidebar">
		  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
		  <table>
			  <tr><td style="color:white">Filtriraj po statusu:</td> 
			  		<td>
				  		<select name="tip" id="kriterijum">
				  		  <option value="SVI">SVE PONUDE</option>
						  <option value="PRIHVACENA">PRIHVACENA</option>
						  <option value="ODBIJENA">ODBIJENA</option>
						  <option value="CEKA_NA_ODGOVOR">CEKA NA ODGOVOR</option>
						</select>
					</td></tr>
			  <tr><td colspan=2 align=center ><input type="button" name="search" value="Filter" v-on:click="pregledajBtn" /></td></tr>
		  </table>
	</div>
		
	<div id="main">
	  <button class="openbtn" onclick="openNav()">&#9776; Pretraga</button>
	</div>
	</br>
		<table class="table table-hover">
            <thead>
            	<tr>
                <th scope="col">Id</th>
                <th scope="col">Status</th>
                <th scope="col">Ukupna cena</th>
               	<th scope="col">Rok isporuke ponude</th>
             	<th scope="col">Narudzbenica</th>
           		<th scope="col">Dobavljac</th>
           		<th scope="col">Rok isporuke narudzbenice</th>
           		<th scope="col">Azuriraj ponudu</th>

                </tr>
           	</thead>
            <tbody>
                <tr v-for="offer in offers" v-bind:key="offer.id">
                                <th scope="row">{{offer.id}}</th>
                                <td>{{offer.status}}</td>
                                <td>{{offer.ukupnaCena}}</td>
                                <td>{{offer.rokIsporuke}}</td>
                                <td>{{offer.idNarudzbenice}}</td>
                                <td>{{offer.dobavljac}}</td>
                                <td>{{offer.rokIsporukeNarudzbenice}}</td>
                                <div v-if="new Date() < new Date(offer.rokIsporukeNarudzbenice) && offer.status != 'PRIHVACENA'">
                                <td ><input type="date" :id="'datumAzur'+offer.id"/></td>
                                <td ><input type="number" :id="'cenaAzur'+offer.id"/></td>
                                <td ><input @click="proveri(offer.id)" type="button" class="button1" value="Azuriraj ponudu"/></td>
                                </div>
            	</tr>           
            </tbody>
     	</table>

	
	
</div>		  
`
	,
	methods: {	
    	pregledajBtn : function() {
    		let tip = $("#kriterijum").children("option:selected").val();
    		toast(tip);
    		axios
			.get("api/admin/cures/"+tip)
			.then(response => {
				this.offers = response.data;
			});
    	},
    	proveri : function(i) {
    		let d = "#datumAzur" + i;
    		let n = "#cenaAzur" + i;
    		let datumZaAzur = $(d).val();
    		let novacZaAzur = parseFloat($(n).val());
    		if(datumZaAzur.trim() == "" || Number.isNaN(novacZaAzur)) {
    			toast("Ne ostavljajte prazna polja");
    			return;
    		}
    		if(new Date(datumZaAzur) < new Date()) {
    			toast("Morate uneti datum posle danasnjeg");
    			return;
    		}
    		
    		axios
			.put("api/admin/updateOffer/"+i+"/"+datumZaAzur+"/"+novacZaAzur)
			.then(response => {
				if(response.data == "OK") {
					axios
					.get("api/admin")
					.then(respo => {
						this.offers = respo.data;
						
					});
				}
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
							if(!resp.data.loggedBefore) {
								temp.$router.push({ path: "/promeniSifru" });
							}
						}else if(resp.data.zaposlenjeKorisnika == "DERMATOLOG"){
							temp.$router.push({ path: "/dermatolozi" });
						}else if(resp.data.zaposlenjeKorisnika == "PACIJENT"){
							temp.$router.push({ path: "/apoteke/0" });
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
		axios
			.get("api/admin")
			.then(response => {
				this.offers = response.data;
				
			});
    }
});