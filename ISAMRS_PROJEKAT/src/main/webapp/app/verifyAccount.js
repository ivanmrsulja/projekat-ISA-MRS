Vue.component("verify-account", {
	data: function () {
		    return {
				
				msg: ""
		    }
	},
	template: ` 
<div align = center style="width:75%">

		<h1>{{msg}}</h1>
		<a href="#/" class="btn btn-primary">Nazad</a>
		

	
	
</div>		  
`
	,
	methods: {	
    	pregledajBtn : function(event) {
    		
    	}
    },
	mounted: function() {
	    var self = this;
		axios
			.put("/api/pacijenti/activate/"+self.$route.params.id)
			.then(function(re){
				self.msg = re.data;
				
			});
	  	
    }
});