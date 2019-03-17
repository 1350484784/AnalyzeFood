//获取食物类型
function getFoodType() {
    //json  food_type
    var foodTypeObj = [];
    if(window.sessionStorage.getItem("foodType")){
        foodTypeObj = JSON.parse(window.sessionStorage.getItem("foodType"));
    }else{
        $.ajax({
            url: "/" + projectName + "/manage/getFoodType",
            type: "post",
            success: function (data) {
                if(!data){
                    top.layer.msg("没有数据！");
                }else{
                    window.sessionStorage.setItem("foodType",JSON.stringify(data));
                    foodTypeObj = JSON.parse(window.sessionStorage.getItem("foodType"));
                }
            }
        });
    }
    return foodTypeObj
}

//获取滚动高度、屏幕高度、可滚动文本总高度
function getScrollInfo() {
    var scrollTop = 0;
    var clientHeight = 0;
    var scrollHeight = 0;
    if (document.documentElement && document.documentElement.scrollTop) {
        scrollTop = document.documentElement.scrollTop;
    } else if (document.body) {
        scrollTop = document.body.scrollTop;
    }
    if (document.body.clientHeight && document.documentElement.clientHeight) {
        clientHeight = (document.body.clientHeight < document.documentElement.clientHeight) ? document.body.clientHeight : document.documentElement.clientHeight;
    } else {
        clientHeight = (document.body.clientHeight > document.documentElement.clientHeight) ? document.body.clientHeight : document.documentElement.clientHeight;
    }
    scrollHeight = Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);

    return {
        scrollTop: scrollTop,
        clientHeight: clientHeight,
        scrollHeight: scrollHeight,
    }
}

function minNumber(el) {
    if (el.value == "" || el.value < 0) {
        el.value = ""
    }
}