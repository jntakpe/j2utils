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

j2utilsApp.controller('SessionsController', ['$scope', 'resolvedSessions', 'Sessions', function ($scope, resolvedSessions, Sessions) {
    "use strict";
    $scope.success = null;
    $scope.error = null;
    $scope.sessions = resolvedSessions;
    $scope.invalidate = function (series) {
        Sessions.delete({series: encodeURIComponent(series)},
            function () {
                $scope.success = true;
                $scope.error = null;
                $scope.sessions = Sessions.get();
            },
            function () {
                $scope.success = null;
                $scope.error = true;
            });
    };
}]);

j2utilsApp.controller('BadmintonController', ['$scope', 'resolvedReservations', 'Reservations',
    function ($scope, resolvedReservations, reservations) {
        "use strict";
        $scope.reservations = resolvedReservations;
    }]);