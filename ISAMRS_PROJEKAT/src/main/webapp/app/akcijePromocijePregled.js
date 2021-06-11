Vue.component("akcije-promocije", {
	data: function () {
		    return {
				akcijePromocije : [],
				user: {}
		    }
	},
	template: ` 
<div align = center style="width:60%">
		
		<h1>Moje pretplate</h1>
		<br/>
		<table class="table table-hover">
            <thead>
            	<tr>
                <th scope="col">Naziv apoteke</th>
                <th scope="col">Adresa</th>
                <th scope="col">Ocena</th>
				<th scope="col">Akcija</th>
                </tr>
           	</thead>
            <tbody>
                <tr v-for="a in akcijePromocije" v-bind:key="a.id">
                    <td>{{a.naziv}}</td>
                    <td>{{a.lokacija.ulica}}</td>
                    <td v-if="a.ocena != 0">{{a.ocena.toFixed(2)}}</td>
                    <td v-else ><h3 style="color: lightgray">Nije ocenjeno</h3></td>
                    <td><input type="button" class="button1" value="Otkazi pretplatu" @click="otkazivanje(a.id)"></td>
            	</tr>           
            </tbody>
     	</table>

	
	
</div>		  
`
	,
	methods : {
        otkazivanje : function(i) {
        	var self = this;
        	axios
				.put("api/users/unsub/" + i)
				.then(response => {
					axios
				.get("api/users/akcije/" + self.user.id)
				.then(resp => {
					toast("Uspesno ste otkazali pretplatu");
					self.akcijePromocije = resp.data
				});
				});
        },
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
		axios.get("/api/users/currentUser").then( response => {
            if(response.data){
            	this.user = response.data;
            	console.log(this.user);
                axios
				.get("api/users/akcije/" + response.data.id)
				.then(response => {
					this.akcijePromocije = response.data
				});
            }else{
            	this.$router.push({ path: "/" });
            }
        });
    }
    
});