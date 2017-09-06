//������λ
function setPosition(obj){
    var width=parseFloat(obj.css("width"));
    var docWidth=parseFloat($("body").css("width"));
    var bigWidth=docWidth/0.85;
    var rightWidth=(bigWidth-width)/2;
    var leftWidth=rightWidth-bigWidth*0.15;
    if(leftWidth<0){
    	leftWidth=0;
    }
    var height=parseFloat(obj.css("height"));
    var docHeight=parseFloat($("body").css("height"));
    var bigHeight=docHeight/0.89;
    var bottomHeight=(bigHeight-height)/2;
    var topHeight=bottomHeight-bigHeight*0.11-60;
    if(topHeight<0){
    	topHeight=0;
    }
    
    obj.css("left",leftWidth+"px");
    obj.css("top",topHeight+"px");
}
