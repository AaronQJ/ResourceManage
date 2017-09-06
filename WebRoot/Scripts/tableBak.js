


$(function () {
	
	$.ajax({
		type:"POST",
		url:"probak/bakAction_sendChart",
		async: false,
		data:{},
		success:function(data)
		{
			var json =JSON.parse(data);
			Highcharts.setOptions({
		        colors: ['#F7A35C','#F45B5B','#7CB5EC','#F15C80','#90ED7D', '#8085E9', '#8D0100', '#5C5C61', '#0D233A', '#8BBC22', '#64E572', '#ED561B', '#DDDF00', '#24CBE5', '#FF9655', '#FFF263', '#6AF9C4','#50B432']
		    });	    			    
			    var inStore = parseInt(json[1].入库);
				var outStore=parseInt(json[1].出库);
				var scraping=parseInt(json[1].待报废);
				var scraped=parseInt(json[1].报废);
				var maxNumB=Math.max(inStore, outStore, scraping, scraped);
				var MaxYB = Math.ceil(maxNumB/10)*10;				
				var stepB = Math.ceil(MaxYB/4);					//纵坐标步长
				while( (stepB%10) != 0 && typeof(stepB)=='number' && !(isNaN(stepB)))
				{
					stepB++;
				}
				$('#equState').highcharts({                    //图表展示容器
			        chart: {
			            type: 'column'                         //指定图表的类型
			        },
			        title: {
			            text: '设备状态'      					   //指定图表标题
			        },
			        xAxis: {
			            categories: ['入库', '出库', '待报废', '报废']//指定x轴分组
						
			        },
			        yAxis: {
			            title: {
			                text: '设备数量'                  		//指定y轴的标题
			            },
						tickPositions: [0, stepB*1, stepB*2, stepB*3, stepB*4]
			        },
					plotOptions: {
			                    column:{
			                        dataLabels:{
			                            enabled:true, 			// dataLabels设为true
			                            style:{
			                                color:'#000'
			                            }
			                        }
			                    },
								 series: {
			                colorByPoint: true /////////////////////////////////
			            }
			      },
					exporting:{},
			        series: [{                                 			//指定数据列
			            name: '设备状态',                          		//数据列名
			            data: [inStore, outStore, scraping, scraped ]	//数据
			        }]                        
			    });
	
				//设备类型				
				var equTypeCount = parseInt(json[1].类型总数);
				var equTypeArray=new Array()
				for(var i=0; i<equTypeCount; i++)
					{
						equTypeArray[i]=json[2][i];							 
					}
				var widthLength=equTypeCount*100;
				$("#equType").width(widthLength)
				var equData=new Array();
				var MaxYB=0;
				for(var i=0; i<equTypeCount; i++)
					{
						equData[i]=parseInt(json[3][i]);
						if(parseInt(json[3][i])>MaxYB)
							{
							MaxYB=parseInt(json[3][i]);
							}
					}
				var stepB = Math.ceil(MaxYB/4);				//纵坐标步长
			
				while( (stepB%10) != 0 && typeof(stepB)=='number' && !(isNaN(stepB)) )
				{
					stepB++;
				}		
			    $('#equType').highcharts({                   //图表展示容器
			        chart: {
			            type: 'column'                       //指定图表的类型
			        },
			        title: {
			            text: ''     					 	 //指定图表标题
			        },
			        xAxis: {
			            categories: equTypeArray   			//指定x轴分组
						
			        },
			        yAxis: {
			            title: {
			                text: '设备数量'                  //指定y轴的标题
			            },
						tickPositions: [0, stepB*1, stepB*2, stepB*3, stepB*4]
			        },
					plotOptions: {
			                    column:{
			                        dataLabels:{
			                            enabled:true, 		// dataLabels设为true
			                            style:{
			                                color:'#000'
			                            }
			                        }
			                    },
								 series: {
			                colorByPoint: true /////////////////////////////////
			            }
			      },
					exporting:{},
			        series: [{                               //指定数据列
			            name: '设备类型',                       //数据列名
			            data: equData						 //数据
			        }]                        
			    });

		},
		error:function()
		{
			alert("获取数据失败！请重试");
		}
	});	
});