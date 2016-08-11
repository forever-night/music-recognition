<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>
    <jsp:attribute name="head">
        <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/inline-label.css"/>"/>

        <script src="<c:url value="/static/js/classes/trackMatch.js"/>"></script>
        <script src="<c:url value="/static/js/controllers/resultController.js"/>"></script>
        <title>Result</title>
    </jsp:attribute>
    <jsp:body>
        <div ng-controller="ResultCtrl">
            <ul class="list-group" ng-if="matches.length > 0">
                <li class="list-group-item cover-override" ng-repeat="match in matches"
                    ng-class="{'list-group-item-info':$first, 'cover-text-override':!$first}" style="text-shadow:none;">
                    <span class="badge">{{match.percentage}}% </span>
                    <p>
                        <span class="glyphicon glyphicon-music" ng-if="$first"></span>
                        <strong>{{match.title}}</strong> - {{match.artist}}
                    </p>
                    <p>{{match.albumTitle}}, {{match.year}}</p>
                    <div ng-if="match.genres != null">
                        <span class="label label-default" ng-repeat="genre in match.genres">{{genre}}</span>
                    </div>
                </li>
            </ul>
            <p ng-if="matches.length == 0">
                <span class="glyphicon glyphicon-ice-lolly-tasted"></span> No match found.
            </p>
            <p class="center-block" style="margin-top: 2em;"><a href="<c:url value="/identify"/>">
                <span class="glyphicon glyphicon-backward"></span> Go back!
                <span class="glyphicon glyphicon-backward"></span>
            </a></p>
        </div>
    </jsp:body>
</t:template>