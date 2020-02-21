/////////////////////////////////////////////////////////////////////////////
////////////////////////////   LEFT MENU   /////////////////////////////////
///////////////////////////////////////////////////////////////////////////

$(function () {
    var urlLocation = location.href.replace(/(?:^(http:\/\/)?[\w.:]+\/)(\w+\/\w+)(?:\S*)/, "$2");
    if (urlLocation != null) {
        var subs = urlLocation.toString().split("/", 1);
        subs = "/" + subs + "/";
        var count = 0;
        //...
        $("#leftmenu_Accordion").accordion({ header: ".accordion_header", active: count });
        $("#leftmenu_information").accordion({ header: ".accordion_header", active: 0 });
        getPageTitle(urlLocation);
    }
    //leftmenu高度
    autoLeftMenuHeight();
});
function pageHeight() {
    if ($.browser.msie) {
        return document.compatMode == "CSS1Compat" ? document.documentElement.clientHeight : document.body.clientHeight;
    }
    else {
        return self.innerHeight;
    }
}
function autoLeftMenuHeight() {
    //leftmenu撑满页面高度

    //页面内容高度总和
    var mainheight = pageHeight() - 10;
    var bannerheight = $(".master_banner").height();
    //实际内容填充高度
    var contentheight = mainheight - bannerheight;

    //设置
    $(".maintable_leftmenu").height(contentheight);
}
function getPageTitle(herfStr) {
    var _title = $("title").html();
    if ($.trim(_title) != "" && $.trim(_title) != "首页") {
        //转换首页样式
        $("#aindex").removeClass("a_index").addClass("a_index_hide");
        //头选项卡 样式改变
        $(".span_currenttitle [href='/" + herfStr + "']").attr("class", "subacurrent");
        //获取路径
        var homeStr = $(".span_currenttitle [home='true']").attr("href");
        //左侧同路径菜单父节点 点击
        $("#" + $("li [href='" + homeStr + "']").attr("parentid")).click();
    }
}