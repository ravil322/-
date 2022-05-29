function getMetadata(){

    let formData = new FormData();
    formData.append("docx-file", $('#docx-file')[0].files[0]);
    $.ajax({
        url: '/metadata',
        method: 'post',
        data: formData,
        processData: false,
        contentType: false,
    }).fail(function (jqXHR) {
        console.log("Ошибка");
    }).done(function (metadata) {
        console.log(metadata);
        const obj = JSON.parse(metadata);
        $('#title').val(obj.title);
        $('#authors').val(obj.authors);
        $('#keyWords').val(obj.key_words);
        $('#publicationYear').val(obj.publication_year);
        $('.metadata').removeClass('hidden');
    });
}