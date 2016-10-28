app.controller('RegisterCtrl', function($scope, $window, AuthService, ElementService){
    var statusElement = document.getElementById('status');
    var csrfToken = document.getElementsByName('_csrf')[0].content;

    $scope.user = new User();


    $scope.register = function(user) {
        ElementService.hide(statusElement);
        AuthService.register(user, csrfToken, statusElement);
    };

    $window.onload = function() {
        loadNavElements();
        setActive(navElements.login);
    };
});