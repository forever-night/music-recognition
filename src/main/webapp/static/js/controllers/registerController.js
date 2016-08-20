app.controller('RegisterCtrl', function($scope, $window, AuthService){
    var statusElement = document.getElementById('status');
    var csrfToken = document.getElementsByName('_csrf')[0].content;

    $scope.user = new User();


    $scope.register = function(user) {
        AuthService.register(user, csrfToken, statusElement);
    };

    $window.onload = function() {
        loadNavElements();
        setActive(navElements.login);
    };
});


app.service('ValidatorService', function(){
   this.validatePassword = function(password, confirmPassword) {
       return password != null && confirmPassword != null && password == confirmPassword;
   } ;
});

app.service('AuthService', function($http, $window, ValidatorService, StatusService){
    this.postUser = function(user, csrfToken) {
        var config = {
            headers: {
                'X-CSRF-TOKEN' : csrfToken
            }
        };

        return $http.post(restUrl.register, JSON.stringify(user), config);
    };

    this.register = function(user, csrfToken, statusElement) {
        if (user.password == null || user.password.length == 0 ||
            user.confirm == null || user.confirm.length == 0) {
            StatusService.setStatus(statusElement, false, message.fieldEmpty);
            return;
        }

        if (ValidatorService.validatePassword(user.password, user.confirm)) {
            this.postUser(user, csrfToken).then(
                function success() {
                    $window.location.href = url.login + "?register";
                },
                function error(response) {
                    StatusService.setStatus(statusElement, false, 'error ' + response.status);
                }
            );
        } else
            StatusService.setStatus(statusElement, false, message.noPasswordMatch);
    };
});