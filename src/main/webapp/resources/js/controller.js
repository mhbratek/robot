angular.module('searchingManager', [])
    .controller('AppCtl', function($scope, $filter, $http, jsonFilter){

        $scope.getStartBooks = function() {
            $http.get('/robot/rest/startBooks').success(function(data) {
                $scope.books = data;
            });
        };

        $scope.getBooks = function(data) {
            window.alert($scope.myFilter+ "   " + data);
            $http.get('/robot/rest/books/'+$scope.myFilter+"/"+data).success(function(data) {
                $scope.books = data;
            });
        };

        $scope.setFilter = function(data) {
            $scope.myFilter = data;
            window.alert($scope.myFilter);
        };

        $scope.myFilter = 'Set filter'
    });
