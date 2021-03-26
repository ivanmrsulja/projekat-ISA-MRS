Vue.component("pregled-apoteka", {
	data: function () {
		    return {
				apoteke : {},
				numPages: 1
		    }
	},
	template: ` 
<div align = center style="width:75%">
		
		<h1>Pregled apoteka</h1>
		<br/>
		
		<div class="card" v-for="a in this.apoteke">
		  <div class="post-container">
	      <div class="post-thumb"><img src="css/drugstore.jpg" style="height:200px;"></img></div>
		  <div class="post-content">
	      <h2 style="margin-bottom:6px">{{a.naziv}}</h2>
	      <p>{{a.lokacija.ulica}}</p>
	      
	      <p>{{a.opis}}</p>
	      
	      <table>
	      	<tr><td><input type="button" class="button1" value="Vise informacija" v-on:click="pregledaj(a)" /></td><td style="padding = 0px; margin = 0px;" v-bind:hidden=" a.ocena == 0" ><strong>Ocena: {{a.ocena}}</strong></td></tr>
	      </table>
	      </div></div>
	    </div>
		
		<div class="pagination" v-for="i in numPages+1" :key="i" >
		  <a :href="'#/apoteke/'+(i-1)" v-on:click="loadNext(i-1)">{{i}}</a>
		</div>
</div>		  
`
	,
	methods: {
		loadNext: function(p){
			axios
			.get("api/apoteke/all/" + p)
			.then(response => {
				this.apoteke = response.data.content;
			});
		}
	},
	mounted: function() {
		axios
			.get("api/apoteke/all/" + this.$route.params.page)
			.then(response => {
				this.apoteke = response.data.content;
				this.numPages = response.data.totalPages - 1;
				
			});
    }
});