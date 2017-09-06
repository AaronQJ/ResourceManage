function globalShade()
{
	//获取页面的高度和宽度
	
	if(window.parent.document.getElementById('mask'))
		{	
		
		window.parent.document.getElementById('mask').style.display="block";
	
		}
	if(document.getElementById("maskIframe"))
		{
			document.getElementById("maskIframe").style.display="block";
			
		
		}
	
		
};
function deleteGlobalShade()
{
	if(window.parent.document.getElementById('mask'))
	{	
	window.parent.document.getElementById('mask').style.display="none";

	}
if(document.getElementById("maskIframe"))
	{

		document.getElementById("maskIframe").style.display="none";
	
	}

};
/*$(function(){
	
	//var sWidth=document.body.scrollWidth;
	var sWidth=document.documentElement.clientWidth;
//	var sHeight=document.body.scrollHeight;
	var sHeight=document.documentElement.clientHeight;
	
	//获取页面的可视区域高度和宽度
	var wHeight=document.documentElement.clientHeight;
	
	var oMask=document.createElement("div");
		oMask.id="mask";
		oMask.style.height=sHeight+"px";
		oMask.style.width=sWidth+"px";
		document.body.appendChild(oMask);
		//alert(document.body.clientHeight);
	 /*   tds.bind("mouseover",function(){
	            if(parseFloat($(this).css("width"))>130){
	                $(this).prop("title",$(this).text());
	            }
	    })*/
//	});
$(function(){
	
	//var sWidth=document.body.scrollWidth;
	var sWidth=document.documentElement.clientWidth;
//	var sHeight=document.body.scrollHeight;
	var sHeight=document.documentElement.clientHeight;
	
	//获取页面的可视区域高度和宽度
	var wHeight=document.documentElement.clientHeight;

	var oMask=document.getElementById("mask");
	var oMaskIframe=document.getElementById("maskIframe");
	
		
		oMask.style.height=sHeight+"px";
		oMask.style.width=sWidth+"px";
		
	
		
		//alert(document.body.clientHeight);
	 /*   tds.bind("mouseover",function(){
	            if(parseFloat($(this).css("width"))>130){
	                $(this).prop("title",$(this).text());
	            }
	    })*/
	});