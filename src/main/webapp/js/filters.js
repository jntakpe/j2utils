j2utilsApp.filter('localDateFilter', ['$filter', function ($filter) {
    return function (localDate) {
        var date = new Date(localDate[0], localDate[1] - 1, localDate[2], 0, 0, 0, 0);
        return $filter('date')(date, 'dd/MM');
    };
}]);
