<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag description="Main template" pageEncoding="UTF-8" %>
<%@ attribute name="head" fragment="true" %>
<!DOCTYPE html>
<html lang="en" ng-app="app">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <%--<link rel="icon" href="../../favicon.ico">--%>

    <link type="text/css" href="<c:url value="/static/css/bootstrap.min.css"/>" rel="stylesheet">
    <link type="text/css" href="<c:url value="/static/css/cover.css"/>" rel="stylesheet">
    <link type="text/css" href="<c:url value="/static/css/cover-custom.css"/>" rel="stylesheet">

    <script src="<c:url value="/static/js/lib/jquery-2.2.3.js"/>"></script>
    <script src="<c:url value="/static/js/lib/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/static/js/lib/angular.min.js"/>"></script>

    <script src="<c:url value="/static/js/controllers/controllers.js"/>"></script>

    <jsp:invoke fragment="head"/>
</head>
<body ng-cloak ng-controller="MainCtrl">
<div class="site-wrapper">
    <div class="site-wrapper-inner">
        <div class="cover-container">
            <div class="masthead clearfix">
                <div class="inner">
                    <h3 class="masthead-brand"><a href="<c:url value="/"/>">Music Recognition</a></h3>
                    <nav>
                        <ul class="nav masthead-nav">
                            <li id="navIdentify">
                                <a href="<c:url value="/identify"/>">Identify</a>
                            </li>
                            <li id="navHow"><a href="<c:url value="/howitworks"/>">How it works</a></li>
                            <li id="navAdd"><a href="<c:url value="/add"/>">Add a song</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
            <div class="inner cover">
                <jsp:doBody/>
            </div>
            <div class="mastfoot">
                <div class="inner">
                    <p>Source code @ <a class="text-info" href="https://github.com/forever-night/music-recognition">GitHub
                    </a></p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
