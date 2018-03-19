$(document).ready(function () {
    $.get("/goods/list", function (data, status) {
        for (var i = 0; i < data.length; i++) {
            $("#list").append(`<li><a href="/editor.html?goodsId=${data[i].goodsId}">${data[i].goodsId}</a></li>`)
        }
    });
});