var fillGameList = function (gameList) {
    var $container = $('#container');
    $.each(gameList, function(i, gameInfo) {
        fillGameInfo(gameInfo, $container);
    });
};

var fillGameInfo = function (gameInfo, $container) {
    var date = new Date(gameInfo.date);
    var $date = $('<legend>').html(getFormattedDate(date));
    $container.append($date);

    var $table = $('<table>').addClass('display');
    $table.DataTable({
        data: gameInfo.players,
        "aaSorting": [[ 1, "desc" ]],
        "bAutoWidth": false,
        "columns": [
            { data: 'fst' },
            { data: 'snd' }
        ],
        "paging" : false,
        "columnDefs": [
            {   "width": "50%",
                "targets": 0
            },
            {   "width": "50%",
                "targets": 1
            },
            {
                "type": "numeric",
                "targets": [1]
            }
        ]
    } );
    $container.append($table);
};

$(document).ready( function () {
    $.ajax({
        url : "api/game/all",
    data: {},
    success : function(resp) {
            console.log('Load data success. ');
            fillGameList(resp);
        },
        error : function(resp) {
            console.log('Load data failed. ' + resp);
        }
    });
} );
