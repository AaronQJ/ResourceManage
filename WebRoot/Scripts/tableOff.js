


$(function () {
	
	$.ajax({
		type:"POST",
		url:"offpro/offAction_sendChart",
		async: false,
		data:{},
		success:function(data)
		{
			var json =JSON.parse(data);
			var printer = json[0].打印机;
			var computer=json[0].电脑;
			var others=json[0].其他;
			var maxNumA=Math.max(printer, computer, others);
			var MaxYA = Math.ceil(maxNumA/10)*10;			
			var stepA = Math.ceil(MaxYA/4);						//纵坐标步长
			while( (stepA%10) != 0 && typeof(stepA)=='number' && !(isNaN(stepA)))
			{
				stepA++;
			}			
			Highcharts.setOptions({
		        colors: ['#F7A35C','#F45B5B','#7CB5EC','#F15C80','#90ED7D', '#8085E9', '#8D0100', '#5C5C61', '#0D233A', '#8BBC22', '#64E572', '#ED561B', '#DDDF00', '#24CBE5', '#FF9655', '#FFF263', '#6AF9C4','#50B432']
		    });
			    $('#equType').highcharts({                   	//图表展示容器
			        chart: {
			            type: 'column'                          //指定图表的类型
			        },
			        title: {
			            text: '设备类型'      						//指定图表标题
			        },
			        xAxis: {
			            categories: ['打印机', '电脑', '其他']   	//指定x轴分组						
			        },
			        yAxis: {
			            title: {
			                text: '设备数量'                  		//指定y轴的标题
			            },
						tickPositions: [0, stepA*1, stepA*2, stepA*3, stepA*4]
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
			        series: [{                                 //指定数据列
			            name: '设备类型',                         //数据列名
			            data: [printer, computer, others]	   //数据
			        }]                        
			    });
			    var waitUse = json[1].待领用;
				var using=json[1].在用;
				var scraping=json[1].待报废;
				var scraped=json[1].报废;
				var maxNumB=Math.max(waitUse, using, scraping, scraped);
				var MaxYB = Math.ceil(maxNumB/10)*10;			
				var stepB = Math.ceil(MaxYB/4);					//纵坐标步长
				while( (stepB%10) != 0 && typeof(stepB)=='number' && !(isNaN(stepB)))
				{
					stepB++;
				}   			    
				$('#equState').highcharts({                     //图表展示容器
			        chart: {
			            type: 'column'                          //指定图表的类型
			        },
			        title: {
			            text: '设备状态'      						//指定图表标题
			        },
			        xAxis: {
			            categories: ['待领用', '在用', '待报废', '报废']//指定x轴分组
						
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
			        series: [{                                   //指定数据列
			            name: '设备状态',                           //数据列名
			            data: [waitUse, using, scraping, scraped]//数据
			        }]                        
			    });

		},
		error:function( )
		{
			alert("获取数据失败！请重试");
		}

	});
	
});