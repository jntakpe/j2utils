"use strict";

var j2utilsApp = angular.module('j2utilsApp', ['http-auth-interceptor', 'ngResource', 'ngRoute']);

j2utilsApp.config(['$routeProvider', '$httpProvider', function ($routeProvider, $httpProvider) {
    $routeProvider
        .when('/main', {templateUrl: 'views/main.html', controller: 'MainController'})
        .otherwise({templateUrl: 'views/login.html', controller: 'LoginController'});
}]);

