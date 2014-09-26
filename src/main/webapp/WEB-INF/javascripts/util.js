var getFormattedDate = function(time) {
    var date = new Date(time);
    var mm = date.getMonth() + 1;
    if (mm < 10) {
        mm = '0' + mm;
    }
    var dd = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
    return date.getFullYear() + '-' + mm + '-' + dd;
};
