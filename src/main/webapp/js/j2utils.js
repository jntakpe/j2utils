var j2utilsApp = angular.module('j2utilsApp', ['http-auth-interceptor', 'ngResource', 'ngRoute', 'ui.bootstrap']);

j2utilsApp.config(['$routeProvider', function ($routeProvider) {
        "use strict";
        $routeProvider
            .when('/', {templateUrl: 'views/login.html', controller: 'LoginController'})
            .when('/logout', {templateUrl: 'views/login.html', controller: 'LogoutController'})
            .when('/sessions', {templateUrl: 'views/sessions.html', controller: 'SessionsController', resolve: {
                resolvedSessions: ['Sessions', function (Sessions) {
                    return Sessions.query();
                }]
            }})
            .when('/badminton', {templateUrl: 'views/badminton.html', controller: 'BadmintonController',
                resolve: {
                    resolvedReservations: ['Reservations', function (Reservations) {
                        return Reservations.query();
                    }]
                }
            })
            .otherwise({templateUrl: 'views/portail.html', controller: 'PortailController'});
    }]).run(['$rootScope', '$location', 'AuthService', 'Utilisateur',
        function ($rootScope, $location, AuthService, Utilisateur) {
            "use strict";
            $rootScope.$on("$routeChangeStart", function (event, next, current) {
                //Vérifie si l'utilisateur est authentifié.
                AuthService.authenticate(function () {
                    $rootScope.authOK = true;
                });
            });

            //Fonction utilisée lorsque le serveur renvoi le code 401
            $rootScope.$on('event:auth-loginRequired', function (rejection) {
                $rootScope.authOK = false;
                if ($location.path() !== '/' && $location.path() !== "") {
                    $location.path('/').replace();
                }
            });

            //Fonction utilisée lorsque l'utilisateur s'est authentifié
            $rootScope.$on('event:auth-authConfirmed', function () {
                $rootScope.authOK = true;
                $rootScope.utilisateur = Utilisateur.get();
                if ($location.path() === '/') { //Si la page de login est demandée alors que l'utilisateur est déjà loggé
                    $location.path('/portail').replace();
                }
            });

            //Fonction utilisée lorsque l'utilisateur se connecte
            $rootScope.$on('event:auth-loginConfirmed', function () {
                $rootScope.authOK = true;
                $rootScope.utilisateur = Utilisateur.get();
                $location.path('').replace();
            });

            //Fonction utilisée lorsque l'utilisateur se déconnecte
            $rootScope.$on('event:auth-loginCancelled', function () {
                $rootScope.authOK = false;
                $location.path('');
            });
        }]);



