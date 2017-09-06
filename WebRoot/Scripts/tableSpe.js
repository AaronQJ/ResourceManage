

$(function(){
	$.ajax({
		type:"POST",
		url:"spepro/speAction_sendChart",
		data:{},
		success:function(data)
		{
			$.ajax({
				type:"POST",
				url:"spepro/speAction_sendChart",
				async: false,
				data:{},
				success:function(data)
				{
					var json =JSON.parse(data);			
					var Router = parseInt(json[0].路由器);
					var Switch=parseInt(json[0].交换机);
					var Server=parseInt(json[0].服务器);
					var Other=parseInt(json[0].其他);
					var Count=parseInt(json[1].总数);
					var maxNum=Math.max(Router, Switch, Server, Other);
					var MaxYA = Math.ceil(maxNum/10)*10;
					var stepA = Math.ceil(MaxYA/4);   					//纵坐标步长
					
					while( (stepA%10) != 0 && typeof(stepA)=='number' && !(isNaN(stepA)) )
					{
						stepA++;
					}
					
					//设备类型
					 Highcharts.setOptions({
					        colors: ['#F7A35C','#F45B5B','#7CB5EC','#F15C80','#90ED7D', '#8085E9', '#8D0100', '#5C5C61', '#0D233A', '#8BBC22', '#64E572', '#ED561B', '#DDDF00', '#24CBE5', '#FF9655', '#FFF263', '#6AF9C4','#50B432']
					    });
					    $('#equType').highcharts({                  	//图表展示容器
					        chart: {
					            type: 'column'                          //指定图表的类型
					        },
					        title: {
					            text: '设备类型'      						//指定图表标题
					        },
					        xAxis: {
					            categories: ['交换机', '路由器', '服务器', '其他']   //指定x轴分组
								
					        },
					        yAxis: {
					            title: {
					                text: '设备数量'                  		//指定y轴的标题
					            },
								tickPositions: [0, stepA, stepA*1, stepA*2, stepA*3, stepA*4]
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
					        series: [{                                  //指定数据列
					            name: '设备类型',                          //数据列名
					            data: [Switch, Router, Server, Other]   //数据
					        }]                        
					    });
					    					    
					    //设备状态
					    var inDimension = parseInt(json[1].在维);
					    var outDimension = parseInt(json[1].不在维);
					    var offline = parseInt(json[1].下线);
					    var maxNumB=Math.max( inDimension, outDimension, offline );
						var MaxYB = Math.ceil(maxNumB/10)*10;
						var stepB = Math.ceil(MaxYB/4);					//纵坐标步长

						while( (stepB%10) != 0 && typeof(stepB)=='number' && !(isNaN(stepB)) )
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
					            categories: ['在维', '不在维', '下线']  		//指定x轴分组
								
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
					        series: [{                                     //指定数据列
					            name: '设备状态',                            //数据列名
					            data: [inDimension, outDimension, offline]//数据
					        }]                        
					    });
						
						//设备位置
						var equRoomCount = parseInt(json[1].机房总数)
						var equSiteArray=new Array()
						for(var i=0; i<equRoomCount; i++)
							{
								equSiteArray[i]="机房"+(i+1);							 
							}
						var widthLength=equRoomCount*100;
						$("#equSite").width(widthLength)
						var equData=new Array();
						var MaxYC=0;
						for(var i=0; i<equRoomCount; i++)
							{
								equData[i]=parseInt(json[3][i]);
								if(parseInt(json[3][i])>MaxYC)
									{
									MaxYC=parseInt(json[3][i]);
									}
							}
						var stepC = Math.ceil(MaxYC/4);					//纵坐标步长
					
						while( (stepC%10) != 0 && typeof(stepC)=='number' && !(isNaN(stepC)) )
						{
							stepC++;
						}
						for(var i=0; i<equRoomCount; i++)
							{
								var tempTr="<tr>"
											+"<td>机房"+(i+1)+"</td>"
											+"<td>"+json[2][i]+"</td>"
											+"</tr>";
								$(tempTr).appendTo($("#roomTbody"));
							}
							
						 $('#equSite').highcharts({                     //图表展示容器
					        chart: {
					            type: 'column'                          //指定图表的类型
					        },
					        title: {
					            text: '设备位置'      							//指定图表标题
					        },
					        xAxis: {
					            categories: equSiteArray   				//指定x轴分组								
					        },
					        yAxis: {
					            title: {
					                text: '设备数量'                		//指定y轴的标题
					            },
								tickPositions: [0, stepC*1, stepC*2, stepC*3, stepC*4]
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
					            name: '设备位置',                         //数据列名
					            data: equData				 			//数据
					        }]                        
					    });					
				},
				error:function()
				{
					alert("获取数据失败！请重试");
				}									
			});			
		},
		error:function()
		{
			alert("失败");
		}
	});
});









