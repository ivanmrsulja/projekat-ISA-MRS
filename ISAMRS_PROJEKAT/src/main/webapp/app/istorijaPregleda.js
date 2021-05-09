Vue.component("istorija-pregleda", {
	data: function () {
		    return {
				pregledi : [],
				numPages: 1,
				korisnik: {},
				selected: {vrijeme: {}, apoteka:{lokacija:{}}, zaposleni:{ocena:0.0}},
				ocena: 0,
				ocenjivo: false
		    }
	},
	template: ` 
<div align = center style="width:75%">
		
		<h1>Istorija pregleda</h1>
		<br/>
		
		<h2 v-bind:hidden="pregledi.length > 0" >Nemate ni jedan obavljen pregled/savetovanje.</h2>
		
		<div class="dropdown" v-bind:hidden="pregledi.length == 0" >
		  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    Sortiraj po:
		  </button>
		  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		    <a class="dropdown-item" :href="'#/istorijaPregleda/'+$route.params.page+'/CENA'" v-on:click="loadNext($route.params.page, 1)">CENA</a>
		    <a class="dropdown-item" :href="'#/istorijaPregleda/'+$route.params.page+'/DATUM'" v-on:click="loadNext($route.params.page, 2)">DATUM</a>
		    <a class="dropdown-item" :href="'#/istorijaPregleda/'+$route.params.page+'/TRAJANJE'" v-on:click="loadNext($route.params.page, 3)">TRAJANJE</a>
			<a class="dropdown-item" :href="'#/istorijaPregleda/'+$route.params.page+'/PLAIN'" v-on:click="loadNext($route.params.page, 4)">BEZ_SORTIRANJA</a>
		  </div>
		</div>
		
		<br/>
		<table class="table table-hover" v-bind:hidden="pregledi.length == 0" >
            <thead>
            	<tr>
                <th scope="col">Status</th>
                <th scope="col">Tip</th>
                <th scope="col">Datum</th>
               	<th scope="col">Vrijeme</th>
             	<th scope="col">Trajanje</th>
           		<th scope="col">Cijena</th>
				<th scope="col">Akcija</th>
                </tr>
           	</thead>
            <tbody>
                <tr v-for="p in pregledi">
                    <td>{{p.status}}</td>
                    <td>{{p.tip}}</td>
                    <td>{{p.datum}}</td>
                    <td>{{p.vrijeme}}</td>
                    <td>{{p.trajanje}}</td>
					<td>{{p.cijena}}</td>
					<td><input type="button" class="button1" value="Detaljnije" v-on:click="detaljnije(p)" data-toggle="modal" data-target="#modalDetaljnije"/></td>
            	</tr>           
            </tbody>
     	</table>

		<div class="pagination" v-for="i in numPages+1" :key="i" >
		  <a :href="'#/istorijaPregleda/'+(i-1)+'/'+$route.params.criteria" v-on:click="loadNext(i-1, 0)">{{i}}</a>
		</div>
		
		
		<div class="modal fade" id="modalDetaljnije" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLongTitle">{{selected.tip}} - ID: {{selected.id}}</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        <table style="border-spacing: 0px, 2em">
		        
		        <tr>
		        <td><h6>Apoteka:</h6></td>
		        <td>{{selected.apoteka.naziv}}</td>
		        </tr>
		        
		        <tr>
		        <td><h6>Adresa apoteke:</h6></td>
		        <td>{{selected.apoteka.lokacija.ulica}}</td>
		        </tr>
		        
		        <tr>
		        <td v-bind:hidden="selected.tip == 'PREGLED'"><h6>Farmaceut:</h6></td>
		        <td v-bind:hidden="selected.tip != 'PREGLED'"><h6>Dermatolog:</h6></td>
		        <td>{{selected.zaposleni.ime}} {{selected.zaposleni.prezime}}</td>
		        </tr>
		        
		        <tr>
		        <td><h6>Datum odrzavanja:</h6></td>
		        <td>{{selected.datum}}</td>
		        </tr>
		        
		        <tr>
		        <td><h6>Vreme odrzavanja:</h6></td>
		        <td>{{selected.vrijeme.toString().split(":")[0]}}:{{selected.vrijeme.toString().split(":")[1]}}</td>
		        </tr>
		        
		        <tr>
		        <td><h6>Trajanje pregleda:</h6></td>
		        <td>{{selected.trajanje}} min</td>
		        </tr>
		        
		        <tr>
		        <td><h6>Cena pregleda:</h6></td>
		        <td>{{selected.cijena}} rsd</td>
		        </tr>
		        
		        <tr>
		        <td><h6>Izvestaj:</h6></td>
		        <td>{{selected.izvjestaj}}</td>
		        </tr>
		        
		        <tr>
		        <td><h6>Ocena zaposlenog:</h6></td>
		        <td>{{selected.zaposleni.ocena.toFixed(2)}}</td>
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
	methods: {
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
    		if(this.selected.tip == "PREGLED"){
    			axios
		        .put("/api/ocene/oceniDermatologa/" + this.selected.zaposleni.id + "/" + this.ocena)
		        .then(response => {
		        	axios
			        .get("/api/dermatolog/ocenjivanje/" + this.selected.zaposleni.id)
			        .then(response => {
			        	this.selected.zaposleni.ocena = response.data.ocjena;
			        	this.initData();
			        });
		        });
    		} else {
    			axios
		        .put("/api/ocene/oceniFarmaceuta/" + this.selected.zaposleni.id + "/" + this.ocena)
		        .then(response => {
		        	axios
			        .get("/api/farmaceut/ocenjivanje/" + this.selected.zaposleni.id)
			        .then(response => {
			        	this.selected.zaposleni.ocena = response.data.ocena;
			        	this.initData();
			        });
		        });
    		}
    	},
		detaljnije: function(r){
			if(r.tip == "PREGLED"){
				axios
		        .get("/api/dermatolog/ocenjivanje/" + r.zaposleni.id)
		        .then(response => {
		        	this.selected = r;
		        	this.selected.zaposleni.ocena = response.data.ocjena;
		        });
				axios
		        .get("/api/ocene/dermatolog/" + r.zaposleni.id)
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
			}else{
				axios
		        .get("/api/farmaceut/ocenjivanje/" + r.zaposleni.id)
		        .then(response => {
		        	this.selected = r;
		        	this.selected.zaposleni.ocena = response.data.ocena;
		        });
				axios
		        .get("/api/ocene/farmaceut/" + r.zaposleni.id)
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
		loadNext: function(p, crit){
			let criteria = this.$route.params.criteria;	
			switch(crit) {
			  case 1:
			    criteria = "CENA";
			    break;
			  case 3:
			    criteria = "TRAJANJE";
			    break;
			  case 2:
			    criteria = "DATUM";
			  	break;
			  case 4:
				criteria = "PLAIN";
				break;
			}
			
			axios
			.get("api/users/istorijaPregleda/" + this.korisnik.id + "/" + p + "/" + criteria)
			.then(response => {
				this.pregledi = response.data.content;
			});
		},
		initData: function(){
			axios
			.get("/api/users/currentUser").then(response => {
	            if(response.data){
	            	this.korisnik = response.data;
	                axios
						.get("api/users/istorijaPregleda/" + response.data.id + "/" + this.$route.params.page + "/" + this.$route.params.criteria)
						.then(response => {
							this.pregledi = response.data.content;
							this.numPages = response.data.totalPages - 1;
						});
	            }else{
            	this.$router.push({ path: "/" });
            }
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
		
		this.initData();
    }
});