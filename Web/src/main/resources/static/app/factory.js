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

app.factory('ActionFactory', ['$http', 'ActionCreate', function ($http, ActionCreate) {
    var result = {};
    
    result.addAction = function (action) {
        $http({
            method: 'PUT',
            url: '/api/akcije/add',
            data: action,
            headers: {'Content-Type': 'application/json; charset=UTF-8'}
        }).success(function (data, status, header, config) {
            ActionCreate.set(data.response.id);
            return data.response.id;
        }).error(function (data, status, header, config) {
            // TODO
            return -1;
        });
    };

    result.addRescToAction = function (actionID, rescIdArray) {
        $http({
            method: 'POST',
            url: '/api/akcije/invite-rescuers/'+actionID,
            data: rescIdArray,
            headers: {'Content-Type': 'application/json; charset=UTF-8'}
        }).success(function (data, status, header, config) {
            ActionCreate.set(data.response.id);
            return data.response.id;
        }).error(function (data, status, header, config) {
            // TODO
            return -1;
        });
    };
    
    result.getAllActive = function () {
        return $http.get("/api/akcije/active")
    };

    return result;
}]);

app.factory('RescFactory', ['$http', 'ActionCreate', function ($http, ActionCreate) {
    var result = {};

    result.getAllGrouped = function () {
        return $http.get('/api/spasavatelji/all-grouped')
    };

    result.getAll = function () {
        return $http.get('/api/spasavatelji/all')
    };
    
    result.getAllNear = function (actionID) {
        return $http.get('/api/spasavatelji/get-closest?actionId='+actionID);
    };

    result.addResc = function (resc) {
        $http({
            method: 'PUT',
            url: '/api/spasavatelji/add',
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

app.factory('ActionCreate', function() {
    var savedData = {};
    function set(data) {
        savedData = data;
    }
    function get() {
        return savedData;
    }

    return {
        set: set,
        get: get
    }

});