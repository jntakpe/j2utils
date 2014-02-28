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
                $scope.sessions = Sessions.query();
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

        $scope.rowBg = function (reservation) {
            if (!reservation.creneau) {
                return 'danger';
            } else if (reservation.joueurs.length === 4) {
                return 'success';
            } else {
                return 'warning';
            }
        };

        $scope.reservAction = function (reservation) {
            var role = null;
            if ($scope.utilisateur.role) {
                role = $scope.utilisateur.role.nom;
            }
            if (reservation.creneau === null && role === 'ROLE_ADMIN') {
                return '0';
            } else if (reservation.creneau !== null && reservation.joueurs[$scope.utilisateur.login]) {
                return '1';
            } else if (reservation.creneau !== null) {
                return '2';
            } else {
                return '3';
            }

        };

        $scope.reserv = function (reservation) {
            console.log("reserv court");
        };

        $scope.enreg = function (reservation) {
            console.log("enreg");
        };

        $scope.annul = function (reservation) {
            console.log("annul");
        };
    }]);