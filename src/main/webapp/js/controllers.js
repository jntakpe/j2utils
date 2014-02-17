j2utilsApp.controller('PortailController', ['$scope', function ($scope) {
    "use strict";

}]);

j2utilsApp.controller('LoginController', ['$scope', '$location', 'AuthService', function ($scope, $location, AuthService) {
    "use strict";
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
    };
}]);

j2utilsApp.controller('LogoutController', ['AuthService', function (AuthService) {
    "use strict";
    AuthService.logout();
}]);