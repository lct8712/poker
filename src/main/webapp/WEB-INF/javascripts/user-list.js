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
        "paging" : false,
        "sDom": '<"top">rt<"bottom"flp><"clear">',
        "rowCallback": function(row, data) {
            var url = 'user-info.html?id=' + data.name;
            $('td:eq(0)', row).html('<a href="' + url + '">' + data.name + '</a>');
        },
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
