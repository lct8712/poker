$('#submit-game-update').click( function () {
    $.ajax({
        url : "api/game/update",
        type: 'POST',
        data: {
            'date' : $('#input-date').val(),
            'content' : $('#input-content').val(),
            'comment' : ''
        },
        success : function(resp) {
            console.log('Post data success. ' + resp);
            if (resp.isSuccess) {
                $('#error-info').html('Success.');
            } else {
                $('#error-info').html(resp.errorInfo);
            }
        },
        error : function(resp) {
            console.log('Post data failed. ' + resp);
        }
    });
} );
