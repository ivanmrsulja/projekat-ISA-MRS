const PocetnaStrana = { template: '<pocetna-strana></pocetna-strana>' }
const TabelaPonuda = { template: '<pocetna-stranas></pocetna-stranas>' }
const RegistracijaKorisnika = {template: '<register-user></register-user>'}

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: PocetnaStrana},
	    { path: '/tab', component: TabelaPonuda},
	    { path: '/register', component: RegistracijaKorisnika}
	  ]
});


var app = new Vue({
	router,
	el: '#apoteke',
	data: {
        korisnik: {zaposlenjeKorisnika : "GOST"},
    },
	mounted () {
		let self = this;
		
		this.$root.$on('sendingUser', (data) => {
			this.korisnik = data;
		});
		
		alert(korisnik.zaposlenjeKorisnika);
    },
     methods: {
    	logout : function() {
    		let self = this;
    		axios
    			.get("/api/users/logout")
    			.then(function(resp){
    				if(resp.data == "OK"){
    					self.korisnik = {zaposlenjeKorisnika : "GOST"};
    					window.location.href = "#/";
    					self.$root.$emit('loggingUserOut', self.korisnik);
    				}
    			});
    	}   
    }
});