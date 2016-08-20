app.controller('LoginCtrl', function ($scope, $window, StatusService) {
    var statusElement = document.getElementById('status');


    $scope.loginError = function () {
        StatusService.setStatus(statusElement, false, message.loginError);
    };

    $scope.logoutSuccess = function() {
        StatusService.setStatus(statusElement, true, message.logoutSuccess);
    };

    $scope.registerSuccess = function() {
        StatusService.setStatus(statusElement, true, message.registerSuccess);
    };


    $window.onload = function () {
        loadNavElements();
        setActive(navElements.login);
    };
});
