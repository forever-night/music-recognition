app.controller('ManagementCtrl', function($scope, UserService) {
    $scope.userRole = userRole;
    $scope.displayUsers = [];
    $scope.pageCount = 0;
    $scope.pages = [];
    $scope.isEditing = [];

    // starts from 1
    // 0 - nothing to display
    $scope.currentPage = 0;

    var allUsers = [];
    var usersPerPage = 20;
    var csrfToken = document.getElementsByName('_csrf')[0].content;


    $scope.getAll = function() {
        var request = UserService.getAll();

        request.then(
            function success(response) {
                $scope.currentPage = 0;
                $scope.displayUsers = [];
                allUsers = response;

                if (allUsers.length > 0) {
                    $scope.pageCount = Math.ceil(allUsers.length / usersPerPage);
                    $scope.createPageArray($scope.pageCount);
                    $scope.page(1);
                } else {
                    // TODO: display status 'no result'
                    $scope.page(0);
                }
            },
            function error(response) {
                // TODO: report error
                console.log('error');
            }
        );
    };

    $scope.putUser = function(user) {
        return UserService.putUser(user, restUrl.user, csrfToken);
    };

    $scope.page = function(pageNumber) {
        if (pageNumber == 0) {
            $scope.pages = [];
            $scope.pageCount = 0;
            $scope.currentPage = 0;
            $scope.isEditing = [];
        } else if (pageNumber > 0 && pageNumber != $scope.currentPage) {
            $scope.displayUsers = [];
            $scope.isEditing = [];

            var start = usersPerPage * (pageNumber - 1);
            var end = usersPerPage * pageNumber;

            for (var i = start; i < end; i++) {
                if (allUsers.length > i) {
                    $scope.displayUsers.push(copy(allUsers[i]));
                    $scope.isEditing.push(false);
                }
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

    $scope.setRole = function(user, role) {
        user.role = role;
    };

    $scope.edit = function(rowIndex) {
        $scope.isEditing[rowIndex] = true;
    };

    $scope.cancel = function(rowIndex) {
        var allUsersIndex = ($scope.pageCount - 1) * usersPerPage + rowIndex;
        $scope.displayUsers[rowIndex] = copy(allUsers[allUsersIndex]);

        $scope.isEditing[rowIndex] = false;
    };

    $scope.save = function(rowIndex) {
        var allUsersIndex = ($scope.pageCount - 1) * usersPerPage + rowIndex;
        var user = $scope.displayUsers[rowIndex];

        if (user != null) {
            $scope.putUser($scope.displayUsers[rowIndex]).then(
                function success() {
                    allUsers[allUsersIndex] = copy(user);
                    $scope.isEditing[rowIndex] = false;
                    $scope.page($scope.currentPage);
                },
                function error(response) {
                    alert('error ' + response.status);
                }
            );
        }
    };

    $scope.getAll();
});