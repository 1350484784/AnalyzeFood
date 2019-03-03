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


    //json  food_type
    var foodTypeObj = [];
    if(window.sessionStorage.getItem("foodType")){
        foodTypeObj = JSON.parse(window.sessionStorage.getItem("foodType"));
        console.log("1",foodTypeObj)
    }else{
        $.ajax({
            url: "/" + projectName + "/manage/getFoodType",
            type: "post",
            success: function (data) {
                if(!data){
                    top.layer.msg("没有数据！");
                }else{
                    console.log("2",JSON.stringify(data))
                    window.sessionStorage.setItem("foodType",JSON.stringify(data));
                }
            }
        });
    }


    // 引入 table模块
    table.render({
        id: "food_table_render",
        elem: '#food_table_id',//指定表格元素
        url: "/" + projectName + "/manage/allFood",  //请求路径
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

            {type: 'checkbox', fixed: 'left', width: 40, LAY_CHECKED: false}
            // ,{field: 'foodId', title: 'ID', fixed: 'left',hide:true}
            , {field: 'foodName', title: '名称',}
            // ,{field: 'typeId', title: '类型', }
            , {field: 'eat_part', title: '可食部分', width: 120}
            , {field: 'energy', title: '能量', width: 120}
            , {field: 'moisture', title: '水分', width: 120}
            , {field: 'protein', title: '蛋白质', width: 120}
            , {field: 'fat', title: '脂肪', width: 120}
            , {field: 'fiber', title: '膳食纤维', width: 120}
            , {field: 'carbohydrate', title: '碳水化合物', width: 120}
            // ,{field: 'va', title: '维A', }
            // ,{field: 'vb1', title: '维B1', }
            // ,{field: 'vb2', title: '维B2', }
            // ,{field: 'niacin', title: '烟酸', }
            // ,{field: 've', title: '维E', }
            // ,{field: 'na', title: '钠', }
            // ,{field: 'ca', title: '钙', }
            // ,{field: 'fe', title: '铁', }
            // ,{field: 'vc', title: '维C', }
            // ,{field: 'cholesterol', title: '胆固醇', }
            , {fixed: 'right', align: 'center', title: '操作', toolbar: '#bar'}

        ]]
    });


    //查询
    $(".search_btn").click(function(){
        if($(".search_input").val() != ''){
            var searchData = $(".search_input").val();
            var index = layer.msg('查询中，请稍候',{icon: 16,time:false,shade:0.8});
            setTimeout(function(){
                table.reload('food_table_render',{
                    page:{
                        curr:1
                    },
                    method:'post',
                    where:{
                        'searchData':searchData
                    },
                    url: "/" + projectName + "/manage/searchFood"
                });

                layer.close(index);
            },1500);
        }else{
            layer.msg("请输入需要查询的内容");
        }
    })

    //添加食材
    $(".foodAdd_btn").click(function () {
        var index = layui.layer.open({
            title: "添加食物",
            type: 2,
            area: ['700px', '530px'],
            content: "/" + projectName + "/to/CMSAddFood",
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
    })


    // 批量删除
    var $$ = layui.$, active = {
        getCheckData: function () { //获取选中数据
            var checkStatus = table.checkStatus('food_table_render')
                , data = checkStatus.data;
            var jsonData = JSON.stringify(data);
            if (jsonData == "[]") {
                top.layer.msg("请选择之后在删除");
                return;
            }

            var delList = [];
            data.forEach(function (n, i) {
                delList.push(n.foodId)
            });
            layer.confirm('真的删除行么', function (index) {
                $.ajax({
                    url: "/" + projectName + "/manage/delManyFood",
                    type: "post",
                    data: "delFoodList=" + delList,
                    dataType: 'json',
                    success: function (data) {
                        if (data) {
                            top.layer.msg("删除成功！");
                            table.reload('food_table_render', {});
                        } else {
                            layer.open({
                                type: 1,
                                icon: 5,
                                content: '<div style="padding: 20px 100px;">' + "删除失败" + '</div>',
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


    // 导出数据
    $(".exportOut").click(function () {
        $.ajax({
            url: "/" + projectName + "/manage/exportOutFood",
            type: "post",
            success: function (data) {
                if(!data){
                    top.layer.msg("没有数据！");
                }else{
                    table.exportFile(['名称','类型','可食部分','能量','水分','蛋白质','脂肪','膳食纤维','碳水化合物','维A','维B1','维B2','烟酸','维E','钠','钙','铁','维C','胆固醇'], data, 'xls');
                }
            }
        });
    });

    // 操作
    table.on('tool(food_table)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                $.ajax({
                    url: "/" + projectName + "/manage/delOneFood",
                    type: "post",
                    data: {foodId: data.foodId},
                    success: function (data) {
                        if (data) {
                            top.layer.msg("删除成功！");
                            layer.closeAll("iframe");
                        } else {
                            layer.open({
                                type: 1,
                                icon: 5,
                                content: '<div style="padding: 20px 100px;">' + "删除失败" + '</div>',
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
        } else if (obj.event === 'edit') {
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
        } else if (obj.event === 'detail') {
            var typeName = '';
            foodTypeObj.forEach(function (n, i) {
                if(n.number === data.typeId){
                    typeName = n.typeName;
                }
            });
            layer.open({
                type: 1,
                title: '详细数据',
                fix: false,
                // maxmin: true,
                shadeClose: true,
                shade: 0.8,
                area: ['360px', '420px'],
                content:
                '<table align="center"  style="width: 180px;">' +
                '<tr><td >名称:</td><td>' + data.foodName+ '</td></tr>' +
                '<tr><td >类型:</td><td>' + typeName + '类</td></tr>' +
                '<tr><td >可食部分:</td><td>' + data.eat_part+ '</td></tr>' +
                '<tr><td >能量:</td><td>' + data.energy+ '</td></tr>' +
                '<tr><td >水分:</td><td>' + data.moisture+ '</td></tr>' +
                '<tr><td >蛋白质:</td><td>' + data.protein+ '</td></tr>' +
                '<tr><td >脂肪:</td><td>' + data.fat+ '</td></tr>' +
                '<tr><td >膳食纤维:</td><td>' + data.fiber+ '</td></tr>' +
                '<tr><td >碳水化合物:</td><td>' + data.carbohydrate+ '</td></tr>' +
                '<tr><td >维生素A:</td><td>' + data.va+ '</td></tr>' +
                '<tr><td >维生素B1:</td><td>' + data.vb1+ '</td></tr>' +
                '<tr><td >维生素B2:</td><td>' + data.vb2+ '</td></tr>' +
                '<tr><td >烟酸:</td><td>' + data.niacin+ '</td></tr>' +
                '<tr><td >维生素E:</td><td>' + data.ve+ '</td></tr>' +
                '<tr><td >钠:</td><td>' + data.na+ '</td></tr>' +
                '<tr><td >钙:</td><td>' + data.ca+ '</td></tr>' +
                '<tr><td >铁:</td><td>' + data.fe+ '</td></tr>' +
                '<tr><td >维生素C:</td><td>' + data.vc+ '</td></tr>' +
                '<tr><td >胆固醇:</td><td>' + data.cholesterol+ '</td></tr>' +
                '</table>'

            });
        }
    });


    $('.batchDel .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

})