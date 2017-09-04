angular.module('searchingManager', ['angularUtils.directives.dirPagination'])
    .controller('AppCtl', function($scope, $filter, $http, jsonFilter){

        $scope.getBooks = function(data) {
            $http.get('/robot/rest/books/'+$scope.myFilter+"/"+data).success(function(data) {
                $scope.books = data;
            });
        };

        $scope.getBooksWithPriceRange = function(data, lower, higher) {
            $http.get('/robot/rest/books/'+$scope.myFilter+"/"+data+"/price;low="+lower+";high="+higher).success(function(data) {
                $scope.books = data;
            });
        };

        $scope.setFilter = function(data) {
            $scope.myFilter = data;
            window.alert($scope.myFilter);
        };

        $scope.lower = 0;
        $scope.higher = 200;
        $scope.myFilter = 'Set filter'
        $scope.currentPage = 1;
        $scope.pageSize = 20;
        $scope.meals = [];
    });

function OtherController($scope) {
    $scope.pageChangeHandler = function(num) {
        console.log('going to page ' + num);
    };
}