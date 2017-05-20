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

app.controller('RescController', ['$scope', function ($scope) {
    // ==== MODELS ====
    // ==== INIT MODELS ====
    // ==== CONTROL FUNCTIONS ====
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

app.controller('AddActionController', ['$scope', function ($scope) {
    // ==== MODELS ====
    // ==== INIT MODELS ====
    // ==== CONTROL FUNCTIONS ====
}]);

app.controller('AddRescController', ['$scope', 'RescFactory', function ($scope, RescController) {
    // ==== MODELS ====
    $scope.currentResc = {
        id: null,
        ime: null,
        isActive: false,
        brojTelefona: null,
        specijalnost: null,
        iskustvo: null,
        lokacije: {
            long: null,
            lat: null
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
        $scope.currentResc.specijalnost = $scope.spec[$scope.selectedSpec].enum;
        RescFactory.addResc($scope.currentResc);
    };
}]);