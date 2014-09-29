$(document).ready(function(){
    $('a#href-user-list').click(function () {
        var season = $.url().param('season');
        var url = (season === undefined || season.size === 0) ? "index.html" : "user-list.html?season=" + season;
        window.location.href = url;
    });

    $('a#href-game-list').click(function () {
        var season = $.url().param('season');
        var url = (season === undefined || season.size === 0) ? "index.html" : "game-list.html?season=" + season;
        window.location.href = url;
    });

    $('a#href-game-update').click(function () {
        var season = $.url().param('season');
        var url = (season === undefined || season.size === 0) ? "index.html" : "game-update.html?season=" + season;
        window.location.href = url;
    });
});
