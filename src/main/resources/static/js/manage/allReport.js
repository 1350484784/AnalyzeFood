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
            {type: 'checkbox', fixed: 'left', width: 40, LAY_CHECKED: false}
            , {field: 'id', title: 'ID',sort: true, fixed: 'left',width:60}
            , {field: 'title', title: '标题',  width:180, event:'showContent',style:'color: blue;'}
            , {field: 'authorId', title: '作者id', width:80 }
            , {field: 'author', title: '作者账号', width:150 }
            , {field: 'roleId', title: '举报人id', width:80 }
            , {field: 'reportUserName', title: '举报人账号', width:150}
            , {field: 'reportContent', title: '举报内容',  width:180, event:'showReportContent',style:'color: blue;'}
            , {field: 'reportTime', title: '举报时间',sort: true,  width:180}
            , {field: 'status', title: '审核中...', filter: "isShow",
                templet: function (d) {
                    if (d.status == 1) {
                        return '<input type = "checkbox" name="switchStatus" lay-skin = "switch" lay-text = "通过|不通过" lay-filter = "isShow" > ';
                    }
                }
                ,width:100}
            , {fixed: 'right', align: 'center', title: '操作', toolbar: '#bar'}
        ]]
    });


    // 批量
    var $$ = layui.$, active = {
        getCheckData: function () { //获取选中数据
            var checkStatus = table.checkStatus('report_table_render')
                , data = checkStatus.data;
            var jsonData = JSON.stringify(data);
            if (jsonData == "[]") {
                top.layer.msg("请选择之后在删除");
                return;
            }

            var delList = [];
            data.forEach(function (n, i) {
                delList.push(n.id)
            });
            layer.confirm('确定审核么', function (index) {
                $.ajax({
                    url: "/" + projectName + "/manage/delManyReport",
                    type: "post",
                    data: "delReportList=" + delList,
                    dataType: 'json',
                    success: function (data) {
                        if (data) {
                            top.layer.msg("反馈成功！");
                            table.reload('report_table_render', {});
                        } else {
                            layer.open({
                                type: 1,
                                icon: 5,
                                content: '<div style="padding: 20px 100px;">' + "反馈失败" + '</div>',
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
                layer.close(index);
            });
        }
    };


    //操作
    table.on('tool(report_table)', function (obj) {
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
        } else if (obj.event === 'showReportContent') {
            layer.open({
                type: 1,
                icon: 5,
                title: '内容',
                content: data.reportContent,
                btn: '关闭',
                btnAlign: 'r', //按钮居中
                shade: 0, //不显示遮罩
                area: '500px',
                yes: function () {
                    layer.closeAll();
                }
            });
        } else if (obj.event === 'del') {
            layer.confirm('取消举报信息么', function (index) {
                console.log("data",data)
                console.log("index", index)
                console.log("status", $('.layui-form-switch').eq(index-1).find('em').html())

                // $.ajax({
                //     url: "/" + projectName + "/manage/delOneReport",
                //     type: "post",
                //     data: {id: data.id, authorId:data.authorId,roleId:data.roleId},
                //     success: function (data) {
                //         if (data) {
                //             top.layer.msg("取消成功！");
                //             layer.closeAll("iframe");
                //         } else {
                //             layer.open({
                //                 type: 1,
                //                 icon: 5,
                //                 content: '<div style="padding: 20px 100px;">' + "取消失败" + '</div>',
                //                 btn: '关闭',
                //                 btnAlign: 'c', //按钮居中
                //                 shade: 0, //不显示遮罩
                //                 yes: function () {
                //                     layer.closeAll();
                //                 }
                //             });
                //         }
                //     }
                // });
                // obj.del();
                // layer.close(index);
            });
        }
    });


    $('.batchDel .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });


})