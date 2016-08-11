<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>
    <jsp:attribute name="head">
        <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/loader.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/positionFix.css"/>"/>

        <script src="<c:url value="/static/js/classes/track.js"/>"></script>
        <script src="<c:url value="/static/js/controllers/addController.js"/>"></script>
        <title>Add a track</title>
    </jsp:attribute>
    <jsp:body>
        <div ng-controller="AddCtrl" style="padding-top:3em;">
            <div id="txtHead">
                <h3>Improve identification process.</h3>
                <h4 class="text-muted">Add a track to the database.</h4>
            </div>
            <form id="form" class="center-block form-horizontal" style="padding-top:2em;" method="post"
                  enctype="multipart/form-data">
                <div class="form-group">
                    <label for="inputTitle" class="col-sm-2 control-label">Title</label>
                    <div class="col-sm-8">
                        <input class="form-control" id="inputTitle" name="inputTitle" type="text"
                               ng-model="track.title" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputArtist" class="col-sm-2 control-label">Artist</label>
                    <div class="col-sm-8">
                        <input class="form-control" id="inputArtist" name="inputArtist" type="text"
                               ng-model="track.artist" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputAlbum" class="col-sm-2 control-label">Album title</label>
                    <div class="col-sm-8">
                        <input class="form-control" id="inputAlbum" name="inputAlbum" type="text"
                               ng-model="track.albumTitle" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputYear" class="col-sm-2 control-label">Year</label>
                    <div class="col-sm-2">
                        <input class="form-control" id="inputYear" name="inputYear" type="number"
                               ng-model="track.year" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputGenre" class="col-sm-2 control-label">Genre(s)</label>
                    <div class="col-sm-5">
                        <textarea class="form-control" id="inputGenre" name="inputGenre"
                                  rows="2" placeholder="comma-separated" ng-model="track.genre"></textarea>
                    </div>
                </div>
                <div class="form-group"><h4>
                    <input id="file" name="file" type="file" file-model="file" class="center-block text-info"
                           required></h4>
                </div>
                <div class="form-group">
                    <button id="btnAdd" ng-click="add()" class="btn btn-lg btn-info">
                        Add <span class="glyphicon glyphicon-cloud-upload"></span>
                    </button>
                </div>
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
                <div id="status" class="col-sm-8 col-sm-offset-2" role="alert" style="display: none;"></div>
            </div>
        </div>
    </jsp:body>
</t:template>