j2utilsApp.factory('Utilisateur', ['$resource', function ($resource) {
    "use strict";
    return $resource('j2utils/rest/utilisateur');
}]);

j2utilsApp.factory('Sessions', ['$resource', function ($resource) {
    "use strict";
    return $resource('j2utils/rest/utilisateur/sessions/:series');
}]);

j2utilsApp.factory('Reservations', ['$resource', function ($resource) {
    "use strict";
    return $resource('j2utils/rest/reservations/:reservation');
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
}]);

