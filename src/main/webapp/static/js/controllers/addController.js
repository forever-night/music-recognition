app.controller('AddCtrl', function ($scope, $window, $http, StatusService, ElementService, LoaderService, MultipartService) {
    var statusElement = document.getElementById('status');
    var loaderElement = document.getElementById('loader');
    var formElement = document.getElementById('form');
    var headerTextElement = document.getElementById('txtHead');
    var csrfToken = document.getElementsByName('_csrf')[0].content;

    $scope.track = new Track();


    $scope.elementPositionFix = function(fixed) {
        var positionStyle = fixed ? 'fixed' : 'static';

        document.getElementsByClassName('masthead')[0].style.position = positionStyle;
        document.getElementsByClassName('mastfoot')[0].style.position = positionStyle;
    };


    $scope.createFormData = function() {
        var data = {
            file: $scope.file,
            track: $scope.track
        };

        var formData = new FormData();

        formData.append('track', new Blob([JSON.stringify(data.track)],
            { type: "application/json" }
        ));

        formData.append('file', data.file);

        return formData;
    };


    $scope.add = function () {
        $scope.elementPositionFix(true);
        ElementService.hide(statusElement);

        if ($scope.track.year == 0 || $scope.track.title.length == 0 ||
            $scope.track.albumTitle.length == 0 || $scope.track.artist.length == 0) {
            StatusService.setStatus(statusElement, false, message.fieldEmpty);
            return;
        }


        var formData = $scope.createFormData();

        MultipartService.postMixedMultipart(restUrl.uploadAdd, formData, csrfToken).then(
            function success(response) {
                $window.location.href = url.addStatus + '=' + response.status;
            },
            function error(response) {
                $window.location.href = url.addStatus + '=' + response.status;
            }
        );

        LoaderService.loader(loaderElement, true);
        ElementService.hide(headerTextElement);
        ElementService.hide(formElement);
    };


    $window.onload = function () {
        loadNavElements();
        setActive(navElements.add);
    };

    LoaderService.loader(loaderElement, false);
    ElementService.hide(statusElement);
});