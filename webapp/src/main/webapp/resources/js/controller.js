angular.module('searchingManager', ['angularUtils.directives.dirPagination'])
    .controller('AppCtl', function($scope, $filter, $http, jsonFilter){

        $scope.getBooksByFilters = function(author, title, category, bookstore, lower, higher, version) {
            $http.get('/robot/rest/books/filters;price='+lower+","+higher+";author="+author+";title="+title+";category="+category+";bookstore="+bookstore+";version="+version).success(function(data) {
                $scope.books = data;
            });
        };

        $scope.setFilter = function(data) {
            $scope.myFilter = data;
            window.alert($scope.myFilter);
        };

        $scope.clickHandler = function(){
            $scope.isHidden = !$scope.isHidden;
        };

        $scope.lower = 0;
        $scope.higher = 200;
        $scope.myFilter = 'Set filter'
        $scope.currentPage = 1;
        $scope.pageSize = 20;
    });

function OtherController($scope) {
    $scope.pageChangeHandler = function(num) {
        console.log('going to page ' + num);
    };
}