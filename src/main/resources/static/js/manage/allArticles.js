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
        id: "article_table_render",
        elem: '#article_table_id',//指定表格元素
        url: "/" + projectName + "/manage/allArticle",  //请求路径
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
            {field: 'articleId', title: 'ID',sort: true, fixed: 'left',width:80}
            , {field: 'authorName', title: '作者', width:150}
            , {field: 'title', title: '标题',  width:220, event:'showContent',style:'color: blue;'}
            , {field: 'type', title: '文章类型', width:150 }
            , {field: 'pic_path', title: '封面地址', width:180 }
            , {field: 'view', title: '浏览数',  width:80}
            , {field: 'commentNum', title: '评论数',  width:80}
            , {field: 'createTime', title: '创建时间',sort: true,  width:180}
            , {field: 'status', title: '审核',  templet: '#checkTpl'}
        ]]
    });

    //查询
    $(".search_btn").click(function(){
        if($(".search_input").val() != ''){
            var index = layer.msg('查询中，请稍候',{icon: 16,time:false,shade:0.8});
            var searchData = $(".search_input").val();
            setTimeout(function(){
                table.reload('article_table_render',{
                    page:{
                        curr:1
                    },
                    method:'post',
                    where:{
                        'searchData':searchData
                    },
                    url: "/" + projectName + "/manage/searchArticle"
                });
                layer.close(index);
            },2000);
        }else{
            layer.msg("请输入需要查询的内容");
        }
    })




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
        }
    });


    $(' .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });


})