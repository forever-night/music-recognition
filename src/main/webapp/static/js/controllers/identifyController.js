app.controller('IdentifyCtrl', function($scope, $window, StatusService, ElementService, LoaderService, MultipartService) {
    var statusElement = document.getElementById('status');
    var loaderElement = document.getElementById('loader');
    var headerTextElement = document.getElementById('txtHead');
    var formElement = document.getElementById('form');
    var csrfToken = document.getElementsByName('_csrf')[0].content;


    $scope.uploadFile = function() {
        if ($scope.file == null)
            StatusService.setStatus(statusElement, false, message.fieldEmpty);


        var file = $scope.file;

        MultipartService.postMultipart(url.uploadIdentify, file, csrfToken).then(
            function success(response) {
                sessionStorage.setItem('trackMatches', JSON.stringify(response.data));
                $window.location.href = url.result;
            },
            function error(response){
                LoaderService.loader(loaderElement, false);
                ElementService.display(headerTextElement, 'block');
                ElementService.display(formElement, 'block');
                ElementService.display(statusElement, 'block');

                switch (response.status) {
                    case 204:
                        StatusService.setStatus(statusElement, false, message.noContent);
                        break;
                    case 403:
                        StatusService.setStatus(statusElement, false, message.noAccess);
                        break;
                    case 415:
                        StatusService.setStatus(statusElement, false, message.unsupportedType);
                        break;
                    case 500:
                        StatusService.setStatus(statusElement, false, message.io);
                        break;
                    default:
                        StatusService.setStatus(statusElement, false, 'error ' + response.status);
                }
            }
        );

        ElementService.hide(headerTextElement);
        ElementService.hide(formElement);
        LoaderService.loader(loaderElement, true);
    };


    $window.onload = function() {
        loadNavElements();
        setActive(navElements.identify);
    };

    LoaderService.loader(loaderElement, false);
    ElementService.hide(statusElement);
});