///////////////////////////////////////       javascript common        ///////////////////////////////////////////////////
//////////////////////////////////////       Eric Chen 2011/11/9       /////////////////////////////////////////////////
$(function () {
    $("#divDeleteConfirm").dialog({
        autoOpen: false,
        width: 300,
        modal: true,
        maxHeight: 260
    });
});
//function inputstyle() {
//    $("input:text,input:password").addClass("txt");
//    //鼠标点击text
//    $("input:text,input:password").click(function () {
//        $(this).css("background-color", "#CCE3F6");
//    });
//    $("input:text,input:password").blur(function () {
//        $(this).css("background-color", "#fff");
//    });
//}
function appendLoading(jqObj) {
    var strdiv = "<div class='divloading'></div>";
    jqObj.empty().html(strdiv);
}
function cancelDialog(dialogDIVID) {
    $("#" + dialogDIVID).dialog("close");
}
function execAjax(controllerName, actionName, parameter, reCall) {
    $.ajax({
        type: "POST",
        url: '/' + controllerName + '/' + actionName,
        data: parameter,
        async: false,
        cache: false,
        beforeSend: function () {
            //$("#loadingBox").empty().html("加载中...").dialog("open");
        },
        success: function (data) {
            //判断是否返回报错页面
            var errmsg = $("#UntityErrorMsg", data).text();
            if (errmsg != "") {
                ShowMessage(errmsg);
                //$("#loadingBox").empty().dialog("close");
                return;
            }

            //判断是否返回ResultInfo
            if (data.Successed != undefined) {
                if (!data.Successed) {
                    ShowMessage(data.FailReasonDesc);
                    // $("#loadingBox").empty().dialog("close");
                    return;
                }
            }

            reCall(data);
            //$("#loadingBox").empty().dialog("close");
        }
    });
}
var out_div = true; //标识 鼠标在DIV内还是外
///下拉选择
function dropDiv(ctrlDropID, ctrlTxtClass, _width) {
    
    var divDropContentID= ctrlDropID; //下拉容器
    var divTextClass = ctrlTxtClass;
    //弹出下拉DIV
    $("." + divTextClass).click(function () {
        var aimHeight = $("." + divTextClass)[0].offsetHeight;
        var aimWidth = $("." + divTextClass)[0].offsetWidth;
        var aimTop = 0; //txt1.offsetTop;
        var aimLeft = 0; //txt1.offsetLeft;
        var txt1 = $("." + divTextClass)[0];
        //alert(txt1.offsetParent);
        while (txt1) {
            aimTop += txt1.offsetTop;
            aimLeft += txt1.offsetLeft;
            txt1 = txt1.offsetParent;
            //alert(aimTop + " - " + aimLeft);
        }
        $("#" + divDropContentID).css("top", aimTop + aimHeight);
        $("#" + divDropContentID).css("left", aimLeft);
        $("#" + divDropContentID).css("width", _width);
        $("#" + divDropContentID).addClass("divDropDownShow");
    });


    $("#" + divDropContentID)[0].onmouseover = function () {
        out_div = false;
    }
    $("#" + divDropContentID)[0].onmouseout = function () {
        out_div = true;
    }
    //关闭DIV
    $(document).click(function (event) {
        event = window.event || event;
        var obj = event.srcElement ? event.srcElement : event.target;
        if (out_div && obj != $("." + divTextClass)[0]) {
            $("#" + divDropContentID).removeClass("divDropDownShow");
        }
    });
}

