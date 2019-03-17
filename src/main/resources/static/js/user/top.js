function touch_on(){
    $('.dropdown-menu').css('display','block');
}
function touch_out(){
    $('.dropdown-menu').css('display','none');
}

function centerContent(str, delay){
    $("#centerContent").html(str).delay(delay).fadeIn({duration:200}).delay(1000).fadeOut({duration:200});
}

