var fillUserInfo = function (userInfo) {
    $('#user-id').html(userInfo.name);
    $('#user-sum').html('总和：' + userInfo.sum);
    $('#user-mean').html('平均：' + userInfo.mean);
    $('#user-stdDev').html('方差：' + userInfo.stdDev);
    $('#user-num').html('次数：' + userInfo.history.length);

    var $tbody = $('#table-user-info').find('tbody');
    $.each(userInfo.history, function(i, history) {
        $tbody.append('<tr><td>' + history + '</td></tr>');
    });
};

$(document).ready( function () {
    var userId = $.url().param('id');

    $.ajax({
        url : "api/player/search/" + userId,
    data: {},
    success : function(resp) {
            console.log('Load data success. ' + resp);
            var userInfo = JSON.parse(resp);
            fillUserInfo(userInfo);
        },
        error : function(resp) {
            console.log('Load data failed. ' + resp);
            $('user-id').html('Load data failed. ' + resp);
        }
    });
} );
