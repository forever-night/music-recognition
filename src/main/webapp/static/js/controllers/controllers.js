app = angular.module('app', []);


navElements = {
    identify : null,
    howitworks : null,
    add : null
};

errorMessage = {
    unsupportedType: 'Unsupported audio type',
    internal: 'Internal server error',
    io: 'Error in reading file'
};

url = {
    uploadIdentify: '/musrec/upload?identify',
    result: '/musrec/result'
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


app.service('ElementService', function() {
   this.setStatus = function(statusElement, isSuccessful, text) {
       var cssClass;

       if (isSuccessful)
           cssClass = 'alert alert-info';
       else
           cssClass = 'alert alert-danger';

       statusElement.style.visibility = 'visible';
       statusElement.setAttribute('class', cssClass);
       statusElement.innerText = text;
   };

   this.hide = function(element) {
       element.style.visibility = 'hidden';
   };

   this.show = function(element) {
       element.style.visibility = 'visible';
   }
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


function loadNavElements() {
    navElements.identify = document.getElementById('navIdentify');
    navElements.howitworks = document.getElementById('navHow');
    navElements.add = document.getElementById('navAdd');
}


function setActive(element) {
    console.log(navElements);
    console.log(element);

    for (var propt in navElements) {
        if (navElements.hasOwnProperty(propt))
            if (element == navElements[propt])
                navElements[propt].setAttribute('class', 'active');
            else
                navElements[propt].removeAttribute('class');
    }
}
