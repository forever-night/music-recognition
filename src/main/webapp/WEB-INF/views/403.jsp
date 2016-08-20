<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>
    <jsp:attribute name="head">
        <title>Error</title>
    </jsp:attribute>
    <jsp:body>
        <div>
            <h2><strong>Error 403</strong></h2><br>
            <p><span class="glyphicon glyphicon-ice-lolly-tasted"></span> Access denied</p>
        </div>
    </jsp:body>
</t:template>