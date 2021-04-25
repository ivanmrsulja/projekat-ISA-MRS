Vue.component("pojedinacni-preparat", {
	data: function () {
		    return {
				spec : {ocena: 0},
				apoteke: [],
				mode: "BROWSE",
				selected: {},
				datum: {},
				ocena: 0,
                ocenjivo: false
		    }
	},
	template: ` 
<div align = center style="width:50%">
		
		<h1>Specifikacija preparata id: {{$route.params.spec}}</h1>
		<br/>
		<table class="table table-hover">
            <thead>
            	<th>
                <tr scope="col">Id:\t{{spec.id}}</tr>
                <tr scope="col">Naziv:\t{{spec.naziv}}</tr>
                <tr scope="col">Kontraindikacije:\t{{spec.kontraindikacije}}</tr>
                <tr scope="col">Sastav:\t{{spec.sastav}}</tr>
                <tr scope="col">Preporuceni unos:\t{{spec.preporuceniUnos}}</tr>
                <tr scope="col">Oblik:\t{{spec.oblik}}</tr>
                <tr scope="col">Proizvodjac:\t{{spec.proizvodjac}}</tr>
                <tr scope="col">Rezim:\t{{spec.rezim}}</tr>
                <tr scope="col">Ocena:\t{{spec.ocena.toFixed(2)}}</tr>
                <tr scope="col">
                <td v-bind:hidden="ocenjivo == false" >Moja ocena:</td>
                <td>
                	<td style="padding: 20px">
	            	<div class="star-rating" v-on:click="clickStar()" v-bind:hidden="ocenjivo == false">
				        <span class="fa fa-star-o" data-rating="1" ></span>
				        <span class="fa fa-star-o" data-rating="2"></span>
				        <span class="fa fa-star-o" data-rating="3"></span>
				        <span class="fa fa-star-o" data-rating="4"></span>
				        <span class="fa fa-star-o" data-rating="5"></span>
				        <input type="hidden" name="whatever1" class="rating-value" v-model="ocena">
				    </div>
	            </td>
                </td>
                </tr>
                </th>
           	</thead>
     	</table>
		
		<h2 v-bind:hidden="apoteke.length == 0" >Dostupno u:</h2>
		
		<table class="table table-hover" v-bind:hidden="apoteke.length == 0">
			<thead>
				<tr bgcolor="#90a4ae">
					<th>Naziv</th>
					<th>Adresa</th>
					<th>Cena</th>
					<th>Ocena apoteke</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="a in apoteke"" v-on:click="select(a)">
		                <td>{{a.apoteka.naziv}}</td>
		                <td>{{a.apoteka.lokacija.ulica}}</td> 
		                <td>{{a.cena}}</td>
		                <td>{{a.apoteka.ocena.toFixed(2)}}</td>                               
				</tr>
			</tbody>
		</table>
		
		</br>
		
		<table v-bind:hidden="this.mode == 'BROWSE'">
			<tr><td><h4>Rezervisi u:</h4></td><td>{{selected.naziv}}</td></tr>
			<tr><td><h4>Preuzmi do:</h4></td><td><input type="date" v-model="datum" /></td></tr>
			<tr><td align=center ><input type="button" class="button1" v-on:click="rezervisi()" value="Rezervisi" /></td><td align=center ><input class="button1" type="button" value="Odustani" v-on:click="cancel()" /></td></tr>
		</table>
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
    	  	axios
		        .get("/api/ocene/oceniPreparat/" + this.$route.params.spec + "/" + this.ocena)
		        .then(response => {
		        	axios
			        .get("api/preparat/spec/" + this.$route.params.spec)
			        .then(response => {
			            this.spec = response.data;
			        });
		        });
		},
		select: function(apoteka){
			this.mode = "ORDER";
			this.selected = apoteka.apoteka;
		},
		rezervisi: function(){
			axios
				.get("api/preparat/rezervisi/" + this.$route.params.spec + "/" + this.selected.id + "?datum=" + this.datum)
				.then(response => {
					alert(response.data);
				}).catch(response => {
					alert("Morate uneti datum preuzimanja.");
				});
		},
		cancel: function(){
			this.mode = "BROWSE";
			this.selected = {};
			this.datum = {}
		}
	}
	,
	mounted: function() {
		let self = this;
		
    	var $star_rating = $('.star-rating .fa');
    	$star_rating.on('click', function() {
    	  self.ocena = $(this).data('rating');
		  $star_rating.siblings('input.rating-value').val($(this).data('rating'));
		  return self.SetRatingStar();
		});
		
		axios
        .get("/api/users/currentUser")
        .then(response => {
            this.korisnik = response.data;
            if(this.korisnik.zaposlenjeKorisnika == "PACIJENT"){
            	axios
		        .get("/api/ocene/preparat/" + this.$route.params.spec)
		        .then(response => {
		        	if(response.data != -1){
		        		this.ocena = response.data;
		        		this.ocenjivo = true;
		        		this.SetRatingStar();
		        	}else{
		        		this.ocena = 0;
		        		this.ocenjivo = false;
		        		this.SetRatingStar();
		        	}
		        });
            }
        });
        
		axios
			.get("api/preparat/spec/" + this.$route.params.spec)
			.then(response => {
				this.spec = response.data;
				axios
				.get("api/users/currentUser")
				.then(response => {
					if(response.data.zaposlenjeKorisnika == "PACIJENT"){
						axios
						.get("api/preparat/dostupnost/" + this.$route.params.spec)
						.then(response => {
							this.apoteke = response.data;
						});
					}
				});
			});
    }
});