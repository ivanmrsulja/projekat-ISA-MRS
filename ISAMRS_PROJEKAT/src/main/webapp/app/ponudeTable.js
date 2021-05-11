Vue.component("pocetna-stranas", {
	data: function () {
		    return {
				offers : []
				//let tip = $("#kriterijum").children("option:selected").val();
		    }
	},
	template: ` 
<div align = center style="width:75%">
		
		<h1>Moje ponude</h1>
		<br/>
		<div id="mySidebar" class="sidebar">
		  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
		  <table>
			  <tr><td style="color:white">Filtriraj po statusu:</td> 
			  		<td>
				  		<select name="tip" id="kriterijum">
				  		  <option value="SVI">SVE PONUDE</option>
						  <option value="PRIHVACENA">PRIHVACENA</option>
						  <option value="ODBIJENA">ODBIJENA</option>
						  <option value="CEKA_NA_ODGOVOR">CEKA NA ODGOVOR</option>
						</select>
					</td></tr>
			  <tr><td colspan=2 align=center ><input type="button" name="search" value="Filter" v-on:click="pregledajBtn" /></td></tr>
		  </table>
	</div>
		
	<div id="main">
	  <button class="openbtn" onclick="openNav()">&#9776; Pretraga</button>
	</div>
	</br>
		<table class="table table-hover">
            <thead>
            	<tr>
                <th scope="col">Id</th>
                <th scope="col">Status</th>
                <th scope="col">Ukupna cena</th>
               	<th scope="col">Rok isporuke</th>
             	<th scope="col">Narudzbenica</th>
           		<th scope="col">Dobavljac</th>

                </tr>
           	</thead>
            <tbody>
                <tr v-for="offer in offers" v-bind:key="offer.id">
                                <th scope="row">{{offer.id}}</th>
                                <td>{{offer.status}}</td>
                                <td>{{offer.ukupnaCena}}</td>
                                <td>{{offer.rokIsporuke}}</td>
                                <td>{{offer.idNarudzbenice}}</td>
                                <td>{{offer.dobavljac}}</td>
            	</tr>           
            </tbody>
     	</table>

	
	
</div>		  
`
	,
	methods: {	
    	pregledajBtn : function() {
    		let tip = $("#kriterijum").children("option:selected").val();
    		alert(tip);
    		axios
			.get("api/admin/cures/"+tip)
			.then(response => {
				this.offers = response.data;
			});
    	}
    },
	mounted: function() {
		axios
			.get("api/admin")
			.then(response => {
				this.offers = response.data;
				
			});
    }
});