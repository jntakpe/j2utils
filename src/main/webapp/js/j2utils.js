"use strict";

var j2utilsApp = angular.module('j2utilsApp', ['http-auth-interceptor', 'ngResource', 'ngRoute']);

j2utilsApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/', {templateUrl: 'views/login.html', controller: 'LoginController'})
        .otherwise({templateUrl: 'views/portail.html', controller: 'PortailController'});
}]);

