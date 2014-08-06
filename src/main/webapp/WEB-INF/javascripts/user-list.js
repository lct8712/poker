$(document).ready( function () {
    console.log('ready');
    $('#table-user-list').DataTable( {
        "ajax": {
            "url": "api/player/all",
            "dataSrc": ""
        },
        "aaSorting": [[ 1, "desc" ]],
        "columns": [
            { 'data': 'name' },
            { 'data': 'sum' },
            { 'data': 'mean' },
            { 'data': 'stdDev' },
            { 'data': 'history' }
        ],
        "columnDefs": [
            {
                "type": "numeric",
                "targets": [1, 2, 3]
            },
            {
                "targets": 4,
                "render": function (data, type, full) {
                    return full.history.length;
            }
        } ]
    } );
} );
