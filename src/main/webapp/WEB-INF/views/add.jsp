<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>
    <jsp:attribute name="head">
        <script src="<c:url value="/static/js/controllers/addController.js"/>"></script>
        <title>Add a song</title>
    </jsp:attribute>
    <jsp:body>
        <h3>Improve identification process.<br>
            Add a music composition to the database.<br>
        </h3>
        <p class="lead center-block" style="margin-top:2em;">
            <a href="#" class="btn btn-lg btn-info">Add</a>
        </p>
        <h6>
            Supported formats: <b>.MP3</b>, <b>.WAV</b><br>
            Maximum file size: <b>5</b> MB
        </h6>
    </jsp:body>
</t:template>