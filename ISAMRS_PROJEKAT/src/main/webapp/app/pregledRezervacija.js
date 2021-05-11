Vue.component("lista-rezervacija", {
	data: function () {
		    return {
				rezervacije : [],
				selected: {detaljno: {ocena: 0}},
				ocena: 0,
                ocenjivo: false
		    }
	},
	template: ` 
<div align = center style="width:75%">
		
		<h1>Moje rezervacije</h1>
		<br/>
		
		<h2 v-bind:hidden="rezervacije.length > 0" >Nemate aktuelne rezervacije.</h2>
		
		<table class="table table-hover" v-bind:hidden="rezervacije.length == 0" >
            <thead>
            	<tr>
                <th scope="col">Id</th>
                <th scope="col">Status</th>
                <th scope="col">Datum preuzimanja</th>
               	<th scope="col">Preparat</th>
               	<th scope="col">Apoteka</th>
               	<th scope="col">Cena</th>
				<th scope="col">Otkazivanje</th>
				<th scope="col">Akcija</th>
                </tr>
           	</thead>
            <tbody>
                <tr v-for="r in rezervacije">
                                <th scope="row">{{r.id}}</th>
                                <td>{{r.status}}</td>
                                <td>{{r.datumPreuzimanja}}</td>
                                <td>{{r.preparat}}</td>
                                <td>{{r.apoteka}}</td>
                                <td>{{r.cena}}</td>
								<td><input type="button" class="button1" value="Otkazi" v-on:click="otkazi(r)" v-bind:hidden=" (new Date(r.datumPreuzimanja)).getTime() <= Date.now() + 86400000" /><h3 style="color: lightgray" v-bind:hidden="(new Date(r.datumPreuzimanja)).getTime() >= Date.now() + 86400000">ISTEKLO</h3></td>
            					<td><input type="button" class="button1" value="Detaljnije" v-on:click="detaljnije(r)" data-toggle="modal" data-target="#exampleModalCenter"/></td>
            	</tr>           
            </tbody>
     	</table>
		
		<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLongTitle">Rezervacija broj {{selected.id}}</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        <table>
		        
		        <tr>
		        <td><h6>Apoteka:</h6></td>
		        <td>{{selected.apoteka}}</td>
		        </tr>
		        
		        <tr>
		        <td><h6>Preparat:</h6></td>
		        <td>{{selected.preparat}}</td>
		        </tr>
		        
		        <tr>
		        <td><h6>Kontraindikacije:</h6></td>
		        <td>{{selected.detaljno.kontraindikacije}}</td>
		        </tr>
		        
		        <tr>
		        <td><h6>Sastav:</h6></td>
		        <td>{{selected.detaljno.sastav}}</td>
		        </tr>
		        
		        <tr>
		        <td><h6>Preporuceni unos:</h6></td>
		        <td>{{selected.detaljno.preporuceniUnos}} dnevno</td>
		        </tr>
		        
		        <tr>
		        <td><h6>Oblik:</h6></td>
		        <td>{{selected.detaljno.oblik}}</td>
		        </tr>
		        
		        <tr>
		        <td><h6>Proizvodjac:</h6></td>
		        <td>{{selected.detaljno.proizvodjac}}</td>
		        </tr>
		        
		        <tr>
		        <td><h6>Ocena preparata:</h6></td>
		        <td v-bind:hidden="selected.detaljno.ocena == 0">{{selected.detaljno.ocena.toFixed(2)}}</td>
		        <td v-bind:hidden="selected.detaljno.ocena > 0" style="color: gray">NIJE OCENJENO</td>
		        </tr>
		        
		        <tr>
		        <td><h6 v-bind:hidden="ocenjivo == false">Moja ocena:</h6></td>
		        <td>
		        	<div class="star-rating" v-on:click="clickStar()" v-bind:hidden="ocenjivo == false">
				        <span class="fa fa-star-o" data-rating="1" ></span>
				        <span class="fa fa-star-o" data-rating="2"></span>
				        <span class="fa fa-star-o" data-rating="3"></span>
				        <span class="fa fa-star-o" data-rating="4"></span>
				        <span class="fa fa-star-o" data-rating="5"></span>
				        <input type="hidden" name="whatever1" class="rating-value" v-model="ocena">
				    </div>
		        </td>
		        </tr>
		        
		        </table>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Nazad</button>
		      </div>
		    </div>
		  </div>
		</div>
		
</div>		  
`
	,
	methods:{
		SetRatingStar: function(){
    		let self = this;
    		var $star_rating = $('.star-rating .fa');
    		return $star_rating.each(function() {
    			$star_rating.siblings('input.rating-value').val(self.ocena);
			    if (parseInt($star_rating.siblings('input.rating-value').val()) >= parseInt($(this).data('rating'))) {
			      return $(this).removeClass('fa-star-o').addClass('fa-star');
			    } else {
			      return $(this).removeClass('fa-star').addClass('fa-star-o');
			    }
			});
    	},
    	clickStar: function() {
    		axios
	        .put("/api/ocene/oceniPreparat/" + this.selected.detaljno.id + "/" + this.ocena)
	        .then(response => {
	        	axios
		        .get("/api/preparat/specifikacija/" + this.selected.detaljno.id)
		        .then(response => {
		            this.selected.detaljno = response.data;
		        });
		        axios
		        .get("/api/users/currentUser").then(data =>{
		            axios
					.get("api/users/rezervacije/" + data.data.id)
					.then(response => {
						this.rezervacije = response.data;
					});
	        	});
	        });
    	},
		otkazi: function(r){
			axios
	        .patch("/api/preparat/otkazi/" + r.id)
	        .then(response => {
	            if(response.data != "OK"){
	            	alert(response.data);
	            }
	            axios.get("/api/users/currentUser").then(data =>{
		            axios
					.get("api/users/rezervacije/" + data.data.id)
					.then(response => {
						this.rezervacije = response.data;
					});
		        });
	        });
		},
		detaljnije: function(r){
			this.selected = r;
			axios
		        .get("/api/ocene/preparat/" + r.detaljno.id)
		        .then(response => {
		        	if(response.data != -1){
		        		this.ocena = response.data;
		        		this.ocenjivo = true;
		        	}else{
		        		this.ocena = 0;
		        		this.ocenjivo = false;
		        	}
		        	this.SetRatingStar();
		        });
		}
	},
	mounted: function() {
		let self = this;
    	
    	var $star_rating = $('.star-rating .fa');
    	$star_rating.on('click', function() {
    	  self.ocena = $(this).data('rating');
		  $star_rating.siblings('input.rating-value').val($(this).data('rating'));
		  return self.SetRatingStar();
		});
		
		axios.get("/api/users/currentUser").then(data =>{
            if(data.data){
            	axios
				.get("api/users/rezervacije/" + data.data.id)
				.then(response => {
					this.rezervacije = response.data;
				});
            }else{
            	this.$router.push({ path: "/" });
            }
        });
    }
});