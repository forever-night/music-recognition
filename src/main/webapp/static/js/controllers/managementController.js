app.controller('ManagementCtrl', function($scope, UserService) {
    $scope.userRole = userRole;
    $scope.displayUsers = [];
    $scope.pageCount = 0;
    $scope.pages = [];

    // starts from 1
    // 0 - nothing to display
    $scope.currentPage = 0;

    var allUsers = [];
    var usersPerPage = 3;


    //    TODO: set enable/disable
    //    TODO: set role

    $scope.getAll = function() {
        var request = UserService.getAll();

        request.then(
            function success(response) {
                $scope.displayUsers = [];
                allUsers = response;

                if (allUsers.length > 0) {
                    $scope.pageCount = Math.ceil(allUsers.length / usersPerPage);
                    $scope.createPageArray($scope.pageCount);
                    $scope.page(1);
                } else {
                    $scope.page(0);
                }
            },
            function error(response) {
                // TODO: report error
                console.log('error');
            }
        );
    };

    $scope.page = function(pageNumber) {
    //    TODO: save changes

        if (pageNumber == 0) {
            $scope.pages = [];
            $scope.pageCount = 0;
            $scope.currentPage = 0;
        } else if (pageNumber > 0 && pageNumber != $scope.currentPage) {
            $scope.displayUsers = [];

            var start = usersPerPage * (pageNumber - 1);
            var end = usersPerPage * pageNumber;

            for (var i = start; i < end; i++) {
                if (allUsers.length > i)
                    $scope.displayUsers.push(allUsers[i]);
            }

            $scope.currentPage = pageNumber;
        }
    };

    $scope.prevPage = function() {
        if ($scope.currentPage > 1)
            $scope.page($scope.currentPage - 1);
    };

    $scope.nextPage = function() {
        if ($scope.currentPage < $scope.pageCount)
            $scope.page($scope.currentPage + 1);
    };

    $scope.createPageArray = function(pageCount) {
        var pageArray = [];

        for (var i = 1; i <= pageCount; i++)
            pageArray.push(i);

        $scope.pages = pageArray;
    };


    $scope.getAll();
});