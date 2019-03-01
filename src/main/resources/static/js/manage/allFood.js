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
        id:"food_table_render",
        elem: '#food_table_id',//指定表格元素
        url: "/"+projectName+"/manage/allFood",  //请求路径
        skin: 'line ' //表格风格 line （行边框风格）row （列边框风格）nob （无边框风格）
        ,limit:10//要传向后台的每页显示条数
        ,method:'post'
        ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']//自定义分页布局
            ,limits:[10,15]
            ,first: false //不显示首页
            ,last: false //不显示尾页
        }
        // ,toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        ,cols: [[ //表头

            {type: 'checkbox', fixed: 'left',width: 40,LAY_CHECKED:false}
            // ,{field: 'foodId', title: 'ID', fixed: 'left',hide:true}
            ,{field: 'foodName', title: '名称',}
            ,{field: 'typeId', title: '类型', }
            ,{field: 'eat_part', title: '可食部分',}
            ,{field: 'energy', title: '能量', }
            ,{field: 'moisture', title: '水分', }
            ,{field: 'protein', title: '蛋白质',}
            ,{field: 'fat', title: '脂肪'}
            ,{field: 'fiber', title: '膳食纤维', }
            ,{field: 'carbohydrate', title: '碳水化合物',}
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
            ,{fixed: 'right', align:'center',title: '操作', toolbar: '#bar'}

        ]]
    });


    //加载页面数据
    // var foodData = '';
    // $.get("/"+projectName+"/manage/allFood", function(data){
    //     foodData = data;
    //     // if(window.sessionStorage.getItem("addFood")){
    //     //     var addFood = window.sessionStorage.getItem("addFood");
    //     //     foodData = JSON.parse(addFood).concat(foodData);
    //     // }
    //     //执行加载数据的方法
    //     foodList();
    //     console.log("data",foodData)
    // })


    //后台
    //查询
    $(".search_btn").click(function(){
        var userArray = [];
        if($(".search_input").val() != ''){
            var index = layer.msg('查询中，请稍候',{icon: 16,time:false,shade:0.8});
            setTimeout(function(){
                $.ajax({
                    url : "../../json/usersList.json",
                    type : "get",
                    dataType : "json",
                    success : function(data){
                        // if(window.sessionStorage.getItem("addFood")){
                        //     var addFood = window.sessionStorage.getItem("addFood");
                        //     foodData = JSON.parse(addFood).concat(data);
                        // }else{
                            foodData = data;
                        // }
                        for(var i=0;i<foodData.length;i++){
                            var usersStr = foodData[i];
                            var selectStr = $(".search_input").val();
                            function changeStr(data){
                                var dataStr = '';
                                var showNum = data.split(eval("/"+selectStr+"/ig")).length - 1;
                                if(showNum > 1){
                                    for (var j=0;j<showNum;j++) {
                                        dataStr += data.split(eval("/"+selectStr+"/ig"))[j] + "<i style='color:#03c339;font-weight:bold;'>" + selectStr + "</i>";
                                    }
                                    dataStr += data.split(eval("/"+selectStr+"/ig"))[showNum];
                                    return dataStr;
                                }else{
                                    dataStr = data.split(eval("/"+selectStr+"/ig"))[0] + "<i style='color:#03c339;font-weight:bold;'>" + selectStr + "</i>" + data.split(eval("/"+selectStr+"/ig"))[1];
                                    return dataStr;
                                }
                            }
                            //用户名
                            if(usersStr.userName.indexOf(selectStr) > -1){
                                usersStr["userName"] = changeStr(usersStr.userName);
                            }
                            //用户邮箱
                            if(usersStr.userEmail.indexOf(selectStr) > -1){
                                usersStr["userEmail"] = changeStr(usersStr.userEmail);
                            }
                            //性别
                            if(usersStr.userSex.indexOf(selectStr) > -1){
                                usersStr["userSex"] = changeStr(usersStr.userSex);
                            }
                            //会员等级
                            if(usersStr.userGrade.indexOf(selectStr) > -1){
                                usersStr["userGrade"] = changeStr(usersStr.userGrade);
                            }
                            if(usersStr.userName.indexOf(selectStr)>-1 || usersStr.userEmail.indexOf(selectStr)>-1 || usersStr.userSex.indexOf(selectStr)>-1 || usersStr.userGrade.indexOf(selectStr)>-1){
                                userArray.push(usersStr);
                            }
                        }
                        foodData = userArray;
                        foodList(foodData);
                    }
                })

                layer.close(index);
            },2000);
        }else{
            layer.msg("请输入需要查询的内容");
        }
    })

    //添加食材
    $(".usersAdd_btn").click(function(){
    	var index = layui.layer.open({
    		title : "添加食物",
            type : 2,
    		content : "/"+projectName+"/to/CMSAddFood",
    		success : function(layero, index){
    			setTimeout(function(){
    				layui.layer.tips('点击此处返回食物列表', '.layui-layer-setwin .layui-layer-close', {
    					tips: 3
    				});
    			},500)
    		}
    	})
    	//改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
    	$(window).resize(function(){
    		layui.layer.full(index);
    	})
    	layui.layer.full(index);
    })

    //后台
    //批量删除
    // $(".batchDel").click(function(){
    //     var $checkbox = $('.food_list tbody input[type="checkbox"][name="checked"]');
    //     var $checked = $('.food_list tbody input[type="checkbox"][name="checked"]:checked');
    //     if($checkbox.is(":checked")){
    //         layer.confirm('确定删除选中的信息？',{icon:3, title:'提示信息'},function(index){
    //             var index = layer.msg('删除中，请稍候',{icon: 16,time:false,shade:0.8});
    //             setTimeout(function(){
    //                 //删除数据
    //                 for(var j=0;j<$checked.length;j++){
    //                     for(var i=0;i<foodData.length;i++){
    //                         if(foodData[i].newsId == $checked.eq(j).parents("tr").find(".news_del").attr("data-id")){
    //                             foodData.splice(i,1);
    //                             foodList(foodData);
    //                         }
    //                     }
    //                 }
    //                 $('.food_list thead input[type="checkbox"]').prop("checked",false);
    //                 form.render();
    //                 layer.close(index);
    //                 layer.msg("删除成功");
    //             },2000);
    //         })
    //     }else{
    //         layer.msg("请选择需要删除的文章");
    //     }
    // })

    //全选
    // form.on('checkbox(allChoose)', function(data){
    //     var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
    //     child.each(function(index, item){
    //         item.checked = data.elem.checked;
    //     });
    //     form.render('checkbox');
    // });

    // //通过判断 是否全部选中来确定全选按钮是否选中
    // form.on("checkbox(choose)",function(data){
    //     var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
    //     var childChecked = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"]):checked')
    //     if(childChecked.length == child.length){
    //         $(data.elem).parents('table').find('thead input#allChoose').get(0).checked = true;
    //     }else{
    //         $(data.elem).parents('table').find('thead input#allChoose').get(0).checked = false;
    //     }
    //     form.render('checkbox');
    // })

    // //后台
    // //操作
    // $("body").on("click",".users_edit",function(){  //编辑
    //
    //
    //     layer.alert('您点击了会员编辑按钮，由于是纯静态页面，所以暂时不存在编辑内容，后期会添加，敬请谅解。。。',{icon:6, title:'文章编辑'});
    //
    //
    // })

    //后台
    // $("body").on("click",".food_del",function(obj){  //删除
    //     var _this = $(this);
    //     console.log("data",obj.data['foodName'])
    //     layer.confirm('确定删除此用户？',{icon:3, title:'提示信息'},function(index){
    //         // var id = _this.parents("tr").find(".food_id").val();
    //         // console.log("data",foodData)
    //         // for(var i=0;i<foodData.length;i++){
    //         //
    //         //     if(foodData[i].foodId == _this.attr("data-id")){
    //         //         console.log("foodData[i].foodId",foodData[i].foodId)
    //         //         console.log("_this.attr(data-id)",_this.attr("data-id"))
    //         //         console.log("i",i)
    //         //         foodData.splice(i,1);
    //         //         foodList(foodData);
    //         //     }
    //         // }
    //
    //
    //         // $.ajax({
    //         //     url:"/" + projectName+"/manage/delOneFood",
    //         //     type:"post",
    //         //     data:{foodId:id},
    //         //     success:function(data){
    //         //         if(data){
    //         //             top.layer.msg("删除成功！");
    //         //             layer.closeAll("iframe");
    //         //             //刷新父页面
    //         //             // parent.location.reload();
    //         //             obj.del();
    //         //         }else {
    //         //             layer.open({
    //         //                 type: 1,
    //         //                 icon: 5,
    //         //                 content: '<div style="padding: 20px 100px;">'+ "删除失败" +'</div>',
    //         //                 btn: '关闭',
    //         //                 btnAlign: 'c', //按钮居中
    //         //                 shade: 0, //不显示遮罩
    //         //                 yes: function(){
    //         //                     layer.closeAll();
    //         //                 }
    //         //             });
    //         //         }
    //         //     }
    //         // });
    //         layer.close(index);
    //     });
    // })

    //批量删除 2
    var $$ = layui.$, active = {
        getCheckData: function(obj){ //获取选中数据
            var checkStatus = table.checkStatus('food_table_render')
                ,data = checkStatus.data;
            console.log("data",JSON.stringify(data))
            layer.confirm('真的删除行么', function(index){
                console.log("index",index)
                // $.ajax({
                //     url:"/" + projectName+"/manage/delOneFood",
                //     type:"post",
                //     data:{foodId:id},
                //     success:function(data){
                //         if(data){
                //             top.layer.msg("删除成功！");
                //             layer.closeAll("iframe");
                //             //刷新父页面
                //             // parent.location.reload();
                //             obj.del();
                //         }else {
                //             layer.open({
                //                 type: 1,
                //                 icon: 5,
                //                 content: '<div style="padding: 20px 100px;">'+ "删除失败" +'</div>',
                //                 btn: '关闭',
                //                 btnAlign: 'c', //按钮居中
                //                 shade: 0, //不显示遮罩
                //                 yes: function(){
                //                     layer.closeAll();
                //                 }
                //             });
                //         }
                //     }
                // });
                // obj.del();
                layer.close(index);
            });
        }
    };



    //操作
    table.on('tool(food_table)', function(obj){
        var data = obj.data;
        console.log("data",data)
        if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
                // $.ajax({
                //     url:"/" + projectName+"/manage/delOneFood",
                //     type:"post",
                //     data:{foodId:id},
                //     success:function(data){
                //         if(data){
                //             top.layer.msg("删除成功！");
                //             layer.closeAll("iframe");
                //             //刷新父页面
                //             // parent.location.reload();
                //             obj.del();
                //         }else {
                //             layer.open({
                //                 type: 1,
                //                 icon: 5,
                //                 content: '<div style="padding: 20px 100px;">'+ "删除失败" +'</div>',
                //                 btn: '关闭',
                //                 btnAlign: 'c', //按钮居中
                //                 shade: 0, //不显示遮罩
                //                 yes: function(){
                //                     layer.closeAll();
                //                 }
                //             });
                //         }
                //     }
                // });
                obj.del();
                layer.close(index);
            });
        } else if(obj.event === 'edit'){
            layer.alert('编辑行：<br>'+ JSON.stringify(data))
        }else if(obj.event === 'detail'){
            layer.alert('查看：<br>'+ JSON.stringify(data))
        }
    });



    $('.batchDel .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    // function foodList(){
    //     //渲染数据
    //     function renderDate(data,curr){
    //         var dataHtml = '';
    //         currData = foodData.concat().splice(curr*nums-nums, nums);
    //         if(currData.length != 0){
    //             for(var i=0;i<currData.length;i++){
    //                 dataHtml += '<tr>'
    //                     +  '<td><input type="checkbox" name="checked" lay-skin="primary" lay-filter="choose" ></td>'
    //                     +  '<td>'+currData[i].foodName+'</td>'
    //                     +  '<td>'+currData[i].typeId+'</td>'
    //                     +  '<td>'+currData[i].eat_part+'</td>'
    //                     +  '<td>'+currData[i].energy+'</td>'
    //                     +  '<td>'+currData[i].moisture+'</td>'
    //                     +  '<td>'+currData[i].protein+'</td>'
    //                     +  '<td>'+currData[i].fat+'</td>'
    //                     +  '<td>'+currData[i].fiber+'</td>'
    //                     +  '<td>'+currData[i].carbohydrate+'</td>'
    //                     +  '<td>'+currData[i].va+'</td>'
    //                     +  '<td>'+currData[i].vb1+'</td>'
    //                     +  '<td>'+currData[i].vb2+'</td>'
    //                     +  '<td>'+currData[i].niacin+'</td>'
    //                     +  '<td>'+currData[i].ve+'</td>'
    //                     +  '<td>'+currData[i].na+'</td>'
    //                     +  '<td>'+currData[i].ca+'</td>'
    //                     +  '<td>'+currData[i].fe+'</td>'
    //                     +  '<td>'+currData[i].vc+'</td>'
    //                     +  '<td>'+currData[i].cholesterol+'</td>'
    //                     +  '<td>'
    //                     +    '<input type="hidden" class="food_id" name="food_id" value="'+currData[i].foodId+'" >'
    //                     +    '<a class="layui-btn layui-btn-mini" style="padding: 0 6px;"><i class="iconfont icon-edit"></i>编辑</a>'
    //                     +    '<a class="layui-btn layui-btn-danger layui-btn-mini food_del" style="padding: 0 5px;" data-id="'+data[i].foodId+'"><i class="layui-icon" >&#xe640;</i>删除</a>'
    //                     +  '</td>'
    //                     +'</tr>';
    //             }
    //         }else{
    //             dataHtml = '<tr><td colspan="8">暂无数据</td></tr>';
    //         }
    //         return dataHtml;
    //     }

        // //分页
        // var nums = 10; //每页出现的数据量
        // laypage({
        //     cont : "page",
        //     pages : Math.ceil(foodData.length/nums),
        //     jump : function(obj){
        //         $(".food_content").html(renderDate(foodData,obj.curr));
        //         $('.food_list thead input[type="checkbox"]').prop("checked",false);
        //         form.render();
        //     }
        // })
    // }

})