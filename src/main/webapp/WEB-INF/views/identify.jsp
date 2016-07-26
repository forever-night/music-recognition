<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>
    <jsp:attribute name="head">
        <script src="<c:url value="/static/js/controllers/identifyController.js"/>"></script>
        <title>Identify audio</title>
    </jsp:attribute>
    <jsp:body>
        <h2>Upload a file to identify</h2><br>
        <p class="lead center-block" style="margin-top:2em;">
            <a href="#" class="btn btn-lg btn-info">Identify</a>
        </p>
        <h6>
            Supported formats: <b>.MP3</b>, <b>.WAV</b><br>
            Maximum file size: <b>5</b> MB
        </h6>
    </jsp:body>
</t:template>