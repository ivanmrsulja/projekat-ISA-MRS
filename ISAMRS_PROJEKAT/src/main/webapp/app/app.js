const PocetnaStrana = { template: '<pocetna-strana></pocetna-strana>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: PocetnaStrana},
	  ]
});


var app = new Vue({
	router,
	el: '#apoteke',
	data: {
        korisnik: {uloga : "GOST"},
    },
	mounted () {
		let self = this;
		$.get("/api/users/currentUser", function(data){
			if(data){
				self.korisnik = data;
			}
		});
		
		this.$root.$on('sendingUser', (data) => {
			this.korisnik = data;
		});
    },
     methods: {
    	logout : function() {
    		let self = this;
    		axios
    			.get("/api/users/logout")
    			.then(function(resp){
    				if(resp.data == "OK"){
    					self.korisnik = {uloga : "GOST"};
    					window.location.href = "#/";
    					self.$root.$emit('loggingUserOut', self.korisnik);
    				}
    			});
    	}   
    }
});