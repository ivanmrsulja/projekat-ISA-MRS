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
			axios
			.get("api/preparat/search/"+usr)
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
			  <tr><td style="color:white">Filtriraj po:</td> 
			  		<td>
				  		<select name="tip" id="kriterijum" v-model="searchParams.filter" >
						  <option value="SVI">SVI</option>
						  <option value="TIP">TIP</option>
						  <option value="OCENA">OCENA</option>
						</select>
					</td></tr>
			  <tr><td colspan=2 align=center ><input type="button" v-on:click="pretragaPreparata" name="search" value="Pretrazi" /></td></tr>
		  </table>
	</div>
		
	<div id="main">
	  <button class="openbtn" onclick="openNav()">&#9776; Pretraga</button>
	</div>
	
	<br/>
	<table class="table table-hover">
	 <thead>
		<tr bgcolor="#90a4ae">
			<th>Id</th>
			<th>Naziv</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<tr v-for="preparat in preparati""	>
                <td>{{preparat.id}}</td>
                <td>{{preparat.naziv}}</td> 
                <td align = center><input type="button" value="Pregledaj" v-on:click="pregledajPreparat(preparat)"/></td>                               
		</tr>
	</tbody>
	</table>
	
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