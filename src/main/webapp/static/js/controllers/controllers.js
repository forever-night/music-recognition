app = angular.module('app', []);


navElements = {
    identify : null,
    howitworks : null,
    add : null,
    login: null,
    user: null
};

message = {
    unsupportedType: 'Unsupported audio type',
    internal: 'Internal server error',
    io: 'Error in reading file',
    noContent: 'File not selected',
    fieldEmpty: 'One of the fields is empty',
    noAccess: 'Access denied',
    noPasswordMatch: 'Passwords don\'t match',
    emailNotValid: 'e-mail is not valid!',
    loginError: 'Incorrect login or password',
    logoutSuccess: 'Logout successful!',
    registerSuccess: 'Registration successful!'
};

url = {
    login: context + '/login',
    logout: context + '/logout',
    register: context + '/register',
    identify: context + '/identify',
    howitworks: context + '/howitworks',
    add: context + '/add',
    addStatus: context + '/add?status',
    result: context + '/result'
};

restUrl = {
    uploadIdentify: context + '/rest/upload?identify',
    uploadAdd: context + '/rest/upload?add',
    register: context + '/rest/register',
    user: context + '/rest/user'
};


userRole = {
    admin: 'ADMIN',
    user: 'USER'
};


app.controller('MainCtrl', function ($scope) {
});


app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);


app.service('StatusService', function(ElementService) {
    this.setStatus = function(statusElement, isSuccessful, text) {
        var cssClass;

        if (isSuccessful)
            cssClass = 'alert alert-success col-sm-8 col-sm-offset-2 cover-override';
        else
            cssClass = 'alert alert-danger col-sm-8 col-sm-offset-2 cover-override';

        ElementService.display(statusElement, 'block');
        statusElement.setAttribute('class', cssClass);
        statusElement.innerText = text;
    };
});


app.service('ElementService', function() {
   this.hide = function(element) {
       element.style.display = 'none';
   };

   this.display = function(element, value) {
       element.style.display = value;
   };
});


app.service('LoaderService', function() {
    this.loader = function(loaderElement, enable) {
        var cssClass = 'cssload-container center-block';

        if (enable)
            loaderElement.setAttribute('class', cssClass);
        else
            loaderElement.setAttribute('class', '');
    }
});


app.service('MultipartService', function($http) {
    this.postMultipart = function(url, file, csrfToken) {
        var config = {
            transformRequest: angular.identity,
            headers: {
                'Content-Type': undefined,
                'X-CSRF-TOKEN' : csrfToken
            }
        };

        var fd = new FormData();
        fd.append('file', file);

        return $http.post(url, fd, config);
    };

    this.postMixedMultipart = function(url, formData, csrfToken) {
        var config = {
            headers: {
                'Content-Type': undefined,
                'X-CSRF-TOKEN' : csrfToken
            }
        };

        return $http.post(url, formData, config);
    };
});


app.service('UserValidatorService', function(){
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

app.service('AuthService', function($http, $window, UserValidatorService, StatusService){
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


app.service('UserService', function($http) {
    this.getAll = function() {
        return $http.get(restUrl.user).then(
            function success(response) {
                var result = [];
                var data = response.data;

                data.forEach(function(item, i, data) {
                    var user = new User(
                        item.username,
                        item.email,
                        (new Date(item.createdAt)).toLocaleDateString(),
                        item.enabled,
                        item.role
                    );

                    result.push(user);
                });

                return result;
            }
        );
    } ;
});


function loadNavElements() {
    navElements.identify = document.getElementById('navIdentify');
    navElements.howitworks = document.getElementById('navHow');
    navElements.add = document.getElementById('navAdd');
    navElements.login = document.getElementById('navLogin');
    navElements.user = document.getElementById('navUser');
}


function setActive(element) {
    for (var propt in navElements) {
        if (navElements.hasOwnProperty(propt))
            if (element == navElements[propt])
                navElements[propt].setAttribute('class', 'active');
            else if (navElements[propt] != null)
                navElements[propt].removeAttribute('class');
    }
}