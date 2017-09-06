/**
 * Created by Wangrong on 2016/7/31.
 */


//设置元素宽高

$(function(){
    var w=document.documentElement.clientWidth;
    var h=document.documentElement.clientHeight;
    var headerH=parseFloat(h)*0.11;
    var conH=parseFloat(h)-headerH-parseFloat($(".footer").css("height"));
    var conW=parseFloat(w)-4;
	
    $("body").css({width:w+"px",height:h+"px"});
    $(".header").css("height",headerH+"px");
    $(".header").css("width",w);
    $(".content").css({width:conW+"px",height:conH+"px"});
    $(".footer").css("width",w);
});

//日历选择时间

$(function(){
  $(".datepicker").bind("click",function(){
      WdatePicker();
  });
});

//td的悬浮效果
$(function(){
    $("#massageTable").on("mouseover","td",function(){
                $(this).prop("title",$(this).text());
      
    });
});

//左侧栏控制
$(function(){
    var imgbtn=$(".left>.ulBox>ul>li>a");
	var imgbtn1=$(".list>ul>li>a");
	
	/*var chooseA=$(imgbtn[0]).parent("li").next("ul");
	var chooseB=$(imgbtn[1]).parent("li").next("ul");
	imgbtn1.click(function(){
		//if($(this).)
		//alert($(this).attr('title'));
	//alert(1)
	if($(this).attr('title')==='PRO_CHARGE')
		{chooseA.css('display','block');
		chooseB.css('display','none');}
		//alert(1)
						   
	//if($(this).attr('title')==='TABLE_CHARGE')
	//	{chooseB.css('display','block');
		//chooseA.css('display','none');}
						   
						   });*/
    imgbtn.click(function(){
						//  alert(1)
        var ul=$(this).parent("li").next("ul");
        if(ul.css("display")=="none"){
            //$(this).attr("src","images/up1.png");
            ul.css("display","block");
            if(ul.attr("id")=="ul1"){
                $("#ul2").css("display","none");
            }
            if(ul.attr("id")=="ul2"){
                $("#ul1").css("display","none");
            }

        }
        else{
            //$(this).attr("src","images/down1.png");
            ul.css("display","none");
        }
    });
});
//左侧栏控制
/*
$(function(){
    var h=$(".left").css("height");
    var titleH=$(".left>h3").css("height");
    var liH=$("#prosort>li").css("height");
    var num=$("#prosort>li").length;
    var ulH=parseFloat(h)-parseFloat(titleH)-parseFloat(liH)*num-17-num*5;

    $("#ul1").css("height",ulH+"px");
    $("#ul2").css("height",ulH+"px");
    document.getElementById("#ul1").setAttribute("height",ulH+"px");
    document.getElementById("#ul2").setAttribute("height",ulH+"px");
});
*/
//实现复选框全选
$(function(){
    var checkbox=$("#checkbox");
    checkbox.bind("click",function() {
            checkbox.value = checkbox.value == 1 ? 2 : 1;
            var checkboxs = document.getElementsByName("same");
            for (var i = 0; i < checkboxs.length; i++) {
                if (checkbox.value == 2) {
                    checkboxs[i].checked = false;//全不选
                } else {
                    checkboxs[i].checked = true;//全选
                }
            }
        }
        );
    });






