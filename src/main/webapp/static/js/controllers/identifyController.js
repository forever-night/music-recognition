app.controller('IdentifyCtrl', function($scope, $http, $window, ElementService, LoaderService) {
    var statusElement = document.getElementById('status');
    var loaderElement = document.getElementById('loader');
    var btnIdentifyElement = document.getElementById('btnIdentify');
    var fileElement = document.getElementById('file');
    var infoElement = document.getElementById('info');
    // var csrfToken = document.getElementsByName('_csrf')[0].content;


    $scope.uploadFile = function() {
        LoaderService.loader(loaderElement, false);
        ElementService.hide(statusElement);

        var file = $scope.file;
        var fd = new FormData();
        fd.append('file', file);

        ElementService.hide(btnIdentifyElement);
        ElementService.hide(fileElement);
        ElementService.hide(infoElement);
        LoaderService.loader(loaderElement, true);

        $scope.postFile(fd);
    };

    $scope.postFile = function(formData) {
        var config = {
            transformRequest: angular.identity,
            headers: {
                'Content-Type': undefined
                // 'X-CSRF-TOKEN' : csrfToken
            }
        };

        $http.post(url.uploadIdentify, formData, config).then(
            function success(response){
                sessionStorage.setItem('trackMatches', JSON.stringify(response.data));
                $window.location.href = url.result;
            },
            function error(response) {
                LoaderService.loader(loaderElement, false);
                ElementService.show(statusElement);
                ElementService.show(fileElement);
                ElementService.show(infoElement);
                ElementService.show(btnIdentifyElement);

                switch (response.status) {
                    case 415:
                        ElementService.setStatus(statusElement, false, errorMessage.unsupportedType);
                        break;
                    case 500:
                        ElementService.setStatus(statusElement, false, errorMessage.io);
                        break;
                    default:
                        ElementService.setStatus(statusElement, false, 'error ' + response.status);
                }
            }
        );
    };


    $window.onload = function() {
        loadNavElements();
        setActive(navElements.identify);
    }
});