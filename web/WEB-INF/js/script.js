/**
 * Application javascript utils
 */
$(document).ready(function () {
    console.log($('.toolbar').innerHeight());
    $('.main').css({ height: $(window).innerHeight() - 45});
    $('.questions').css({ height: $('.main').innerHeight() - $('.toolbar').innerHeight() - $('.header').innerHeight() - 20 });
    $(window).resize(function(){
        $('.main').css({ height: $(window).innerHeight() - 45});
        $('.questions').css({ height: $('.main').innerHeight() - $('.toolbar').innerHeight() - $('.header').innerHeight() - 20 });
    });
});
