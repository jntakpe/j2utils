var j2utilsApp = angular.module('j2utilsApp', ['http-auth-interceptor', 'ngResource', 'ngRoute', 'ui.bootstrap']);

j2utilsApp.config(['$routeProvider', function ($routeProvider) {
    "use strict";
    $routeProvider
        .when('/', {templateUrl: 'views/login.html', controller: 'LoginController'})
        .when('/logout', {templateUrl: 'views/login.html', controller: 'LogoutController'})
        .otherwise({templateUrl: 'views/portail.html', controller: 'PortailController'});
}]);



