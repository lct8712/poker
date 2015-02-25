var fillSeasonDurations = function (seasonList) {
    var $seasonTitle = $('#seaon-title');
    $.each(seasonList, function (i) {
        fillSeasonDuration(i, $seasonTitle);
    });
};

var fillSeasonDuration = function (i, $seasonTitle) {
    var $season = $('<a>').html('Season ' + i).
        addClass('btn btn-default btn-big').
        attr('href', 'user-list.html?season=' + i).
        attr('role', 'button');
    $seasonTitle.after($season);
};

$(document).ready( function () {
    $.ajax({
        url : "api/season/list",
        success : function(resp) {
            console.log('Load season success.');
            fillSeasonDurations(resp);
        },
        error : function(resp) {
            alert('Load season failed: ' + resp);
        }
    });
} );
