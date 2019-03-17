
$(function () {
    //头像 模态框
    $("#headfile").change(function () {

        $("#uploadHeadImgErr").addClass("hide");

        var path = $(this).val();
        if(path==null ||path.length==0){return;}
        var extStart = path.lastIndexOf('.'),
            ext = path.substring(extStart, path.length).toUpperCase();
        if (ext !== '.PNG' && ext !== '.JPG' && ext !== '.JPEG' && ext !== '.GIF') {
            $("#uploadHeadImgErr").removeClass("hide").html('<span class="fa fa-exclamation-circle text-danger " style="font-size: 17px"></span> 请上传正确的文件格式')
            return false;
        }
        var str=document.getElementById("headfile").files[0];//图片的地址,安全加密
        //获得原始的二进制图片地址
        var imgUlr=window.URL.createObjectURL(str);//解密
        $("#newHeadImg").attr("src",imgUlr);
    })

    //空间背景
    $("#backfile").change(function () {

        $("#uploadBackImgErr").addClass("hide");

        var path = $(this).val();
        if(path==null ||path.length==0){return;}
        var extStart = path.lastIndexOf('.'),
            ext = path.substring(extStart, path.length).toUpperCase();
        if (ext !== '.PNG' && ext !== '.JPG' && ext !== '.JPEG' && ext !== '.GIF') {
            $("#uploadBackImgErr").removeClass("hide").html('<span class="fa fa-exclamation-circle text-danger " style="font-size: 17px"></span> 请上传正确的文件格式')
            return false;
        }

        var str=document.getElementById("backfile").files[0];//图片的地址,安全加密
        //获得原始的二进制图片地址
        var imgUlr=window.URL.createObjectURL(str);//解密
        $("#newImg").attr("src",imgUlr);
    })
});

