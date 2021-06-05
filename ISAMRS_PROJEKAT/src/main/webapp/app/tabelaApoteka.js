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
		<select name="g1" id="select_g1" v-on:change="sorting()">
		    <option value="standard"></option>
	        <option value="cena">Sortiraj po ceni</option>
	        <option value="naziv">Sortiraj po nazivu</option>
	        <option value="ocena">Sortiraj po oceni</option>
	        <option value="mesto">Sortiraj po mestu</option>
    	</select>
		<table class="table table-hover">
            <thead>
            	<tr>
                <th scope="col">Naziv apoteke</th>
                <th scope="col">Ulica apoteke</th>
                <th scope="col">Ocena apoteke</th>
                <th scope="col">Cena preparata</th>

                </tr>
           	</thead>
            <tbody>
                <tr v-for="l in lekovi" v-bind:key="l.id">
                                <td>{{l.apoteka.naziv}}</td>
                                <td>{{l.apoteka.lokacija.ulica}}</td>
                                <td>{{l.apoteka.ocena.toFixed(2)}}</td>
                                <td>{{l.cena}}</td>
                          
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
    		console.log(user);
    		var self = this;
    		axios
			.get("/api/apoteke/sorting/"+self.$route.params.id+"/"+user)
			.then(function(resp){
				self.lekovi = resp.data;
				console.log(resp.data);
				
			});
    	}
    },
	mounted: function() {
		var self = this;
        axios
		.get("/api/apoteke/test/"+self.$route.params.id)
		.then(function(resp){
			self.lekovi = resp.data;
			console.log(resp.data);
			
		});
		console.log(this.lekovi);
	  	
    }
});