<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ tag description="Main template" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ attribute name="head" fragment="true" %>
<!DOCTYPE html>
<html lang="en" ng-app="app">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link type="text/css" href="<c:url value="/static/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="/static/css/cover.css"/>" rel="stylesheet"/>
    <link type="text/css" href="<c:url value="/static/css/cover-custom.css"/>" rel="stylesheet"/>
    <script>context = "${pageContext.request.contextPath}"; </script>
    <script rel="script" src="<c:url value="/static/js/lib/jquery-2.2.3.js"/>"></script>
    <script rel="script" src="<c:url value="/static/js/lib/bootstrap.min.js"/>"></script>
    <script rel="script" src="<c:url value="/static/js/lib/angular.min.js"/>"></script>
    <script rel="script" src="<c:url value="/static/js/controllers/controllers.js"/>"></script>
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
                <sec:authorize access="isAnonymous()">
                    <li id="navLogin"><a href="<c:url value="/login"/>">Log in</a></li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <sec:authentication property="principal.username" var="principalUsername"/>
                    <li id="navIdentify"><a href="<c:url value="/identify"/>">Identify</a></li>
                    <li id="navUser">
                    <div class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button"
                                id="dropdownMenu"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="glyphicon glyphicon-user"></span> ${principalUsername}
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu cover-override" aria-labelledby="dropdownMenu">
                            <sec:authorize access="hasAuthority('ADMIN')">
                                <li><a href="<c:url value="/add"/>">Add a track</a></li>
                                <li role="separator" class="divider"></li>
                            </sec:authorize>
                            <li>
                                <form method="post" action="<c:url value="/logout"/>"
                                      class="dropdown-form">
                                    <sec:csrfInput/>
                                    <a href="javascript:;" onclick="parentNode.submit();"
                                       class="btn">Log out</a>
                                </form>
                            </li>
                        </ul>
                    </div>
                    </li>
                </sec:authorize>
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