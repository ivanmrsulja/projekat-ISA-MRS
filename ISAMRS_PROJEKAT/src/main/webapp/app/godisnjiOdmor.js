Vue.component("zakazivanje-godisnjegOdmora", {
	data: function () {
		return {
			korisnik:{},
		}
	},
	methods: {	
		
	zavrsiZakazivanje : function(){
		var zahtjev = {tip:"GODISNJI_ODMOR"};
		if($("input[name=odsustvo]").val()) {
			zahtjev = {tip:"ODSUSTVO"};			
		}
				
		axios.post("/api/zahtev/zahtjev", zahtjev).then(data => {
			if(data.data == "OK") {
				alert("Uspesno ste podneli zahtev!");
			}
		});
	},
	
	otkaziZakazivanje : function(){
		this.$router.push({ path: "/pregledi" });
	},
	
	},
	template: ` 
		<div align = center style="width:50%">
				
			<table class="table table-hover">
			 <thead>
				<th>
					 <input name="godisnji" type="radio" name="gender"> Godisnji Odmor<br>
					 <input name="odusustvo" type="radio" name="gender"> Odusustvo<br>
					
				</th>
			</thead>
			</table>
			<input type="button" class="button1" v-on:click="otkaziZakazivanje()" value="Otkazi" />
			<input type="button" class="button1" v-on:click="zavrsiZakazivanje()" value="Zakazi" />
		</div>		  
`
	,
	mounted: function() {
		axios.get("/api/users/currentUser").then(data => {
            if(data.data){
            	this.korisnik=data.data;
            }else{
            	this.$router.push({ path: "/" });
            } 
        });		
	}
});
