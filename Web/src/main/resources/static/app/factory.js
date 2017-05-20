// USER FACTORY
app.factory('UserFactory', ['$http', function ($http) {
    var result = {};

    result.getAll = function () {
        //return $http.get('/api/users')
    };

    result.addProject = function (project) {
        $http({
            method: 'POST',
            url: '/users',
            data: project,
            headers: {'Content-Type': 'application/json; charset=UTF-8'}
        }).success(function (data, status, header, config) {
            return data.id;
        }).error(function (data, status, header, config) {
            // TODO
            return -1;
        });
    };

    result.deleteProject = function (user) {
        /*
        $http.delete('/users/' + user.id);
        return project.id;
        */
    };

    return result;
}]);

app.factory('RescFactory', ['$http', function ($http) {
    var result = {};

    result.getAllGrouped = function () {
        return $http.get('/api/spasavatelji/all-grouped')
    };

    result.getAll = function () {
        return $http.get('/api/spasavatelji/all')
    };

    result.addResc = function (resc) {
        $http({
            method: 'POST',
            url: '/api/spasavatelj/add',
            data: resc,
            headers: {'Content-Type': 'application/json; charset=UTF-8'}
        }).success(function (data, status, header, config) {
            return data.id;
        }).error(function (data, status, header, config) {
            // TODO
            return -1;
        });
    };

    return result;
}]);