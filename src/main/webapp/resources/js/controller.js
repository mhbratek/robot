angular.module('searchingManager', [])
    .controller('AppCtl', function($scope, $filter, jsonFilter){

        $scope.books = [
            {
                title: 'Potop',
                author: 'Henryk Sienkiewicz',
                category: 'history novel',
                promoDetails: '-15%',
                price: '42',
                bookstore: 'bonito'
            },
            {
                title: 'Lalka',
                author: 'Bolesław Prus',
                category: 'novel',
                promoDetails: '-20%',
                price: '25',
                bookstore: 'bonito'
            },
            {
                title: 'Potop',
                author: 'Henryk Sienkiewicz',
                category: 'history novel',
                promoDetails: '-35%',
                price: '30',
                bookstore: 'helion'
            },
            {
                title: 'Krew Elfów',
                author: 'Andrzej Sapkowski',
                category: 'fantasy',
                promoDetails: '-35%',
                price: '32',
                bookstore: 'bonito'
            }
        ];

        console.log(jsonFilter($scope.books));

    });
