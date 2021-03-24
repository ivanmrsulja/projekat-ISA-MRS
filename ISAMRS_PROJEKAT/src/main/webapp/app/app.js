const PocetnaStrana = { template: '<pocetna-strana></pocetna-strana>' }
const TabelaPonuda = { template: '<pocetna-stranas></pocetna-stranas>' }
const RegistracijaKorisnika = {template: '<register-user></register-user>'}
const PregledApoteka = {template: '<pregled-apoteka></pregled-apoteka>'}
const ProfilApoteke = {template: '<profil-apoteke></profil-apoteke>'}
const dermatoloziTable = {template: '<profil-dermatolozi></profil-dermatolozi>'}
const farmaceutiTable = {template: '<profil-farmaceuti></profil-farmaceuti>'}

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: PocetnaStrana},
	    { path: '/tab', component: TabelaPonuda},
	    { path: '/dermatolozi', component: dermatoloziTable},
	    { path: '/farmaceuti', component: farmaceutiTable},
	    { path: '/register', component: RegistracijaKorisnika},
	    { path: '/apoteke/:page', component: PregledApoteka},
		{ path: '/profileApoteke', component: ProfilApoteke},
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