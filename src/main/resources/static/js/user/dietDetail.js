var pathName = window.document.location.pathname;
var projectName = pathName.substring(1, pathName.substr(1).indexOf('/') + 1);

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
            async:false,
            success: function (data) {
                if(!data){
                    alert("没有数据！");
                }else{
                    foodTypeObj = data;
                    window.sessionStorage.setItem("foodType",JSON.stringify(data));
                }
            }
        });
    }
    return foodTypeObj
}


//根据食物类型 获取 食物类型ID
function getFoodTypeId(typeName) {
    var foodTypeIds = [];
    var foodType = getFoodType();
    for(var i=0; i < foodType.length; i++){
        if(foodType[i].typeName === typeName){
            foodTypeIds.push(foodType[i].number);
        }
    }
    return foodTypeIds;
}


//得到 食物类型 class
function getFoodClass(typeId) {
    var foodType = getFoodType();
    var foodClass = ['谷','豆','蔬','果','肉','乳','蛋','鱼','油','另'];

    var typeName = '另';
    var thisFoodClass = 'food-type-';
    for(var j = 0; j < foodType.length; j++) {
        if(typeId == foodType[j].number){
            typeName = foodType[j].typeName;
            break;
        }
    }
    for(var j = 0; j < foodClass.length; j++){
        if(typeName == foodClass[j]){
            thisFoodClass = thisFoodClass + (j+1);
            break;
        }
    }
    return thisFoodClass;
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