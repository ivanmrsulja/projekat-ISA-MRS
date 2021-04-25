Vue.component("radni-kalendar", {
	data: function () {
		    return {
				pregledi : [],
				korisnik:{}
		    }
	},
	
	template: ` 
<div align = center style="width:75%">
		
		
		<div class="container">
		
		

<h2>Radni kalendar</h2>
    <p class="lead">
        Pregled radnog kalendara za predstojeci period
    </p>
   <br/>

    <div class="agenda">
        <div class="table-responsive">
            <table class="table table-condensed table-bordered">
               <thead>
                    <tr>
                        <th>Datum</th>
                        <th>Vreme</th>
                        <th>Pacijent</th>
                        <th>Akcija</th>
                    </tr>
                </thead>
                
                                
                    <!-- Multiple events in a single day (note the rowspan) -->
                    <tbody v-for="d in pregledi">
                    <br/>
                    <tr>
                        <td class="agenda-date active"  :rowspan="d.length+1">
                            <div class="dayofmonth">{{d[0].datum.split("-")[2]}}</div>
                            <div class="shortdate text-muted">{{d[0].datum.split("-")[1]}}, {{d[0].datum.split("-")[0]}}</div>
                        </td>
                        
                    </tr>
                    	<tr v-for="p in d">
                        	<td class="agenda-time">{{p.vrijeme + " [trajanje: " +p.trajanje+"]" }}</td>
                        	<td class="agenda-events">{{p.pacijent.ime + " " + p.pacijent.prezime}} </td>
                        	<td align=center><input type="button" class="button1" v-on:click="zapocniPregled(p)" value="Zapocni Pregled" /></td>
                        </tr>
                    </tbody>
                    
                    
                
            </table>
        </div>
    </div>
</div>


	
</div>		  
`
	,
	methods: {
		zapocniPregled : function(r){
    		this.$router.push({ path: "/pacijenti/zapocniPregled/"+r.id });
		},
	},
	mounted: function() {
		axios.get("/api/users/currentUser").then(response => {
            if(response.data){
            	this.korisnik = response.data;
                axios
				.get("api/dermatolog/pregledi")
				.then(response => {
					this.pregledi = response.data;
				});
            }else{
            	this.$router.push({ path: "/" });
            }
        });	
    }
});