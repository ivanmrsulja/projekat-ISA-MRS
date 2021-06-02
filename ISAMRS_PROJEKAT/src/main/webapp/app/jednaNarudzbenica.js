Vue.component("jedna-narudzbenica", {
	data: function () {
		    return {
				zalbe : {},
				user: {},
				indZalba: {},
				modalShow: false
		    }
	},
	template: ` 
<div align = center style="width:75%">

		<h1> Id: {{zalbe.id}}</h1>
		<h1> Rok: {{zalbe.rok}}</h1>
		<br>
		<br>
		<h1>Naruceni proizvodi</h1>
		<table class="table table-hover">
            <thead>
            	<tr>
                <th scope="col">Id</th>
                <th scope="col">Preparat</th>
                <th scope="col">Kolicina</th>
                </tr>
           	</thead>
            <tbody>
                <tr v-for="z in zalbe.naruceniProizvodi" v-bind:key="z.id">
                                <td>{{z.id}}</td>
                                <td>{{z.preparat}}</td>
                               <td>{{z.kolicina}}</td>
            	</tr>           
            </tbody>
     	</table>
     	<br>
     	<br>
     	<table>
			<tr>
				<td> <h2>Datum:</h2> </td> <td> <input type="date" id="datum"/> </td>
			</tr>
			<tr>
				<td> <h2>Cena:</h2> </td> <td> <input type="number" id="cena"/> </td>
			</tr>
		</table>
		<input value="Posalji" class="btn btn-primary" type="button" name="zalBtn" v-on:click="sendZalba()"/> 

	
	
</div>		  
`
	,
	methods: {	
    	sendZalba : function() {
    		let self = this;
    		let datum = $("#datum").val();
    		let cena = $("#cena").val();
    		if(datum.trim() == "" || cena.trim() == "") {
    			alert("Ne ostavljajte prazna polja");
    			return;
    		}
    		newPonuda = {ukupnaCena:cena, rokIsporuke:datum, idNarudzbenice: this.zalbe.id, dobavljac: this.user.username};
    		axios.post("/api/admin//sendOffer", newPonuda).then(data => {
                if (data.data == "OK") {
                    alert("Uspesno ste poslali ponudu");
                    self.$router.push({ path: "/listaNarudzbenica" });
                }
            });
    		console.log(newPonuda);
    	}
    },
	mounted: function() {
		console.log(this.$route.params.zalId);
		var self = this;
        axios
		.get("/api/users/currentUser")
		.then(function(resp){
			self.user = resp.data;
			axios
			.get("/api/admin/oneNar/"+self.$route.params.zalId)
			.then(function(re){
				self.zalbe = re.data;
				
			});
		});
		console.log(this.user);
	  	
    }
});