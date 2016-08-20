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
    register: context + '/rest/register'
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
            else
                navElements[propt].removeAttribute('class');
    }
}