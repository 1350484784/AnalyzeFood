var pathName = window.document.location.pathname;
var projectName = pathName.substring(1, pathName.substr(1).indexOf('/') + 1);

layui.config({
    base : "/"+projectName+"/js/manage/"
}).use(['form','layer','jquery','table','laypage'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery,
        table = layui.table;


    // 引入 table模块
    table.render({
        id: "report_table_render",
        elem: '#report_table_id',//指定表格元素
        url: "/" + projectName + "/manage/allReport",  //请求路径
        skin: 'line ' //表格风格 line （行边框风格）row （列边框风格）nob （无边框风格）
        , limit: 10//要传向后台的每页显示条数
        , method: 'post'
        , page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']//自定义分页布局
            , limits: [10, 15]
            , first: false //不显示首页
            , last: false //不显示尾页
        }
        , cols: [[ //表头
            {field: 'id', title: 'ID',sort: true, fixed: 'left',width:80}
            , {field: 'author', title: '作者', width:150}
            , {field: 'title', title: '标题',  width:220, event:'showContent',style:'color: blue;'}
            , {field: 'authorId', title: '作者id', width:150 }
            , {field: 'roleId', title: '举报人id', width:180 }
            , {field: 'reportUserName', title: '举报人', templet: '#sexTpl',  width:80}
            , {field: 'reportContent', title: '举报内容',  width:80}
            , {field: 'reportTime', title: '举报时间',sort: true,  width:180}
            , {field: 'status', title: '审核', width:100 }
            , {fixed: 'right', align: 'center', title: '操作', toolbar: '#bar'}
        ]]
    });

    //批量删除


    //操作
    table.on('tool(article_table)', function (obj) {
        var data = obj.data;
        if (obj.event === 'showContent') {
            layer.open({
                type: 1,
                icon: 5,
                title: '内容',
                content: data.content,
                btn: '关闭',
                btnAlign: 'r', //按钮居中
                shade: 0, //不显示遮罩
                area: '500px',
                yes: function () {
                    layer.closeAll();
                }
            });
        }else if (obj.event === 'del') {
            layer.confirm('取消举报信息么', function (index) {
                $.ajax({
                    url: "/" + projectName + "/manage/delOneReport",
                    type: "post",
                    data: {foodId: data.foodId},
                    success: function (data) {
                        if (data) {
                            top.layer.msg("取消成功！");
                            layer.closeAll("iframe");
                        } else {
                            layer.open({
                                type: 1,
                                icon: 5,
                                content: '<div style="padding: 20px 100px;">' + "取消失败" + '</div>',
                                btn: '关闭',
                                btnAlign: 'c', //按钮居中
                                shade: 0, //不显示遮罩
                                yes: function () {
                                    layer.closeAll();
                                }
                            });
                        }
                    }
                });
                obj.del();
                layer.close(index);
            });
        }
    });


    $(' .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });


})