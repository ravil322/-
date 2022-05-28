function getMetadata(){
    $.ajax({
        url: '/metadata',
        method: 'post'
    }).fail(function (jqXHR) {
        console.log("Ошибка");
    }).done(function () {
        console.log();
    });
}