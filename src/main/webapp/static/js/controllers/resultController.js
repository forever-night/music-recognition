app.controller('ResultCtrl', function($scope, $window){
    var trackMatchesJson = JSON.parse(sessionStorage.getItem('trackMatches'));

    $scope.matches = [];

    $scope.getPercentage = function(matchCount, fingerprintCount) {
        var percentage = matchCount * 100 / fingerprintCount;

        percentage *= 100;
        percentage = Math.round(percentage);
        percentage /= 100;

        return percentage;
    };


    $window.onload = function() {
        loadNavElements();
    };

    trackMatchesJson.forEach(function(trackMatch, i, trackMatchesJson){
        var match = new TrackMatch();
        match.title = trackMatch.track.title;
        match.albumTitle = trackMatch.track.albumTitle;
        match.artist = trackMatch.track.artist;
        match.year = trackMatch.track.year;
        match.matchCount = trackMatch.matchCount;
        match.fingerprintCount = trackMatch.fingerprintCount;
        match.percentage = $scope.getPercentage(match.matchCount, match.fingerprintCount);

        if (trackMatch.track.genre != null)
            match.genres = trackMatch.track.genre.split(',');
        else
            match.genres = null;

        $scope.matches.push(match);
    });
});
