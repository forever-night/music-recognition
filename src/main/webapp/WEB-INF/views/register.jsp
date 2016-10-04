<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<t:template>
<jsp:attribute name="head">
    <script src="<c:url value="/static/js/classes/user.js"/>"></script>
    <script src="<c:url value="/static/js/controllers/registerController.js"/>"></script>
    <title>Register</title>
    <sec:csrfMetaTags/>
</jsp:attribute>
<jsp:body>
<div ng-controller="RegisterCtrl">
    <h3>Create an account</h3>
    <form class="center-block form-horizontal" style="padding-top:2em;padding-bottom:2em;">
        <div class="form-group">
            <label for="inputUsername" class="col-sm-offset-1 col-sm-3 control-label">Username</label>
            <div class="col-sm-6">
                <input class="form-control" id="inputUsername" name="inputUsername" type="text"
                       ng-model="user.username" required>
            </div>
        </div>
        <div class="form-group">
            <label for="inputEmail" class="col-sm-offset-1 col-sm-3 control-label">e-mail</label>
            <div class="col-sm-6">
                <input class="form-control" id="inputEmail" name="inputEmail" type="text"
                       ng-model="user.email" required>
            </div>
        </div>
        <div class="form-group">
            <label for="inputPassword" class="col-sm-offset-1 col-sm-3 control-label">Password</label>
            <div class="col-sm-6">
                <input class="form-control" id="inputPassword" name="inputPassword" type="password"
                       ng-model="user.password" required>
            </div>
        </div>
        <div class="form-group">
            <label for="inputConfirm" class="col-sm-offset-1 col-sm-3 control-label">Confirm password</label>
            <div class="col-sm-6">
                <input class="form-control" id="inputConfirm" name="inputConfirm" type="password"
                       ng-model="user.confirm" required>
            </div>
        </div>
        <div class="col-sm-12">
            <div id="status" class="col-sm-8 col-sm-offset-2" role="alert" style="display: none;"></div>
        </div>
        <div class="form-group" style="margin-top:3em;">
            <button id="btnSubmit" class="btn btn-lg btn-info col-sm-2 col-sm-offset-5"
                    ng-click="register(user)">Submit</button>
        </div>
        <div class="form-group">
            <u><a href="<c:url value="/login"/>" class="text-primary">I already have an account</a></u>
        </div>
    </form>
</div>
</jsp:body>
</t:template>