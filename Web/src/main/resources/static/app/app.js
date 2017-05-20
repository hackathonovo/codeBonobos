var app = angular.module('application', ['ngRoute', 'ngResource', 'ui.bootstrap', 'ngMap', 'ngSanitize', 'com.2fdevs.videogular']);

app.config(['$routeProvider',
    function ($routeProvider, $locationProvider) {
        $routeProvider.when('/', {
            templateUrl: '/app/views/main.html',
            controller: 'MainController'
        }).when('/resc', {
            templateUrl: '/app/views/resc.html',
            controller: 'RescController'
        }).when('/action', {
            templateUrl: '/app/views/action.html',
            controller: 'ActionController'
        }).when('/current', {
            templateUrl: '/app/views/current.html',
            controller: 'CurrentController'
        }).when('/codes', {
            templateUrl: '/app/views/codes.html',
            controller: 'CodesController'
        }).when('/addAction', {
            templateUrl: '/app/views/addAction.html',
            controller: 'AddActionController'
        }).otherwise({
            redirectTo: '/'
        });

    }]);

app.controller('applicationController', ['$scope', '$sce', function ($scope, $sce) {
    $scope.showSidebar = false;

    $scope.toggleSidebar = function () {
        $scope.showSidebar = !$scope.showSidebar
    }
}]);

app.filter('cutFilter', function () {
    return function (value, wordwise, max, tail) {
        if (!value) return '';

        max = parseInt(max, 10);
        if (!max) return value;
        if (value.length <= max) return value;

        value = value.substr(0, max);
        if (wordwise) {
            var lastspace = value.lastIndexOf(' ');
            if (lastspace !== -1) {
                //Also remove . and , so its gives a cleaner result.
                if (value.charAt(lastspace - 1) === '.' || value.charAt(lastspace - 1) === ',') {
                    lastspace = lastspace - 1;
                }
                value = value.substr(0, lastspace);
            }
        }

        return value + (tail || ' …');
    };
});