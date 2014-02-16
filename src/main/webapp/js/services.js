"use strict";

j2utilsApp.factory('Utilisateur', ['$resource',
    function ($resource) {
        return $resource('j2utils/rest/utilisateur');
    }]);

j2utilsApp.factory('AuthService', ['$rootScope', '$http', 'authService',
        function ($rootScope, $http, authService) {
            return {
                //Vérifie si l'utilisateur est connecté
                authenticate: function () {
                    $http.get('j2utils/rest/auth')
                        .success(function (data) {
                            $rootScope.login = data;
                            if (data == '') {
                                $rootScope.$broadcast('event:auth-loginRequired');
                            } else {
                                $rootScope.$broadcast('event:auth-authConfirmed');
                            }
                        })
                },
                //Tentative de connexion (envoi formulaire de login)
                login: function (param) {
                    var data = "j_username=" + param.username + "&j_password=" + param.password + "&_spring_security_remember_me=" +
                        param.rememberMe + "&submit=Login";
                    $http.post('j2utils/authentication', data, {
                        headers: {
                            "Content-Type": "application/x-www-form-urlencoded"
                        },
                        ignoreAuthModule: 'ignoreAuthModule'
                    }).success(function (data, status, headers, config) {
                        $rootScope.authenticationError = false;
                        authService.loginConfirmed();
                        if (param.success) {
                            param.success(data, status, headers, config);
                        }
                    }).error(function (data, status, headers, config) {
                        console.log("auth error");
                        $rootScope.authenticationError = true;
                        if (param.error) {
                            param.error(data, status, headers, config);
                        }
                    });
                },
                //Tentative de déconnexion
                logout: function () {
                    $rootScope.authenticationError = false;
                    $http.get('j2utils/logout')
                        .success(function () {
                            $rootScope.login = null;
                            authService.loginCancelled();
                        });
                }
            };
        }])
    .run(['$rootScope', '$location', 'AuthService', 'Utilisateur',
        function ($rootScope, $location, AuthService, Utilisateur) {
            console.log('test');
            $rootScope.hasRole = function (role) {
                if ($rootScope.account === undefined) {
                    return false;
                }

                if ($rootScope.account.roles === undefined) {
                    return false;
                }

                if ($rootScope.account.roles[role] === undefined) {
                    return false;
                }

                return $rootScope.account.roles[role];
            };

            $rootScope.$on("$routeChangeStart", function (event, next, current) {
                // Check if the status of the user. Is it authenticated or not?
                AuthService.authenticate({}, function () {
                    $rootScope.authOK = true;
                });
            });

            // Call when the 401 response is returned by the client
            $rootScope.$on('event:auth-loginRequired', function (rejection) {
                $rootScope.authOK = false;
                if ($location.path() !== '/' && $location.path() !== "") {
                    $location.path('/').replace();
                }
            });

            // Call when the user is authenticated
            $rootScope.$on('event:auth-authConfirmed', function () {
                $rootScope.authOK = true;
                $rootScope.utilisateur = Utilisateur.get();

                // If the login page has been requested and the user is already logged in
                // the user is redirected to the home page
                if ($location.path() === '/') {
                    $location.path('/home').replace();
                }
            });

            // Call when the user logs in
            $rootScope.$on('event:auth-loginConfirmed', function () {
                $rootScope.authOK = true;
                $rootScope.utilisateur = Utilisateur.get();
                $location.path('').replace();
            });

            // Call when the user logs out
            $rootScope.$on('event:auth-loginCancelled', function () {
                $rootScope.authOK = false;
                $location.path('');
            });
        }]);
