$(document).ready(function(){
    $.get("/goods/" + window.location.href.split("?")[1].split("=")[1],function(data,status){
        $(document).attr("title", data.goodsName);
        var E = window.wangEditor;
        var editor = new E('#editor');
        // 或者 var editor = new E( document.getElementById('editor') )
        editor.create();
//    var html = "<p>"+
//        "    &nbsp;&nbsp;&nbsp;&nbsp; <a href=\"https://sale.jd.com/act/8aUplu2Q7IcfJog.html\" target=\"_blank\"><img alt=\"\" class=\"\" src=\"//img30.360buyimg.com/jgsq-productsoa/jfs/t7909/162/4066399226/39087/ca9a4a6f/5a02e839N036f781f.jpg\"/></a><br/>"+
//        "</p>"+
//        "<p>"+
//        "    <img alt=\"\" class=\"\" src=\"//img30.360buyimg.com/jgsq-productsoa/jfs/t4006/105/1818668843/100997/31be644e/58980fafNb8d2392f.jpg\"/><br/><img alt=\"\" class=\"\" src=\"//img30.360buyimg.com/jgsq-productsoa/jfs/t3214/333/5976189747/90515/f33414e6/58980fcbN000d2cdf.jpg\"/><br/><img alt=\"\" class=\"\" src=\"//img30.360buyimg.com/jgsq-productsoa/jfs/t4096/180/1752609882/75585/6e6b0853/58980ffaN4f65ff4c.jpg\"/><br/><img alt=\"\" class=\"\" src=\"//img30.360buyimg.com/jgsq-productsoa/jfs/t3184/261/5986290400/133984/20ed9dca/58981023Neff022b6.jpg\"/>"+
//        "</p>"+
//        "<p>"+
//        "    <br/>"+
//        "</p>"+
//        "<p>"+
//        "    <!-- #J-detail-content -->"+
//        "</p>"+
//        "<p>"+
//        "    <img alt=\"\" class=\"\" src=\"//img30.360buyimg.com/jgsq-productsoa/jfs/t2827/86/888220498/350962/76612413/57299bcaNe828b53d.jpg\"/><br/>"+
//        "</p>"+
//        "<ul class=\"detail-content-tab list-paddingleft-2\"></ul>";
        editor.txt.html(data.goodsIntro);
    });
});