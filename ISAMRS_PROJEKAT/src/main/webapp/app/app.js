const LoginStrana = { template: '<pocetna-strana></pocetna-strana>' };
const TabelaPonuda = { template: '<pocetna-stranas></pocetna-stranas>' };
const RegistracijaKorisnika = { template: '<register-user></register-user>'};
const PregledApoteka = { template: '<pregled-apoteka></pregled-apoteka>'};
const ProfilApoteke = { template: '<profil-apoteke></profil-apoteke>'};
const dermatoloziTable = { template: '<profil-dermatolozi></profil-dermatolozi>'};
const farmaceutiTable = { template: '<profil-farmaceuti></profil-farmaceuti>'};
const IstorijaPregleda = { template: '<istorija-pregleda></istorija-pregleda>'};
const ZakazaniPregledi = { template: '<zakazani-pregledi></zakazani-pregledi>'};
const ProfilPacijenta = { template: '<profil-pacijenta></profil-pacijenta>'};
const PregledRezervacija = { template: '<lista-rezervacija></lista-rezervacija>'};
const PregledErecepata = { template: '<pregled-erecepata></pregled-erecepata>'};
const PregledStavkiErecepta = { template: '<pojedinacni-erecept></pojedinacni-erecept>'};
const AkcijePromocije = { template: '<akcije-promocije></akcije-promocije>'};
const PregledApoteke = { template: '<pregled-apoteke></pregled-apoteke>'};
const preparatiTable = {template: '<profil-preparati></profil-preparati>'};
const PregledStavkiPreparata = { template: '<pojedinacni-preparat></pojedinacni-preparat>'};
const RegistracijaFarmaceuta = { template: '<register-pharmacist></register-pharmacist>'};
const registracijaDobavljaca = { template: '<register-supplier></register-supplier>' };
const registracijaDermatologa = { template: '<register-dermatolog></register-dermatolog>' };
const StranicaZalbe = {template: '<pisanje-zalbe></pisanje-zalbe>'};
const pacijentTable = {template: '<profil-pacijenti></profil-pacijenti>'};
const PregledPacijenta = { template: '<pojedinacni-pacijent></pojedinacni-pacijent>'};

const ZakazivanjeSavetovanja = { template: '<zakazivanje-savetovanja></zakazivanje-savetovanja>'};
const ZakazivanjeSavetovanjaKorak2 = { template: '<zakazivanje-savetovanjaK2></zakazivanje-savetovanjaK2>'};

const ZapocniPregled = { template: '<pacijent-pregled></pacijent-pregled>'};
const ZapocniNoviPregled = { template: '<zakazivanje-termina></zakazivanje-termina>'};
const RadniKalendar = { template: '<radni-kalendar></radni-kalendar>'};
const RadniKalendarFarmaceut = { template: '<radni-kalendar-farmaceut></radni-kalendar-farmaceut>'};




const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: LoginStrana},
	    { path: '/tab', component: TabelaPonuda},
	    { path: '/dermatolozi', component: dermatoloziTable},
	    { path: '/pregledi', component: RadniKalendar },
	    { path: '/savetovanja', component: RadniKalendarFarmaceut },
	    { path: '/preparati', component: preparatiTable},
	    { path: '/preparati/:spec', component: PregledStavkiPreparata },
	    { path: '/farmaceuti', component: farmaceutiTable},
	    { path: '/register', component: RegistracijaKorisnika},
	    { path: '/apoteke/:page', component: PregledApoteka},
		{ path: '/profileApoteke', component: ProfilApoteke},
		{ path: '/istorijaPregleda/:page/:criteria', component: IstorijaPregleda},
		{ path: '/zakazaniPregledi/:page', component: ZakazaniPregledi},
		{ path: '/profilPacijenta', component: ProfilPacijenta},
		{ path: '/pregledRezervacija', component: PregledRezervacija},
		{ path: '/eRecepti', component: PregledErecepata },
		{ path: '/eRecepti/:recept', component: PregledStavkiErecepta },
		{ path: '/apoteke/pregled/:id', component: PregledApoteke, name: "PregledApoteke" },
		{ path: '/akcijePromocije', component: AkcijePromocije },
		{ path: '/registracijaFarmaceuta', component: RegistracijaFarmaceuta },
		{ path: '/regSupp', component: registracijaDobavljaca },
        { path: '/regDerm', component: registracijaDermatologa },
	    { path: '/pacijenti', component: pacijentTable},
	    { path: '/pacijenti/:spec', component: PregledPacijenta },

	    { path: '/zalbe', component: StranicaZalbe },
	    { path: '/zakaziSavetovanje/:page/', component: ZakazivanjeSavetovanja },
	    { path: '/zakaziSavetovanje/:page/:apoteka', component: ZakazivanjeSavetovanjaKorak2 },

	    { path: '/pacijenti/zapocniPregled/:spec', component: ZapocniPregled },
	    { path: '/pacijenti/zapocniNoviPregled/:spec', component: ZapocniNoviPregled },

	    
	  ]
	});




var app = new Vue({
    router,
    el: '#apoteke',
    data: {
        korisnik: { zaposlenjeKorisnika: "GOST" },
    },
    mounted() {
        let self = this;
        axios.get("/api/users/currentUser").then(function(data) {
            if (data.data) {
                self.korisnik = data.data;
            }
        });
        this.$root.$on('sendingUser', (data) => {
            this.korisnik = data;
        });
    },
    methods: {
        logout: function() {
            let self = this;
            axios
                .get("/api/users/logout")
                .then(function(resp) {
                    if (resp.data == "OK") {
                        self.korisnik = { zaposlenjeKorisnika: "GOST" };
                        self.$router.push({ path: "/" });
                        self.$root.$emit('loggingUserOut', self.korisnik);
                    }
                });
        }
    }
});