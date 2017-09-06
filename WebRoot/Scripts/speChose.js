/**
 * Created by Wangrong on 2016/8/5.
 */

function fun(parent,ul){
    var top=parseFloat(parent.offset().top)+24+"px";
    var left=parent.offset().left;
    //alert(left+aaa);
    //  var width=parseFloat(parent.css("width"))-2+"px";
    //   alert(width);
//    ul.css({top:top,left:left,width:width});
    // ul.css("display","block");
}
$(function(){
   // alert("11111");
    var th=$("table th img");
    th.eq(0).bind("click",function(){
        fun($(this).parent(),$("#speDevName1"));
    });
    th.eq(1).bind("click",function(){
        fun($(this).parent(),$("#speRoom1"));
    })
    th.eq(2).bind("click",function(){
        fun($(this).parent(),$("#speRoomFrame1"));
    })
    th.eq(3).bind("click",function(){
        fun($(this).parent(),$("#speUserName1"));
    })
    th.eq(4).bind("click",function(){
        fun($(this).parent(),$("#speState1"));
    })
});

