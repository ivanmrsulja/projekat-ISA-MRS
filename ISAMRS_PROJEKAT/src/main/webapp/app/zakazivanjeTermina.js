Vue.component("zakazivanje-termina", {
	data: function () {
		return {
			
		}
	},
	methods: {	
	zavrsiZakazivanje : function(){
		let datum = $("input[name=datum]").val();
		let vrijeme = $("input[name=vrijeme]").val();
		let noviPregled = {izvjestaj: "", datum : datum, vrijeme: vrijeme, trajanje: 45, cijena: 5000 };
		
		axios.post("/api/", noviPregled).then(data => {
			if(data.data == "OK") {
				alert("Uspesno ste se zakazali pregled!");
			}
		});	this.$router.push({ path: "/pregledi" });
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
			
				<br>
					<tr>Datum: <input type="date" name="datum"/></tr>
				<br>
					<tr>Vrijeme: <input type="time" name="vrijeme"/></tr>				
				</th>
			</thead>
			</table>
			<input type="button" class="button1" v-on:click="otkaziZakazivanje()" value="Otkazi" />
			<input type="button" class="button1" v-on:click="zavrsiZakazivanje()" value="Zakazi" />
		</div>		  
`
});
