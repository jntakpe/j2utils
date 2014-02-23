j2utilsApp.factory('Utilisateur', ['$resource', function ($resource) {
    "use strict";
    return $resource('j2utils/rest/utilisateur');
}]);

j2utilsApp.factory('Sessions', ['$resource', function ($resource) {
    "use strict";
    return $resource('j2utils/rest/utilisateur/sessions/:series', {}, {get: {method: 'GET', isArray: true}});
}]);

j2utilsApp.factory('Reservations', ['$resource', function ($resource) {
    "use strict";
    return $resource('j2utils/rest/reservations/:reservation', {}, {get: {method: 'GET', isArray: true}});
}]);

j2utilsApp.factory('AuthService', ['$rootScope', '$http', 'authService', function ($rootScope, $http, authService) {
        "use strict";
        return {
            /**
             * Vérifie si l'utilisateur est connecté
             * @param [authOK] callback en cas d'authentification
             * @param [authKO] callback en cas d'utilisateur non autorisé
             */
            authenticate: function (authOK, authKO) {
                $http.get('j2utils/rest/auth')
                    .success(function (data) {
                        if (data === '') {
                            $rootScope.$broadcast('event:auth-loginRequired');
                            if (authKO) {
                                authKO();
                            }
                        } else {
                            $rootScope.$broadcast('event:auth-authConfirmed');
                            if (authOK) {
                                authOK(data);
                            }
                        }
                    });
            },
            /**
             * Tentative de connexion (envoi formulaire de login)
             * @param username valeur du nom de l'utilisateur
             * @param password valeur du mot de passe
             * @param rememberMe valeur de la checkbox 'Rester connecté ?'
             * @param [loginOK] callback en cas de réussite de l'authentification
             * @param [loginFail] callback en cas d'échec de l'authentification
             */
            login: function (username, password, rememberMe, loginOK, loginFail) {
                var data = "j_username=" + username + "&j_password=" + password + "&_spring_security_remember_me=" +
                    rememberMe + "&submit=Login";
                $http.post('j2utils/authentication', data, {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    ignoreAuthModule: 'ignoreAuthModule'
                }).success(function (data, status, headers, config) {
                    $rootScope.authenticationError = false;
                    authService.loginConfirmed();
                    if (loginOK) {
                        loginOK(data, status, headers, config);
                    }
                }).error(function (data, status, headers, config) {
                    $rootScope.authenticationError = true;
                    if (loginFail) {
                        loginFail(data, status, headers, config);
                    }
                });
            },
            /**
             * Tentative de déconnexion
             * @param [lougoutOK] callback de déconnexion
             */
            logout: function (lougoutOK) {
                $rootScope.authenticationError = false;
                $http.get('j2utils/logout').success(function (data, status, headers, config) {
                    authService.loginCancelled();
                    if (lougoutOK) {
                        lougoutOK(data, status, headers, config);
                    }
                });
            }
        };
    }])
    .run(['$rootScope', '$location', 'AuthService', 'Utilisateur',
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
