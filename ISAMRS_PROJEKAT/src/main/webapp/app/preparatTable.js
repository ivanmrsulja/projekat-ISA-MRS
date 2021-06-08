Vue.component("profil-preparati", {
	data: function () {
		    return {
				preparati : [],
				selectedPreparat:{},
				searchParams: {naziv: "", filter: "SVI"}
		    }
	},
	methods: {	
    	pregledajBtn : function(el) {
    		this.selectedPreparat = el;
    	},   
    	pregledajPreparat : function(r){
			window.location.href = "#/preparati/" + r.id;
		},
		pretragaPreparata : function() {
			
			let usr = $("input[name=naziv]").val();
			if(usr.trim() == "") {
				usr = "SVI";
			}
			axios
			.get("api/preparat/search/"+usr)
			.then(response => {
				this.preparati = response.data
			});
		},
		filterPreparata : function() {
			let name = $("input[name=naziv]").val();
			let tip = $("#tipleka").children("option:selected").val();
			let lowerBound = parseInt($("#donjaoc").children("option:selected").val());
			let higherBound = parseInt($("#gornjaoc").children("option:selected").val());
			if(lowerBound > higherBound) {
				toast("Donja granica ne sme biti veca od gornje");
				return
			}
			if(name.trim() == "") {
				name = "SVI";
			}
			axios
			.get("api/preparat/search/"+name+"/"+tip+"/"+"/"+lowerBound+"/"+higherBound)
			.then(response => {
				this.preparati = response.data
			});
		
		}
    },
    filters: {
    	dateFormat: function (value, format) {
    		var parsed = moment(value);
    		return parsed.format(format);
    	}
   	},
	
	template: ` 
<div align = center style="width:50%">
	
	<h1>Pregled preparata</h1>
	
	<div id="mySidebar" class="sidebar">
		  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
		  <table>
			  <tr><td colspan=2 ><input type="text" name="naziv" placeholder="Unesite naziv leka" v-model="searchParams.naziv" /></td></tr>
			  <tr><td style="color:white">Filtriraj po tipu:</td> 
			  		<td>
				  		<select name="tip" id="tipleka">
						  <option value="SVI">SVI</option>
						  <option value="ANTIBIOTIK">Antibiotik</option>
						  <option value="ANESTETIK">Anestetik</option>
						  <option value="ANTIHISTAMINIK">Antihistaminik</option>
						</select>
					</td></tr>
			  <tr><td style="color:white">Filtriraj po Oceni:</td> 
			  		<td>
				  		<select name="donjaocena" id="donjaoc">
						  <option value="0">0</option>
						  <option value="1">1</option>
						  <option value="2">2</option>
						  <option value="3">3</option>
						  <option value="4">4</option>
						  <option value="5">5</option>
						</select>
					</td>
					<td>
				  		<select name="gornjaocena" id="gornjaoc">
						  <option value="0">0</option>
						  <option value="1">1</option>
						  <option value="2">2</option>
						  <option value="3">3</option>
						  <option value="4">4</option>
						  <option value="5">5</option>
						</select>
					</td></tr>
			  <tr><td colspan=2 align=center ><input type="button" v-on:click="pretragaPreparata" name="search" value="Pretrazi" /></td></tr>
			  <tr><td colspan=2 align=center ><input type="button" v-on:click="filterPreparata" name="search" value="Filtriraj" /></td></tr>
		  </table>
	</div>
		
	<div id="main">
	  <button class="openbtn" onclick="openNav()">&#9776; Pretraga</button>
	</div>
	
	<br/>
	
	
	
	<div class="card" style="width: 18rem; display: inline-block; margin-right: 10px;" v-for="preparat in preparati" >
	  <img class="card-img-top" src="../css/drug.png" alt="Card image cap">
	  <div class="card-body">
	    <h5 class="card-title">{{preparat.naziv}}</h5>
	    <p class="card-text">{{preparat.sastav}}</p>
	    <input type="button" class="button1" value="Pregledaj" v-on:click="pregledajPreparat(preparat)"/>
	  </div>
	</div>
	
</div>		  
`
	,
	mounted: function() {
		axios
			.get("api/preparat")
			.then(response => {
				this.preparati = response.data
			});
    }
});