/**
 * Created by Wangrong on 2016/8/6.
 */

function fun(parent,ul){
    var top=parseFloat(parent.offset().top)+24+"px";
    var left=parent.offset().left;
    var width=parent.css("width");
    ul.css({top:top,left:left,width:width});
  //  alert(top);
}
$(function(){
    var th=$("table th");
    fun(th.eq(2),$("#speCodeBar"));
    fun(th.eq(3),$("#speDevName"));
    fun(th.eq(4),$("#speSeqNum"));
    fun(th.eq(5),$("#speRoom"));
    fun(th.eq(6),$("#speRoomFrame"));
    fun(th.eq(7),$("#speUserName"));
    fun(th.eq(8),$("#speState"));

});
