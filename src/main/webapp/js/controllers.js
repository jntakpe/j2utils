"use strict";

j2utilsApp.controller('PortailController', ['$scope', function ($scope) {
}]);

j2utilsApp.controller('LoginController', ['$scope', '$location', 'AuthService',
    function ($scope, $location, AuthService) {
        $scope.rememberMe = true;
        $scope.login = function () {
            AuthService.login({
                username: $scope.username,
                password: $scope.password,
                rememberMe: $scope.rememberMe,
                success: function () {
                    $location.path('/portail');
                }
            });
        }
    }]);