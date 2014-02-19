j2utilsApp.controller('PortailController', ['$scope', function ($scope) {
    "use strict";

}]);

j2utilsApp.controller('LoginController', ['$scope', '$location', 'AuthService', function ($scope, $location, AuthService) {
    "use strict";
    $scope.login = function () {
        AuthService.login($scope.username, $scope.password, $scope.rememberMe, function () {
            $location.path('/portail');
        });
    };
}]);

j2utilsApp.controller('LogoutController', ['AuthService', function (AuthService) {
    "use strict";
    AuthService.logout();
}]);