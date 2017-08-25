angular.module('searchingManager', [])
    .controller('AppCtl', function($scope, $filter, $http, jsonFilter){

        $scope.getBooks = function() {
            $http.get('/robot/rest/books').success(function(data) {
                $scope.books = data;
            });
        };

        $scope.setFilter = function(data) {
            $scope.myFilter = data;
        };

        console.log(jsonFilter($scope.books));

    });
