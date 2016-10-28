<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template>
<jsp:attribute name="head">
    <script src="<c:url value="/static/js/classes/user.js"/>"></script>
    <script src="<c:url value="/static/js/controllers/managementController.js"/>"></script>
    <link type="text/css" href="<c:url value="/static/css/position-fix.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="/static/css/table-custom.css"/>" rel="stylesheet"/>
    <title>User Management</title>
</jsp:attribute>
<jsp:body>
    <div ng-controller="ManagementCtrl">
        <div ng-show="displayUsers.length == 0">
            <h3>No result found.</h3>
        </div>
        <div class="col-md-12 col-sm-12" ng-if="displayUsers.length > 0">
            <button class="btn btn-info col-sm-2 col-sm-offset-10 col-md-2 col-md-offset-10">Save</button>
        </div>
        <div class="col-md-12 col-sm-12" style="margin-top:2em; margin-bottom:5em;" ng-if="displayUsers.length > 0">
            <table class="table table-bordered table-hover table-condensed">
                <tr class="info">
                    <th><p class="text-center">Username</p></th>
                    <th><p class="text-center">e-mail</p></th>
                    <th><p class="text-center">Created</p></th>
                    <th><p class="text-center">Enabled</p></th>
                    <th><p class="text-center">Role</p></th>
                </tr>
                <tr ng-repeat="user in displayUsers">
                    <td>{{user.username}}</td>
                    <td>{{user.email}}</td>
                    <td>{{user.createdAt}}</td>
                    <td>
                        <input type="checkbox" checked ng-if="user.enabled"/>
                        <input type="checkbox" ng-if="!user.enabled"/>
                    </td>
                    <td>
                        <div class="dropdown">
                            <button class="btn btn-sm btn-default dropdown-toggle" type="button" id="dropdownRole"
                                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                {{user.role}}
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu cover-override col-md-1 col-sm-1"
                                aria-labelledby="dropdownRole">
                                <li><a href="#">{{userRole.admin}}</a></li>
                                <li><a href="#">{{userRole.user}}</a></li>
                            </ul>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
        <nav ng-show="pageCount > 1">
            <ul class="pagination">
                <li ng-class="{'disabled': currentPage == 1}">
                    <a href="#" aria-label="Previous" ng-click="prevPage()">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <li ng-repeat="pageNumber in pages" ng-class="{'active': pageNumber == currentPage}"
                    ng-show="pageCount > 1">
                    <a href="#" ng-click="page(pageNumber)">{{pageNumber}}</a>
                </li>
                <li ng-class="{'disabled': currentPage == pageCount}">
                    <a href="#" aria-label="Next" ng-click="nextPage()">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</jsp:body>
</t:template>
