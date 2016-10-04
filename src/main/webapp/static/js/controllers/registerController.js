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


app.service('ValidatorService', function(){
   this.validatePassword = function(password, confirmPassword) {
       return password != null && confirmPassword != null && password == confirmPassword;
   } ;

   this.validateEmail = function(email) {
       if (email == null || email.length == 0)
           return false;

       var regex = /^[\w\d]{3,}@\w{3,}\.\w{2,}/;
       return regex.test(email);
   }
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
            user.confirm == null || user.confirm.length == 0 ||
            user.email == null || user.email.length == 0) {
            StatusService.setStatus(statusElement, false, message.fieldEmpty);
            return;
        }

        var passwordValid = ValidatorService.validatePassword(user.password, user.confirm);
        var emailValid = ValidatorService.validateEmail(user.email);

        if (passwordValid && emailValid) {
            this.postUser(user, csrfToken).then(
                function success() {
                    $window.location.href = url.login + "?register";
                },
                function error(response) {
                    if (response.status == 422)
                        StatusService.setStatus(statusElement, false, 'Username is taken');
                    else
                        StatusService.setStatus(statusElement, false, 'error ' + response.status);
                }
            );
        } else if (!passwordValid) {
            StatusService.setStatus(statusElement, false, message.noPasswordMatch);
        } else if (!emailValid) {
            StatusService.setStatus(statusElement, false, message.emailNotValid);
        }
    };
});