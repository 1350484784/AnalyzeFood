var pathName = window.document.location.pathname;
var projectName = pathName.substring(1, pathName.substr(1).indexOf('/') + 1);

layui.config({
    base: "/" + projectName + "/js/manage/"
}).use(['form', 'layer', 'jquery', 'laypage'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;

    form.on("submit(addFood)",function(data){
        $("input[name!='foodName']").each(function () {
            if($(this).val() < 0 || $(this).val() > 100){
                layer.open({
                    type: 1,
                    icon: 5,
                    content: '<div style="padding: 20px 100px;">'+ "数据不再范围内,[0,100]" +'</div>',
                    btn: '关闭',
                    btnAlign: 'c', //按钮居中
                    shade: 0, //不显示遮罩
                    yes: function(){
                        layer.closeAll();
                    }
                });
                return ;
            }
        });

        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var json_data=(data.field)
        $.ajax({
            url:"/" + projectName+"/manage/addFood",
            type:"post",
            // data:$('#addFoodForm').serialize(),
            data:json_data,
            success:function(data){
                if(data){
                    setTimeout(function(){
                        top.layer.close(index);
                        top.layer.msg("Food添加成功！");
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    },1000);
                }else {
                    top.layer.close(index);
                    layer.open({
                        type: 1,
                        icon: 5,
                        content: '<div style="padding: 20px 100px;">'+ "数据重复" +'</div>',
                        btn: '关闭',
                        btnAlign: 'c', //按钮居中
                        shade: 0, //不显示遮罩
                        yes: function(){
                            layer.closeAll();
                        }
                    });
                }
            },
            error:function () {
                top.layer.close(index);
                layer.open({
                    type: 1,
                    icon: 5,
                    content: '<div style="padding: 20px 100px;">'+ "请求出错" +'</div>',
                    btn: '关闭',
                    btnAlign: 'c', //按钮居中
                    shade: 0, //不显示遮罩
                    yes: function(){
                        layer.closeAll();
                    }
                });
            }
            

        });
        return false;
    });

})