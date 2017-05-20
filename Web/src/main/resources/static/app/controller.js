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

app.controller('AddRescController', ['$scope', function ($scope) {
    // ==== MODELS ====
    // ==== INIT MODELS ====
    // ==== CONTROL FUNCTIONS ====
}]);