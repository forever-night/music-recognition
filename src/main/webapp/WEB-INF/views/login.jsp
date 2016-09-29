<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<t:template>
<jsp:attribute name="head">
    <script src="<c:url value="/static/js/controllers/loginController.js"/>"></script>
    <title>Log in</title>
</jsp:attribute>
<jsp:body>
<div ng-controller="LoginCtrl">
    <form class="center-block form-horizontal" style="margin-top:4em;padding-bottom:2em;" method="post"
          action="<c:url value="/login"/>">
        <c:if test="${param.error != null}">{{loginError()}}</c:if>
        <c:if test="${param.logout != null}">{{logoutSuccess()}}</c:if>
        <c:if test="${param.register != null}">{{registerSuccess()}}</c:if>
        <sec:csrfInput/>
        <div class="form-group">
            <label for="username" class="col-sm-offset-2 col-sm-2 control-label">Username</label>
            <div class="col-sm-6">
                <input class="form-control" id="username" name="username" type="text" required>
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-offset-2 col-sm-2 control-label">Password</label>
            <div class="col-sm-6">
                <input class="form-control" id="password" name="password" type="password" required>
            </div>
        </div>
        <div class="col-sm-12" style="margin-top:2em;">
            <div id="status" class="col-sm-8 col-sm-offset-2" role="alert" style="display: none;"></div>
        </div>
        <div class="form-group" style="margin-top:3em;">
            <button id="btnLogin" class="btn btn-lg btn-info col-sm-2 col-sm-offset-5" type="submit">
                Log in
            </button>
        </div>
        <div class="form-group">
            <u><a href="<c:url value="/register"/>" class="text-primary">I don't have an account</a></u>
        </div>
    </form>
</div>
</jsp:body>
</t:template>