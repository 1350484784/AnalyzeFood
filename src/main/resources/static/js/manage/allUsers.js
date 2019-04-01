var pathName=window.document.location.pathname;
var projectName=pathName.substring(1,pathName.substr(1).indexOf('/')+1);

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
        id: "user_table_render",
        elem: '#user_table_id',//指定表格元素
        url: "/" + projectName + "/manage/allUser",  //请求路径
        skin: 'line ' //表格风格 line （行边框风格）row （列边框风格）nob （无边框风格）
        , limit: 10//要传向后台的每页显示条数
        , method: 'post'
        , page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']//自定义分页布局
            , limits: [10, 15]
            , first: false //不显示首页
            , last: false //不显示尾页
        }
        // ,toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        , cols: [[ //表头
             {field: 'roleId', title: 'ID',sort: true, fixed: 'left',width:80}
            , {field: 'roleAccount', title: '昵称', width:220}
            , {field: 'name', title: '真实姓名',  width:100}
            , {field: 'age', title: '年龄',  width:80}
            , {field: 'weight', title: '体重(斤)', width:80 }
            , {field: 'sex', title: '性别', templet: '#sexTpl',  width:80}
            , {field: 'phone', title: '手机号',  width:180}
            , {field: 'createTime', title: '创建时间',sort: true,  width:180}
            , {field: 'onlineFlag', title: '是否在线',  templet: '#onlineTpl',width:100}
            , {fixed: 'right', align: 'center', title: '操作', toolbar: '#bar',}
        ]]
    });

	//查询
	$(".search_btn").click(function(){
		var userArray = [];
		if($(".search_input").val() != ''){
			var index = layer.msg('查询中，请稍候',{icon: 16,time:false,shade:0.8});
            var searchData = $(".search_input").val();
            setTimeout(function(){
                table.reload('user_table_render',{
                    page:{
                        curr:1
                    },
                    method:'post',
                    where:{
                        'searchData':searchData
                    },
                    url: "/" + projectName + "/manage/searchUser"
                });
                layer.close(index);
            },2000);
		}else{
			layer.msg("请输入需要查询的内容");
		}
	})




	//操作
    table.on('tool(user_table)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {


            var index = layui.layer.open({
                title: "编辑",
                type: 2,
                area: ['700px', '530px'],
                content: "/" + projectName + "/manage/editFood?foodId="+data.foodId,
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回食物列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                }
            })
            //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
            $(window).resize(function () {
                layui.layer.full(index);
            })
            layui.layer.full(index);
        }
    });


    $(' .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

        
})