Vue.component("lista-narudzbenica", {
	data: function () {
		    return {
				zalbe : [],
				user: {},
				indZalba: {},
				modalShow: false,
				listNarudzbenice: []
		    }
	},
	template: ` 
<div align = center style="width:75%">
		
		<h1>Slobodne narudzbenice</h1>
		<br/>
	</br>
		<table class="table table-hover">
            <thead>
            	<tr>
                <th scope="col">Rok narudzbenice</th>
                <th scope="col">Status</th>
                <th scope="col">Naruceni proizvodi</th>
                <th scope="col">Detaljnije</th>

                </tr>
           	</thead>
            <tbody>
                <tr v-for="z in zalbe" v-bind:key="z.id">
                                <td>{{z.rok}}</td>
                                <td>{{z.status}}...</td>
                               <td>{{z.adminApoteka}}</td>
                                <td><a :href="'#/jednaNarudzbenica/'+z.id" class="button1">Detaljnije</a></td>
            	</tr>           
            </tbody>
     	</table>
     	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        <p>{{indZalba.tekst}}</p>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		        <button type="button" class="btn btn-primary">Save changes</button>
		      </div>
		    </div>
		  </div>
		</div>

	
	
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
    	}
    },
	mounted: function() {
		var self = this;
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
		.get("/api/users/currentUser")
		.then(function(resp){
			self.user = resp.data;
			axios
			.get("/api/admin/availableNar/"+self.user.id)
			.then(function(re){
				self.zalbe = re.data;
				console.log(re.data[0]);
				//self.zalbe.forEach(element => element.naruceniProizvodi.forEach(e => element.listProizvod.push(e.preparat+ ": "+ e.kolicina)));
				var i;
				narudzbenice = [];
				for (i = 0; i < self.zalbe.length; i++) {
				  proizvodi = [];
				  narudz = {};
				  var j;
				  for (j = 0; j<i.naruceniProizvodi; j++) {
				  	proizvodi.push(i.naruceniProizvodi[j].kolicina+ " "+i.naruceniProizvodi[j].preparat);
				  }
				  narudz["id"] = i.id;
				  narudz["status"] = i.status;
				  narudz["rok"] = i.rok;
				  narudz["naruceniProizvodi"] = proizvodi.join();
				  narudzbenice.push(narudz);
				}
				self.listNarudzbenice = narudzbenice;
			});
		});
		console.log(this.user);
		console.log(this.zalbe);
	  	
    }
});