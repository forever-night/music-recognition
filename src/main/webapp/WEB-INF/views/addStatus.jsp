<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>
    <jsp:attribute name="head">
        <title>Status</title>
    </jsp:attribute>
    <jsp:body>
        <div>
            <p id="status">
                <c:choose>
                    <c:when test="${message != null && message.length() > 0}">
                        <c:out value="${message}"/>
                    </c:when>
                    <c:otherwise>
                        Error <c:out value="${param.status}"/>
                    </c:otherwise>
                </c:choose>
            </p>
            <p class="center-block" style="margin-top: 2em;"><a href="<c:url value="/add"/>">
                <span class="glyphicon glyphicon-backward"></span> Go back!
                <span class="glyphicon glyphicon-backward"></span>
            </a></p>
        </div>
    </jsp:body>
</t:template>