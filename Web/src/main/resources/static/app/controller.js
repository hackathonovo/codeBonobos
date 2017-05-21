app.controller('MainController', ['$scope', '$sce', '$http', '$route', 'UserFactory', 'NgMap', function ($scope, $sce, $http, $route, UserFactory, NgMap) {
    // ==== MODELS ====
    this.config = {
        preload: "none",
        sources: [
            {src: $sce.trustAsResourceUrl("http://static.videogular.com/assets/videos/videogular.mp4"), type: "video/mp4"},
            {src: $sce.trustAsResourceUrl("http://static.videogular.com/assets/videos/videogular.webm"), type: "video/webm"},
            {src: $sce.trustAsResourceUrl("http://static.videogular.com/assets/videos/videogular.ogg"), type: "video/ogg"}
        ],
        tracks: [
            {
                src: "http://www.videogular.com/assets/subs/pale-blue-dot.vtt",
                kind: "subtitles",
                srclang: "en",
                label: "English",
                default: ""
            }
        ],
        autoPlay: true,
        theme: {
            url: "http://www.videogular.com/styles/themes/default/latest/videogular.css"
        }
    };

    // ==== INIT MODELS ====


    // ==== CONTROL FUNCTIONS ====
    $scope.sampleFunction = function (project) {

    };
}]);

app.controller('RescController', ['$scope', 'RescFactory', function ($scope, RescFactory) {
    // ==== MODELS ====
    RescFactory.getAllGrouped().then(function (response) {
        var allResc = response.data.response;
        $scope.freeResc = allResc.available;
        $scope.actionResc = allResc.inAction;
        $scope.inactiveResc = allResc.inactive;

        $scope.filteredFreeResc = [];
        $scope.filteredActionResc = [];
        $scope.filteredInactiveResc = [];


        angular.copy($scope.freeResc, $scope.filteredFreeResc);
        angular.copy($scope.actionResc, $scope.filteredActionResc);
        angular.copy($scope.inactiveResc, $scope.filteredInactiveResc);
    });

    $scope.dict = {
        alpinizam: "Alpinizam",
        doktor: "Doktor",
        speleologija: "Speleologija",
        vodic_potraznih_pasa: "Vodić potražnih pasa",
        crtac_mapa: "Crtač mape",
        helikoptersko_spasavanje: "Helikoptersko spašavanje",
        turno_skijanje: "Turno skijanje"
    };

    $scope.specCategoryFilter = [
        {
            val: false,
            name: "alpinizam"

        },
        {
            val: false,
            name: "doktor"

        },
        {
            val: false,
            name: "helikoptersko_spasavanje"

        },
        {
            val: false,
            name: "crtac_mapa"

        },
        {
            val: false,
            name: "vodic_potraznih_pasa"

        },
        {
            val: false,
            name: "turno_skijanje"

        },
        {
            val: false,
            name: "speleologija"

        }
    ];
    // ==== INIT MODELS ====
    var v = 2;
    // ==== CONTROL FUNCTIONS ====
    $scope.specCategoryClick = function (ind) {
        $scope.specCategoryFilter[ind].val = !$scope.specCategoryFilter[ind].val;

        $scope.filteredFreeResc = [];
        $scope.filteredActionResc = [];
        $scope.filteredInactiveResc = [];

        var changed = false;

        $scope.specCategoryFilter.forEach(function (cat) {
            if (cat.val) {
                $scope.freeResc.forEach(function (e1) {
                    if (e1.specijalnost.toUpperCase() == cat.name.toUpperCase()) {
                        $scope.filteredFreeResc.push(e1);

                    }
                });
                $scope.actionResc.forEach(function (e2) {
                    if (e2.specijalnost.toUpperCase() == cat.name.toUpperCase()) {
                        $scope.filteredActionResc.push(e2);

                    }
                });
                $scope.inactiveResc.forEach(function (e3) {
                    if (e3.specijalnost.toUpperCase() == cat.name.toUpperCase()) {
                        $scope.filteredInactiveResc.push(e3);

                    }
                })
            }
        });

        $scope.specCategoryFilter.forEach(function (ch) {
            if (ch.val == true) {
                changed = true;
            }
        });

        if (!changed) {
            angular.copy($scope.freeResc, $scope.filteredFreeResc);
            angular.copy($scope.actionResc, $scope.filteredActionResc);
            angular.copy($scope.inactiveResc, $scope.filteredInactiveResc);
        }
    }
}]);

app.controller('ActionController', ['$scope', 'NgMap', function ($scope, $NgMap) {
    // ==== MODELS ====
    // ==== INIT MODELS ====
    // ==== CONTROL FUNCTIONS ====
}]);

app.controller('CurrentController', ['$scope', function ($scope) {
    // ==== MODELS ====
    // ==== INIT MODELS ====
    // ==== CONTROL FUNCTIONS ====
}]);

app.controller('CodesController', ['$scope', function ($scope) {
    // ==== MODELS ====
    // ==== INIT MODELS ====
    // ==== CONTROL FUNCTIONS ====
}]);

app.controller('AddActionRescController', ['$scope', '$location', '$filter', 'NgMap', 'ActionFactory', 'RescFactory', 'ActionCreate', function ($scope, $location, $filter, NgMap, ActionFactory, RescFactory, ActionCreate) {
    // ==== MODELS ====

    $scope.actionId = ActionCreate.get();

    $scope.freeResc = [];
    $scope.filteredFreeResc = [];

    $scope.tmp =  RescFactory.getAllNear($scope.actionId).then(function (response) {
        $scope.freeResc = response.data.response;
        angular.copy($scope.freeResc, $scope.filteredFreeResc);
    });
    //


    $scope.dict = {
        alpinizam: "Alpinizam",
        doktor: "Doktor",
        speleologija: "Speleologija",
        vodic_potraznih_pasa: "Vodić potražnih pasa",
        crtac_mapa: "Crtač mape",
        helikoptersko_spasavanje: "Helikoptersko spašavanje",
        turno_skijanje: "Turno skijanje"
    };

    $scope.specCategoryFilter = [
        {
            val: false,
            name: "alpinizam"

        },
        {
            val: false,
            name: "doktor"

        },
        {
            val: false,
            name: "helikoptersko_spasavanje"

        },
        {
            val: false,
            name: "crtac_mapa"

        },
        {
            val: false,
            name: "vodic_potraznih_pasa"

        },
        {
            val: false,
            name: "turno_skijanje"

        },
        {
            val: false,
            name: "speleologija"

        }
    ];
    // ==== INIT MODELS ====
    var v = 2;
    // ==== CONTROL FUNCTIONS ====

    $scope.select = function(item) {
        item.selected ? item.selected = false : item.selected = true;
    };

    $scope.getAllSelectedRows = function() {
        var x = $filter("filter")($scope.filteredFreeResc, {
            selected: true
        }, true);
        console.log(x);

        var selected = [];
        x.forEach(function (wrapper) {
            selected.push(wrapper.rescuer.id)
        });
        
        ActionFactory.addRescToAction($scope.actionId, selected);

        $location.url('/current')
    };

    $scope.selectAll = function () {
        $scope.filteredFreeResc.forEach(function (en) {
            en.selected ? en.selected = false : en.selected = true;
        })
    };
    
    $scope.generateCssClass = function (distance) {
        if(distance<25) {
            return "distance_close"
        } else if(distance>25 && distance<60) {
            return "distance_mid"
        } else {
            return "distance_long"
        }
    };

    $scope.specCategoryClick = function (ind) {
        $scope.specCategoryFilter[ind].val = !$scope.specCategoryFilter[ind].val;

        $scope.filteredFreeResc = [];

        var changed = false;

        $scope.specCategoryFilter.forEach(function (cat) {
            if (cat.val) {
                $scope.freeResc.forEach(function (e1) {
                    if (e1.rescuer.specijalnost.toUpperCase() == cat.name.toUpperCase()) {
                        $scope.filteredFreeResc.push(e1);
                    }
                });
            }
        });

        $scope.specCategoryFilter.forEach(function (ch) {
            if (ch.val == true) {
                changed = true;
            }
        });

        if (!changed) {
            angular.copy($scope.freeResc, $scope.filteredFreeResc);
        }
    }
}]);

app.controller('AddActionController', ['$scope', '$location', 'NgMap', 'ActionFactory', 'ActionCreate', function ($scope, $location, NgMap, ActionFactory, ActionCreate) {
    // ==== MODELS ====
    $scope.currentAction = {
        id: null,
        ime: null,
        isActive: true,
        opis: null,
        pozvaniSpasavatelji: null,
        prihvaceniSpasavatelji: null,
        location: {
            lat: "45.00",
            lng: "15.00"
        },
        radius: 0,
        voditelj: null,
        meetingTime: null,
        meetingLocation: null
    };
    // ==== INIT MODELS ====
    $scope.tmpTime = null;
    // ==== CONTROL FUNCTIONS ====
    $scope.createAction = function () {
        $scope.currentAction.meetingTime = $scope.tmpTime.getHours()+":"+$scope.tmpTime.getMinutes();
        var id = ActionFactory.addAction($scope.currentAction);
        $location.url('/addActionResc')
    };

    $scope.markerChanged = function (event) {
        $scope.currentAction.location.lat = event.latLng.lat();
        $scope.currentAction.location.lng = event.latLng.lng();
    }
}]);

app.controller('AddRescController', ['$scope', '$location', 'RescFactory', function ($scope, $location, RescFactory) {
    // ==== MODELS ====
    $scope.currentResc = {
        id: null,
        ime: null,
        isActive: false,
        brojTelefona: null,
        specijalnost: null,
        iskustvo: null,
        lokacija: {
            lat: null,
            lng: null
        }
    };

    $scope.spec = [
        {
            id: 1,
            name: "Speleologija",
            csscl: "",
            enum: "SPELEOLOGIJA"
        },
        {
            id: 2,
            name: "Alpinizam",
            csscl: "",
            enum: "ALPINIZAM"
        },
        {
            id: 3,
            name: "Turno skijanje",
            csscl: "",
            enum: "TURNO_SKIJANJE"
        },
        {
            id: 4,
            name: "Vodić potražnih pasa",
            csscl: "",
            enum: "VODIC_POTRAZNIH_PASA"
        },
        {
            id: 5,
            name: "Doktor",
            csscl: "",
            enum: "DOKTOR"
        },
        {
            id: 6,
            name: "Crtač mape",
            csscl: "",
            enum: "CRTAC_MAPA"
        },
        {
            id: 7,
            name: "Helikoptersko spašavanje",
            csscl: "",
            enum: "HELIKOPTERSKO_SPASAVANJE"
        }
    ];

    $scope.exp = [
        {
            id: 1,
            name: "Pridruženi član",
            enum: "PRIDRUZENI_CLAN"
        },
        {
            id: 2,
            name: "Pripravnik",
            enum: "PRIPRAVNIK"
        },
        {
            id: 3,
            name: "Gorski spasitelj",
            enum: "ISKUSAN"
        }
    ];

    $scope.selectedExp = null;
    // ==== INIT MODELS ====
    // ==== CONTROL FUNCTIONS ====
    $scope.selectSpec = function (s) {
        $scope.selectedSpec = s.id;
    };

    $scope.saveResc = function () {
        $scope.currentResc.iskustvo = $scope.selectedExp.enum;
        var val = $scope.selectedSpec - 1;
        $scope.currentResc.specijalnost = $scope.spec[val].enum;
        RescFactory.addResc($scope.currentResc);

        $location.url('/resc')
    };
}]);