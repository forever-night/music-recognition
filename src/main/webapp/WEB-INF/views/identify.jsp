<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<t:template>
<jsp:attribute name="head">
    <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/loader.css"/>"/>
    <script src="<c:url value="/static/js/controllers/identifyController.js"/>"></script>
    <title>Identify audio</title>
    <sec:csrfMetaTags/>
</jsp:attribute>
<jsp:body>
<div ng-controller="IdentifyCtrl">
    <h2 id="txtHead">Upload a file to identify</h2><br>
    <form id="form" class="center-block" style="margin-top:2em;" method="post" enctype="multipart/form-data">
        <input id="file" type="file" name="file" file-model="file" class="center-block" required/><br>
        <button ng-click="uploadFile()" class="btn btn-lg btn-info">
            Identify <span class="glyphicon glyphicon-cloud-upload"></span>
        </button>
        <h6>
            Supported formats: <b>.MP3</b>, <b>.WAV</b><br>
            Maximum file size: <b>15</b> MB
        </h6>
    </form>
    <div id="loader">
        <div class="cssload-shaft1"></div>
        <div class="cssload-shaft2"></div>
        <div class="cssload-shaft3"></div>
        <div class="cssload-shaft4"></div>
        <div class="cssload-shaft5"></div>
        <div class="cssload-shaft6"></div>
        <div class="cssload-shaft7"></div>
        <div class="cssload-shaft8"></div>
        <div class="cssload-shaft9"></div>
        <div class="cssload-shaft10"></div>
    </div>
    <div class="col-sm-12">
        <div id="status" role="alert" style="display: none;"></div>
    </div>
</div>
</jsp:body>
</t:template>