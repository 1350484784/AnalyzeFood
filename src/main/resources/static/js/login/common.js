$(function(){
	
	//登录输入框效果
	$('.form_text_ipt input').focus(function(){
		$(this).parent().css({
			'box-shadow':'0 0 3px #bbb',
		});
	});
	$('.form_text_ipt input').blur(function(){
		$(this).parent().css({
			'box-shadow':'none',
		});
	});
	
	//表单验证
	$('.form_text_ipt input').blur('input propertychange',function(){
		if($(this).val()==""){
			$(this).css({
				'color':'red',
			});
			$(this).parent().css({
				'border':'solid 1px red',
			});
		}else{
			$(this).css({
				'color':'#ccc',
			});
			$(this).parent().css({
				'border':'solid 1px #ccc',
			});
		}
	});
	$('.form_text_ipt input').focus('input propertychange',function(){
		$(this).css({
			'color':'#ccc',
		});
		$(this).parent().css({
			'border':'solid 1px #ccc',
		});
	});

    getCookie();
});



function setCookie(){ //设置cookie    
	var loginCode = $("#login_code").val(); //获取用户名信息    
	var pwd = $("#login_password").val(); //获取登陆密码信息    
	var checked = $("[name='checkbox']:checked");//获取“是否记住密码”复选框  

	if(checked){ //判断是否选中了“记住密码”复选框    
		$.cookie("login_code",loginCode);//调用jquery.cookie.js中的方法设置cookie中的用户名    
		$.cookie("pwd",$.base64.encode(pwd));//调用jquery.cookie.js中的方法设置cookie中的登陆密码，并使用base64（jquery.base64.js）进行加密    
	}else{
		$.cookie("pwd", null);
	}
}

function getCookie(){ //获取cookie    
	var loginCode = $.cookie("login_code"); //获取cookie中的用户名    
	var pwd =  $.cookie("pwd"); //获取cookie中的登陆密码    
	if(pwd){//密码存在的话把“记住用户名和密码”复选框勾选住    
		$("[name='checkbox']").attr("checked","true");
	}
	if(loginCode){//用户名存在的话把用户名填充到用户名文本框    
		$("#login_code").val(loginCode);
	}
	if(pwd){//密码存在的话把密码填充到密码文本框    
		$("#login_password").val($.base64.decode(pwd));
	}
}


function login(){
	var userName = $('#login_code').val();
	var userPass = $('#login_password').val();

    var userNameRagex = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
    var pwdRegex = /^[\x21-\x7E]{6,20}$/;


	if(userName == ''){
		alert("请输入用户名");
		return false;
	}

	if(userPass == ''){
		alert("请输入密码");
		return false;
	}

    if(!userNameRagex.test(userName) && userName!= 'admin'){
        alert("用户名必须是手机号");
        return false;
    }
    if(!pwdRegex.test(userPass) && userPass != 'admin123'){
        alert("密码6-20位");
        return false;
    }

	//判断是否选中复选框，如果选中，添加cookie  
	if($('#checkeds').prop("checked")){
		//添加cookie    
		setCookie();
	}

	$('#login_form').submit();
}


var div1=document.getElementsByClassName("m-lion-dialog");
var div2=document.getElementsByClassName("m-lion-dialog-overlay");
function check(){
	var pwd = document.getElementById("pwd");
	var suggestion=document.getElementById("suggestion");
	if(!pwd.value){
		suggestion.style.display="block";
	}else{
		suggestion.style.display="none";

	}

}
function delclick(){
	var pws = document.getElementById("m-lion-dialog-overlay");
	var pw = document.getElementById("m-lion-dialog");
	pw.style.display="none";
	pws.style.display="none";
}
function checkother(){
	var pws = document.getElementById("m-lion-dialog-overlay");
	var pw = document.getElementById("m-lion-dialog");
	pw.style.display="block";
	pws.style.display="block";
}
//输入密码提示
function  submitB(){
	var user = document.getElementById("user");
	var user_pass = document.getElementById("user_pass");
	var pwd = document.getElementById("pwd");
	var pwd_pass = document.getElementById("pwd_pass");
	var smsCode = document.getElementById("smsCode");
	var sms_pass = document.getElementById("sms_pass");
	if(!smsCode.value){
		sms_pass.style.color="red";
		sms_pass.style.fontSize = "13px";
		sms_pass.style.height="2em";
		sms_pass.style.lineHeight="2em";
		sms_pass.innerHTML = '请填写您的验证码。';
		smsCode.focus();
		return false;
	}
	if(!user.value){
		user_pass.style.color="red";
		user_pass.style.fontSize = "13px";
		user_pass.style.height="2em";
		user_pass.style.lineHeight="2em";
		user_pass.innerHTML = '请填写您的手机号码。';
		user.focus();
		return false;
	}
	if(!pwd.value){
		pwd_pass.style.color="red";
		pwd_pass.style.fontSize = "13px";
		pwd_pass.style.height="2em";
		pwd_pass.style.lineHeight="2em";
		pwd_pass.innerHTML = '请填写您的用户密码。';
		pwd.focus();
		return false;
	}
	else{
		var f = sendParam();
		return f;
	}
}
