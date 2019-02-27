var pathName=window.document.location.pathname;
var projectName=pathName.substring(1,pathName.substr(1).indexOf('/')+1);

layui.config({
    base : "/"+projectName+"/js/manage/"
}).use(['form','layer','jquery','laypage'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;

    //加载页面数据
    var foodData = '';
    $.get("/"+projectName+"/manage/allFood", function(data){
        foodData = data;
        if(window.sessionStorage.getItem("addFood")){
            var addFood = window.sessionStorage.getItem("addFood");
            foodData = JSON.parse(addFood).concat(foodData);
        }
        //执行加载数据的方法
        foodList();
    })


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
                        if(window.sessionStorage.getItem("addFood")){
                            var addFood = window.sessionStorage.getItem("addFood");
                            foodData = JSON.parse(addFood).concat(data);
                        }else{
                            foodData = data;
                        }
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

    //后台
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
    $(".batchDel").click(function(){
        var $checkbox = $('.food_list tbody input[type="checkbox"][name="checked"]');
        var $checked = $('.food_list tbody input[type="checkbox"][name="checked"]:checked');
        if($checkbox.is(":checked")){
            layer.confirm('确定删除选中的信息？',{icon:3, title:'提示信息'},function(index){
                var index = layer.msg('删除中，请稍候',{icon: 16,time:false,shade:0.8});
                setTimeout(function(){
                    //删除数据
                    for(var j=0;j<$checked.length;j++){
                        for(var i=0;i<foodData.length;i++){
                            if(foodData[i].newsId == $checked.eq(j).parents("tr").find(".news_del").attr("data-id")){
                                foodData.splice(i,1);
                                foodList(foodData);
                            }
                        }
                    }
                    $('.food_list thead input[type="checkbox"]').prop("checked",false);
                    form.render();
                    layer.close(index);
                    layer.msg("删除成功");
                },2000);
            })
        }else{
            layer.msg("请选择需要删除的文章");
        }
    })

    //全选
    form.on('checkbox(allChoose)', function(data){
        var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
        child.each(function(index, item){
            item.checked = data.elem.checked;
        });
        form.render('checkbox');
    });

    //通过判断 是否全部选中来确定全选按钮是否选中
    form.on("checkbox(choose)",function(data){
        var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
        var childChecked = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"]):checked')
        if(childChecked.length == child.length){
            $(data.elem).parents('table').find('thead input#allChoose').get(0).checked = true;
        }else{
            $(data.elem).parents('table').find('thead input#allChoose').get(0).checked = false;
        }
        form.render('checkbox');
    })

    //后台
    //操作
    $("body").on("click",".users_edit",function(){  //编辑


        layer.alert('您点击了会员编辑按钮，由于是纯静态页面，所以暂时不存在编辑内容，后期会添加，敬请谅解。。。',{icon:6, title:'文章编辑'});


    })

    //后台
    $("body").on("click",".users_del",function(){  //删除
        var _this = $(this);
        layer.confirm('确定删除此用户？',{icon:3, title:'提示信息'},function(index){
            //_this.parents("tr").remove();
            for(var i=0;i<foodData.length;i++){
                if(foodData[i].usersId == _this.attr("data-id")){
                    foodData.splice(i,1);
                    foodList(foodData);
                }
            }
            layer.close(index);
        });
    })

    function foodList(){
        //渲染数据
        function renderDate(data,curr){
            var dataHtml = '';
            currData = foodData.concat().splice(curr*nums-nums, nums);
            if(currData.length != 0){
                for(var i=0;i<currData.length;i++){
                    dataHtml += '<tr>'
                        +  '<td><input type="checkbox" name="checked" lay-skin="primary" lay-filter="choose" ></td>'
                        +  '<td>'+currData[i].foodName+'</td>'
                        +  '<td>'+currData[i].typeId+'</td>'
                        +  '<td>'+currData[i].eat_part+'</td>'
                        +  '<td>'+currData[i].energy+'</td>'
                        +  '<td>'+currData[i].moisture+'</td>'
                        +  '<td>'+currData[i].protein+'</td>'
                        +  '<td>'+currData[i].fat+'</td>'
                        +  '<td>'+currData[i].fiber+'</td>'
                        +  '<td>'+currData[i].carbohydrate+'</td>'
                        +  '<td>'+currData[i].va+'</td>'
                        +  '<td>'+currData[i].vb1+'</td>'
                        +  '<td>'+currData[i].vb2+'</td>'
                        +  '<td>'+currData[i].niacin+'</td>'
                        +  '<td>'+currData[i].ve+'</td>'
                        +  '<td>'+currData[i].na+'</td>'
                        +  '<td>'+currData[i].ca+'</td>'
                        +  '<td>'+currData[i].fe+'</td>'
                        +  '<td>'+currData[i].vc+'</td>'
                        +  '<td>'+currData[i].cholesterol+'</td>'
                        +  '<td>'
                        +    '<a class="layui-btn layui-btn-mini" style="padding: 0 6px;"><i class="iconfont icon-edit"></i>编辑</a>'
                        +    '<a class="layui-btn layui-btn-danger layui-btn-mini" style="padding: 0 5px;" data-id="'+data[i].foodId+'"><i class="layui-icon" >&#xe640;</i>删除</a>'
                        +  '</td>'
                        +'</tr>';
                }
            }else{
                dataHtml = '<tr><td colspan="8">暂无数据</td></tr>';
            }
            return dataHtml;
        }

        //分页
        var nums = 10; //每页出现的数据量
        laypage.render({
            cont : "page",
            pages : Math.ceil(foodData.length/nums),
            jump : function(obj){
                $(".food_content").html(renderDate(foodData,obj.curr));
                $('.food_list thead input[type="checkbox"]').prop("checked",false);
                form.render();
            }
        })
    }

})