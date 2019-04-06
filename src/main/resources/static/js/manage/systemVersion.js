var pathName = window.document.location.pathname;
var projectName = pathName.substring(1, pathName.substr(1).indexOf('/') + 1);
layui.config({
    base: "/" + projectName + "/js/manage/"
}).use(['form', 'layer', 'jquery', 'table', 'laypage'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery,
        table = layui.table;


    form.on("submit(systemParameter)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            url: "/" + projectName + "/manage/changeSystemInfo",
            method:'post',
            data:data.field,
            dataType:'JSON',
            success:function(res){
                if(res){
                    setTimeout(function(){
                        layer.close(index);
                        layer.msg("系统基本参数修改成功！");
                    },1000);
                }
            }
        })
        return false;
    })

    //系统基本参数
    $.ajax({
        url : "/"+projectName+"/manage/systemInfo",
        type: "POST",
        success : function(data){
            fillParameter(data);
        }
    })

    //填充数据方法
    function fillParameter(data){
        //判断字段数据是否存在
        function nullData(data){
            if(data == '' || data == "undefined"){
                return "未定义";
            }else{
                return data;
            }
        }
        $(".version").val(nullData(data.version));
        $(".author").val(nullData(data.author));
        $(".account").val(nullData(data.account));
        $(".homePage").val(nullData(data.homePage));
        $(".projectName").val(nullData(data.projectName));
        $(".description").val(nullData(data.description));
    }
})