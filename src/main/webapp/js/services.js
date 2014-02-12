"use strict";

j2utilsApp.factory('Account', ['$resource',
    function ($resource) {
        return $resource('j2utils/rest/account');
    }]);

j2utilsApp.factory('AuthenticationSharedService', ['$rootScope', '$http', 'authService',
        function ($rootScope, $http, authService) {
            return {
                authenticate: function () {
                    $http.get('j2utils/rest/authenticate')
                        .success(function (data, status, headers, config) {
                            $rootScope.login = data;
                            if (data == '') {
                                $rootScope.$broadcast('event:auth-loginRequired');
                            } else {
                                $rootScope.$broadcast('event:auth-authConfirmed');
                            }
                        })
                },
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
                logout: function () {
                    $rootScope.authenticationError = false;
                    $http.get('j2utils/logout')
                        .success(function (data, status, headers, config) {
                            $rootScope.login = null;
                            authService.loginCancelled();
                        });
                }
            };
        }])
    .run(['$rootScope', '$location', 'AuthenticationSharedService', 'Account',
        function ($rootScope, $location, AuthenticationSharedService, Account) {
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
                AuthenticationSharedService.authenticate({}, function () {
                    $rootScope.authenticated = true;
                });
            });

            // Call when the 401 response is returned by the client
            $rootScope.$on('event:auth-loginRequired', function (rejection) {
                $rootScope.authenticated = false;
                if ($location.path() !== '/' && $location.path() !== "") {
                    $location.path('/').replace();
                }
            });

            // Call when the user is authenticated
            $rootScope.$on('event:auth-authConfirmed', function () {
                $rootScope.authenticated = true;
                $rootScope.account = Account.get();

                // If the login page has been requested and the user is already logged in
                // the user is redirected to the home page
                if ($location.path() === '/') {
                    $location.path('/home').replace();
                }
            });

            // Call when the user logs in
            $rootScope.$on('event:auth-loginConfirmed', function () {
                $rootScope.authenticated = true;
                $rootScope.account = Account.get();
                $location.path('').replace();
            });

            // Call when the user logs out
            $rootScope.$on('event:auth-loginCancelled', function () {
                $rootScope.authenticated = false;
                $location.path('');
            });
        }]);
