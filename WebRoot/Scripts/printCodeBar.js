 //打印条码
	 function printHTML(){  
        var bdhtml=window.document.body.innerHTML;//获取当前页的html代码    
        var sprnstr="<!--begin-->";//设置打印开始区域    
        var eprnstr="<!--end-->";//设置打印结束区域    
        var prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+sprnstr.length); //从开始代码向后取html    
        printhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));//从结束代码向前取html    
        printWindow = window.open();
        printWindow.document.write(printhtml);
        
        printWindow.print();
		printWindow.close();
       } 