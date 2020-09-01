		var chart = Highcharts.chart('con',{
		chart: {
			type: 'area'
		},
		title: {
			text: '美苏核武器库存量'
		},
		subtitle: {
			text: null
		},
		xAxis: {
			allowDecimals: false
		},
		yAxis: {
			title: {
				text: '&nbsp;'
			},
			labels: {
				formatter: function () {
					return this.value / 1000 + 'k';
				}
			}
		},
		tooltip: {
			pointFormat: '{series.name} 制造 <b>{point.y:,.0f}</b>枚弹头'
		},
		plotOptions: {
			area: {
				pointStart: 1940,
				marker: {
					enabled: false,
					symbol: 'circle',
					radius: 2,
					states: {
						hover: {
							enabled: true
						}
					}
				}
			}
		},
		series: [{
			name: null,
			data: [null, null, null, null, null, 6, 11, 32, 110, 235, 369, 640,
				   1005, 1436, 2063, 3057, 4618, 6444, 9822, 15468, 20434, 24126,
				   27387, 29459, 31056, 31982, 32040, 31233, 29224, 27342, 26662,
				   26956, 27912, 28999, 28965, 27826, 25579, 25722, 24826, 24605,
				   24304, 23464, 23708, 24099, 24357, 24237, 24401, 24344, 23586,
				   22380, 21004, 17287, 14747, 13076, 12555, 12144, 11009, 10950,
				   10871, 10824, 10577, 10527, 10475, 10421, 10358, 10295, 10104]
		}]
	});

	  //时间格式化
	  Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) {
	        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    }
	    for (var k in o)
	        if (new RegExp("(" + k + ")").test(fmt)) 
	            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
 	  }

	  



		Highcharts.setOptions({
			global: {
				useUTC: false
			}
		});
		function activeLastPointToolip(chart) {
			var points = chart.series[0].points;
			chart.tooltip.refresh(points[points.length -1]);
		}
		

		var chart_T6沉淀池cod = Highcharts.chart('T6沉淀池cod含量曲线图', {
			chart: {
				type: 'spline',
				backgroundColor: 'rgba(0,0,0,0)',
				marginRight: 10,
				events: {
					load: function () {
						var series = this.series[0],
							chart = this;
						activeLastPointToolip(chart);
						setInterval(function () {
							var x = (new Date()).getTime(), // 当前时间
								y = T6沉淀池cod;          // 随机值
							series.addPoint([x, y], true, true);
							activeLastPointToolip(chart);
						}, 1000);
					}
				}
			},
			credits: {
			    enabled:false
			},
			exporting:{ 
				enabled:false
            },
			title: {
				text: null
			},
			xAxis: {
				labels: {
	                style: {
	                	color: '#FFF',//颜色
	                }
                },
				type: 'datetime',
				tickPixelInterval: 150
			},
			yAxis: {
				labels: {
	                style: {
	                	color: '#FFF',//颜色
	                }
                },
				title: {
					text: null
				}
                // tickPositions: [0, 0.4, 0.8] // 指定竖轴坐标点的值
			},
			plotOptions: {
                series: {
                    marker: {
                        radius: 3,  // 曲线点半径，默认是4
                        symbol: 'diamond' // 曲线点类型："circle", "square",
											// "diamond",
											// "triangle","triangle-down"，默认是"circle"
                    }
                }
            },
			tooltip: {
				formatter: function () {
					return '<b>' + this.series.name + '</b><br/>' +
						Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
						Highcharts.numberFormat(this.y, 2);
				}
			},
			legend: {
				enabled: false
			},
			series: [{
				color:'#1EBFAE',
				name: 'T6沉淀池cod',
				data: (function () {
					// 生成随机值
					var data = [],
						time = (new Date()).getTime(),
						i;
					for (i = -19; i <= 0; i += 1) {
						data.push({
							x: time + i * 1000,
							y: T6沉淀池cod
						});
					}
					return data;
				}())
			}]
		});
		
		var chart_T6沉淀池铜 = Highcharts.chart('T6沉淀池铜含量曲线图', {
			chart: {
				type: 'spline',
				backgroundColor: 'rgba(0,0,0,0)',
				marginRight: 10,
				events: {
					load: function () {
						var series = this.series[0],
							chart = this;
						activeLastPointToolip(chart);
						setInterval(function () {
							var x = (new Date()).getTime(), // 当前时间
								y = T6沉淀池铜;          // 随机值
							series.addPoint([x, y], true, true);
							activeLastPointToolip(chart);
						}, 1000);
					}
				}
			},
			credits: {
			    enabled:false
			},
			exporting:{ 
				enabled:false
            },
			title: {
				text: null
			},
			xAxis: {
				labels: {
	                style: {
	                	color: '#FFF',//颜色
	                }
                },
				type: 'datetime',
				tickPixelInterval: 150
			},
			yAxis: {
				labels: {
	                style: {
	                	color: '#FFF',//颜色
	                }
                },
				title: {
					text: null
				},
				tickPixelInterval:144
                // tickPositions: [0, 0.4, 0.8] // 指定竖轴坐标点的值
			},
			plotOptions: {
                series: {
                    marker: {
                        radius: 3,  // 曲线点半径，默认是4
                        symbol: 'diamond' // 曲线点类型："circle", "square",
											// "diamond",
											// "triangle","triangle-down"，默认是"circle"
                    }
                }
            },
			tooltip: {
				formatter: function () {
					return '<b>' + this.series.name + '</b><br/>' +
						Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
						Highcharts.numberFormat(this.y, 2);
				}
			},
			legend: {
				enabled: false
			},
			series: [{
				color:'#1EBFAE',
				name: 'T6沉淀池铜',
				data: (function () {
					// 生成随机值
					var data = [],
						time = (new Date()).getTime(),
						i;
					for (i = -19; i <= 0; i += 1) {
						data.push({
							x: time + i * 1000,
							y: T6沉淀池铜
						});
					}
					return data;
				}())
			}]
		});
		

		var chart_T6沉淀池氨氮 = Highcharts.chart('T6沉淀池氨氮含量曲线图', {
			chart: {
				type: 'spline',
				backgroundColor: 'rgba(0,0,0,0)',
				marginRight: 10,
				events: {
					load: function () {
						var series = this.series[0],
							chart = this;
						activeLastPointToolip(chart);
						setInterval(function () {
							var x = (new Date()).getTime(), // 当前时间
								y = T6沉淀池氨氮;          // 随机值
							series.addPoint([x, y], true, true);
							activeLastPointToolip(chart);
						}, 1000);
					}
				}
			},
			credits: {
			    enabled:false
			},
			exporting:{ 
				enabled:false
            },
			title: {
				text: null
			},
			xAxis: {
				labels: {
	                style: {
	                	color: '#FFF',//颜色
	                }
                },
				type: 'datetime',
				tickPixelInterval: 150
			},
			yAxis: {
				labels: {
	                style: {
	                	color: '#FFF',//颜色
	                }
                },
				title: {
					text: null
				}
                // tickPositions: [0, 0.4, 0.8] // 指定竖轴坐标点的值
			},
			plotOptions: {
                series: {
                    marker: {
                        radius: 3,  // 曲线点半径，默认是4
                        symbol: 'diamond' // 曲线点类型："circle", "square",
											// "diamond",
											// "triangle","triangle-down"，默认是"circle"
                    }
                }
            },
			tooltip: {
				formatter: function () {
					return '<b>' + this.series.name + '</b><br/>' +
						Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
						Highcharts.numberFormat(this.y, 2);
				}
			},
			legend: {
				enabled: false
			},
			series: [{
				color:'#1EBFAE',
				name: 'T6沉淀池氨氮',
				data: (function () {
					// 生成随机值
					var data = [],
						time = (new Date()).getTime(),
						i;
					for (i = -19; i <= 0; i += 1) {
						data.push({
							x: time + i * 1000,
							y: T6沉淀池氨氮
						});
					}
					return data;
				}())
			}]
		});
		
		var chart_生化池cod8C = Highcharts.chart('8C生化池cod含量曲线图', {
			chart: {
				type: 'spline',
				backgroundColor: 'rgba(0,0,0,0)',
				marginRight: 10,
				events: {
					load: function () {
						var series = this.series[0],
							chart = this;
						activeLastPointToolip(chart);
						setInterval(function () {
							var x = (new Date()).getTime(), // 当前时间
								y = 生化池cod8C;          // 随机值
							series.addPoint([x, y], true, true);
							activeLastPointToolip(chart);
						}, 1000);
					}
				}
			},
			credits: {
			    enabled:false
			},
			exporting:{ 
				enabled:false
            },
			title: {
				text: null
			},
			xAxis: {
				labels: {
	                style: {
	                	color: '#FFF',//颜色
	                }
                },
				type: 'datetime',
				tickPixelInterval: 150
			},
			yAxis: {
				labels: {
	                style: {
	                	color: '#FFF',//颜色
	                }
                },
				title: {
					text: null
				},
				tickPixelInterval:184
				// tickPositions: [0, 0.4, 0.8] // 指定竖轴坐标点的值
			},
			plotOptions: {
                series: {
                    marker: {
                        radius: 3,  // 曲线点半径，默认是4
                        symbol: 'diamond' // 曲线点类型："circle", "square",
											// "diamond",
											// "triangle","triangle-down"，默认是"circle"
                    }
                }
            },
			tooltip: {
				formatter: function () {
					return '<b>' + this.series.name + '</b><br/>' +
						Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
						Highcharts.numberFormat(this.y, 2);
				}
			},
			legend: {
				enabled: false
			},
			series: [{
				color:'#1EBFAE',
				name: '8C生化池cod',
				data: (function () {
					// 生成随机值
					var data = [],
						time = (new Date()).getTime(),
						i;
					for (i = -19; i <= 0; i += 1) {
						data.push({
							x: time + i * 1000,
							y: 生化池cod8C
						});
					}
					return data;
				}())
			}]
		});
		
		var chart_生化池氨氮8C = Highcharts.chart('8C生化池氨氮含量曲线图', {
			chart: {
				type: 'spline',
				backgroundColor: 'rgba(255, 255, 255, 0)',
				marginRight: 10,
				events: {
					load: function () {
						var series = this.series[0],
							chart = this;
						activeLastPointToolip(chart);
						setInterval(function () {
							var x = (new Date()).getTime(), // 当前时间
								y = 生化池氨氮8C;          // 随机值
							series.addPoint([x, y], true, true);
							activeLastPointToolip(chart);
						}, 1000);
					}
				}
			},
			credits: {
			    enabled:false
			},
			exporting:{ 
				enabled:false
            },
			title: {
				text: null
			},
			xAxis: {
				labels: {
	                style: {
	                	color: '#FFF',//颜色
	                }
                },
				type: 'datetime',
				tickPixelInterval: 150
			},
			yAxis: {
				labels: {
	                style: {
	                	color: '#FFF',//颜色
	                }
                },
				title: {
					text: null
				},
				/*
				 * labels: { formatter: function() { nums +=
				 * this.value.toFixed(1) + ",";
				 * if(parseFloat(this.value.toFixed(1))){ } alert(nums); return
				 * null;//这里是两位小数，你要几位小数就改成几 } }
				 */
                tickPixelInterval:144
				// tickPositions: [0, 0.4, 0.8] // 指定竖轴坐标点的值
			},
			plotOptions: {
                series: {
                    marker: {
                        radius: 3,  // 曲线点半径，默认是4
                        symbol: 'diamond' // 曲线点类型："circle", "square",
											// "diamond",
											// "triangle","triangle-down"，默认是"circle"
                    }
                }
            },
			tooltip: {
				formatter: function () {
					return '<b>' + this.series.name + '</b><br/>' +
						Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
						Highcharts.numberFormat(this.y, 2);
				}
			},
			legend: {
				enabled: false
			},
			series: [{
				color:'#1EBFAE',
				name: '8C生化池氨氮',
				data: (function () {
					// 生成随机值
					var data = [],
						time = (new Date()).getTime(),
						i;
					for (i = -19; i <= 0; i += 1) {
						data.push({
							x: time + i * 1000,
							y: 生化池氨氮8C
						});
					}
					return data;
				}())
			}]
		});
		
		var chart_T3沉淀池铜 = Highcharts.chart('T3沉淀池铜含量曲线图', {
			chart: {
				type: 'spline',
				backgroundColor: 'rgba(0,0,0,0)',
				marginRight: 10,
				events: {
					load: function () {
						var series = this.series[0],
							chart = this;
						activeLastPointToolip(chart);
						setInterval(function () {
							var x = (new Date()).getTime(), // 当前时间
								y = T3沉淀池铜;          // 随机值
							series.addPoint([x, y], true, true);
							activeLastPointToolip(chart);
						}, 1000);
					}
				}
			},
			credits: {
			    enabled:false
			},
			exporting:{ 
				enabled:false
            },
			title: {
				text: null
			},
			xAxis: {
				labels: {
	                style: {
	                	color: '#FFF',//颜色
	                }
                },
				type: 'datetime',
				tickPixelInterval: 150
			},
			yAxis: {
				labels: {
	                style: {
	                	color: '#FFF',//颜色
	                }
                },
				title: {
					text: null
				},
                tickPixelInterval:144
				// tickPositions: [0, 0.4, 0.8] // 指定竖轴坐标点的值
			},
			plotOptions: {
                series: {
                    marker: {
                        radius: 3,  // 曲线点半径，默认是4
                        symbol: 'diamond' // 曲线点类型："circle", "square",
											// "diamond",
											// "triangle","triangle-down"，默认是"circle"
                    }
                }
            },
			tooltip: {
				formatter: function () {
					return '<b>' + this.series.name + '</b><br/>' +
						Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
						Highcharts.numberFormat(this.y, 2);
				}
			},
			legend: {
				enabled: false
			},
			series: [{
				color:'#1EBFAE',
				name: 'T3沉淀池铜',
				data: (function () {
					// 生成随机值
					var data = [],
						time = (new Date()).getTime(),
						i;
					for (i = -19; i <= 0; i += 1) {
						data.push({
							x: time + i * 1000,
							y: T3沉淀池铜
						});
					}
					return data;
				}())
			}]
		});
		
		
		var 综合池铜 = 0;
		var 有机池cod = 0;
		var T6沉淀池cod = 0;
		var T6沉淀池铜 = 0;
		var T6沉淀池氨氮 = 0;
		
		var 生化池cod8C = 0;
		var 生化池氨氮8C = 0;
		var T3沉淀池铜 = 0;
		
		
		
		function getDataIngToBomin() {
			$.post("../../../shenZhenBoMin/data/index/getDataIngToBomin", function(data) {
	  	      	for(var key in data.numbers)  {
	  	      		
	  	      		if(key.indexOf("累计") != -1){
	  	      			$("#" + key).html((data.numbers[key]).toFixed(1) + "&nbsp;&nbsp;<span style='font-size:13px;'>m³</span>");
	  	      		} else {
	  	      			$("#" + key).html((data.numbers[key]).toFixed(1));
	  	      		}
		  	      	if(key.indexOf("即时") != -1){
			  	      	if(data.numbers[key] <= 0){
		  	      			$("#" + key).html("0.0" + "&nbsp;&nbsp;<span style='font-size:13px;'>m³/min</span>");
		  	      		}else{
		  	      			$("#" + key).html((data.numbers[key]).toFixed(1) + "&nbsp;&nbsp;<span style='font-size:13px;'>m³/min</span>");
	  	      			}
		  	      		
	  	      		}
	  	      	}
	  	      	
	  	      	var sum = 0;
	  			
	  	      	
	  	      	
	  	      	$("#有机池cod含量原值").html(data.szbmRealdata.有机池COD原值);
	  	      	$("#综合池含铜量原值").html(data.szbmRealdata.综合池铜含量原值);
	  	      	
	  	      	$("#有机水cod").html(data.szbmRealdata.有机池COD原值 / parseFloat($("#有机cod除数").val()) + "&nbsp;&nbsp;<span style='font-size:13px;'>mg/l</span>");
	  	      	有机池cod = parseFloat($("#有机水cod").html());
	  	      	$("#综合铜").html(data.szbmRealdata.综合池铜含量原值 / parseFloat($("#综合铜除数").val()) + "&nbsp;&nbsp;<span style='font-size:13px;'>mg/l</span>");
	  	      	综合池铜 = parseFloat($("#综合铜").html());
	  	      	$("#芬顿ORP值").html(data.szbmRealdata.芬顿ORP原值 / 100 + "&nbsp;&nbsp;<span style='font-size:13px;'>mg/l</span>");
	  	      	sum = (data.szbmRealdata.排放PH观察原值 - 10000) * 0.00035;
	  	      	if(sum > 0){
					$("#排放PH观察值").html((sum).toFixed(2));
				} else if(sum < 0){
					$("#排放PH观察值").html('0');
				} else if(sum < 6.8 || sum > 8.5){
					$("#排放PH观察值").css("color","#DD5145");//清水池铜
				}
	  	      	
	  	      	
	  	        sum = (data.szbmRealdata.催化加酸PH原值 - 10000) * 0.00035;
	  	      	if(sum > 0){
					$("#催化加酸PH值").html((sum).toFixed(2));
				} else if(sum < 0){
					$("#催化加酸PH值").html('0');
				} else if(sum < 6.5 || sum > 7){
					$("#催化加酸PH值").css("color","#DD5145");//清水池铜
				}
	  	        
	  	        
	  	        
	  	        sum = (data.szbmRealdata.络合加碱PH原值 - 10000) * 0.00035;
	  	      	if(sum > 0){
					$("#络合加碱PH值").html((sum).toFixed(2));
				} else if(sum < 0){
					$("#络合加碱PH值").html('0');
				} else if(sum < 7 || sum > 10){
					$("#络合加碱PH值").css("color","#DD5145");//清水池铜
				}
	  	        
	  	        
	  	        //$("#络合硫化钠ORP值").html(((data.szbmRealdata.络合硫化钠ORP原值 - 10000) * 0.00035).toFixed(2));
	  	        sum = (data.szbmRealdata.络合硫化钠ORP原值 - 10000) * 0.00035;
	  	      	if(sum > 0){
					$("#络合硫化钠ORP值").html((sum).toFixed(2));
				} else if(sum < 0){
					$("#络合硫化钠ORP值").html('0');
				}
	  	        
	  	        //$("#络合加碱PH值").html(((data.szbmRealdata.络合加碱PH原值 - 10000) * 0.00035).toFixed(2));
	  	        sum = (data.szbmRealdata.络合加碱PH原值 - 10000) * 0.00035;
	  	      	if(sum > 0){
					$("#络合加碱PH值").html((sum).toFixed(2));
				} else if(sum < 0){
					$("#络合加碱PH值").html('0');
				}
	  	      	
	  	        //$("#脱氮加碱PH值高位").html(((data.szbmRealdata.脱氮加碱PH原值高位 - 10000) * 0.00035).toFixed(2));
	  	        sum = (data.szbmRealdata.脱氮加碱PH原值高位 - 10000) * 0.00035;
	  	      	if(sum > 0){
					$("#脱氮加碱PH值高位").html((sum).toFixed(2));
				} else if(sum < 0){
					$("#脱氮加碱PH值高位").html('0');
				}
	  	      	
	  	      	
	  	        //$("#脱氮加酸PH值高位").html(((data.szbmRealdata.脱氮加酸PH原值高位 - 10000) * 0.00035).toFixed(2));
	  	        sum = (data.szbmRealdata.脱氮加酸PH原值高位 - 10000) * 0.00035;
	  	      	if(sum > 0){
					$("#脱氮加酸PH值高位").html((sum).toFixed(2));
				} else if(sum < 0){
					$("#脱氮加酸PH值高位").html('0');
				}
	  	      	
	  	        //$("#脱氮剂ORP值高位").html(data.szbmRealdata.脱氮剂ORP原值高位 / 100 + "&nbsp;&nbsp;<span style='font-size:13px;'>mg/l</span>");
	  	        sum = (data.szbmRealdata.脱氮剂ORP原值高位 - 10000) * 0.00035;
	  	      	if(sum > 0){
					$("#脱氮剂ORP值高位").html((sum).toFixed(2) + "&nbsp;&nbsp;<span style='font-size:13px;'>mg/l</span>");
				} else if(sum < 0){
					$("#脱氮剂ORP值高位").html('0');
				}
	  	      	
	  	        //$("#脱氮加碱PH值低位").html(((data.szbmRealdata.脱氮加碱PH原值低位 - 10000) * 0.00035).toFixed(2));
	  	        sum = (data.szbmRealdata.脱氮加碱PH原值低位 - 10000) * 0.00035;
	  	      	if(sum > 0){
					$("#脱氮加碱PH值低位").html((sum).toFixed(2) + "&nbsp;&nbsp;<span style='font-size:13px;'>mg/l</span>");
				} else if(sum < 0){
					$("#脱氮加碱PH值低位").html('0');
				}
	  	      	
	  	        //$("#脱氮加酸PH值低位").html(((data.szbmRealdata.脱氮加酸PH原值低位 - 10000) * 0.00035).toFixed(2));
	  	        sum = (data.szbmRealdata.脱氮加酸PH原值低位 - 10000) * 0.00035;
	  	      	if(sum > 0){
					$("#脱氮加酸PH值低位").html((sum).toFixed(2));
				} else if(sum < 0){
					$("#脱氮加酸PH值低位").html('0');
				}
	  	        
	  	        //$("#脱氮剂ORP值低位").html(data.szbmRealdata.脱氮剂ORP原值低位 / 100 + "&nbsp;&nbsp;<span style='font-size:13px;'>mg/l</span>");
	  	        sum = (data.szbmRealdata.脱氮剂ORP原值低位 - 10000) * 0.00035;
	  	      	if(sum > 0){
					$("#脱氮剂ORP值低位").html((sum).toFixed(2) + "&nbsp;&nbsp;<span style='font-size:13px;'>mg/l</span>");
				} else if(sum < 0){
					$("#脱氮剂ORP值低位").html('0');
				}
	  	      	
	  	      	
	  	        sum = (data.szbmRealdata.有机加石灰PH原值 - 10000) * 0.00035;
	  	      	if(sum > 0){
					$("#有机加石灰PH值").html((sum).toFixed(2));
				} else if(sum < 0){
					$("#有机加石灰PH值").html('0');
				} else if(sum < 7 || sum > 11){
					$("#有机加石灰PH值").css("color","#DD5145");//清水池铜
				}
	  	      	
	  	        sum = (data.szbmRealdata.综合加碱PH原值 - 10000) * 0.00035;
	  	      	if(sum > 0){
					$("#综合加碱PH值").html((sum).toFixed(2));
				} else if(sum < 0){
					$("#综合加碱PH值").html('0');
				} else if(sum < 7 || sum > 10){
					$("#综合加碱PH值").css("color","#DD5145");//清水池铜
				}
	  	      	
	  	      	sum = data.HTBMA.cod;
	  	      	if(sum >= 0){
	  	      		T6沉淀池cod = sum;
					$("#T6沉淀池cod含量").html((sum).toFixed(2) + "&nbsp;mg/l");
				} else if(sum < 0){
					$("#T6沉淀池cod含量").html('0.00&nbsp;mg/l');
				}
	  	      	
	  	      	sum = data.HTBMCU1.result;
	  	      	if(sum >= 0){
	  	      		T6沉淀池铜 = sum;
					$("#T6沉淀池铜含量").html((sum).toFixed(2) + "&nbsp;mg/l");
				} else if(sum < 0){
					$("#T6沉淀池铜含量").html('0.00&nbsp;mg/l');
				}
	  	      	
	  	      	sum = data.HTBMA.nh3;
	  	      	if(sum >= 0){
	  	      		T6沉淀池氨氮 = sum;
					$("#T6沉淀池氨氮含量").html((sum).toFixed(2) + "&nbsp;mg/l");
				} else if(sum < 0){
					$("#T6沉淀池氨氮含量").html('0.00&nbsp;mg/l');
				}
	  	      	
	  	      	sum = data.HTBMB.cod;//data.HTBMA.cod;
	  	      	if(sum >= 0){
	  	      		生化池cod8C = sum;
					$("#8C生化池cod含量").html((sum).toFixed(2) + "&nbsp;mg/l");
				} else if(sum < 0){
					$("#8C生化池cod含量").html('0.00&nbsp;mg/l');
				}
	  	      	
	  	      	sum = data.HTBMB.nh3;
	  	      	if(sum >= 0){
	  	      		生化池氨氮8C = sum;
					$("#8C生化池氨氮含量").html((sum).toFixed(2) + "&nbsp;mg/l");
				} else if(sum < 0){
					$("#8C生化池氨氮含量").html('0.00&nbsp;mg/l');
				}
	  	      	
	  	      	sum = data.HTBMCU2.result;
	  	      	if(sum >= 0){
	  	      		T3沉淀池铜 = sum;
					$("#T3沉淀池铜含量").html((sum).toFixed(2) + "&nbsp;mg/l");
				} else if(sum < 0){
					$("#T3沉淀池铜含量").html('0.00&nbsp;mg/l');
				}
	  	      	
	  	        sum = (data.szbmRealdata.镍机入水原值) / 125;
	  	      	if(sum >= 0){
					$("#镍机入水量").html("设备维护中");//(sum).toFixed(2) + "&nbsp;mg/l");
				} else if(sum < 0){
					$("#镍机入水量").html('0.00&nbsp;mg/l');
				}
	  	      	
	  	      	sum = (data.HTBMNI.result) / 125;
	  	      	if(sum >= 0){
					$("#镍机出水量").html("设备维护中");//(sum).toFixed(2) + "&nbsp;mg/l");
				} else if(sum < 0){
					$("#镍机出水量").html('0.00&nbsp;mg/l');
				}
	  	      	
	  	      	sum = (data.NH3OUT.nh3);
	  	      	if(sum >= 0){
					$("#氨氮机出水量").html("设备维护中");//(sum).toFixed(2) + "&nbsp;mg/l");
				} else if(sum < 0){
					$("#氨氮机出水量").html('0.00&nbsp;mg/l');
				}
	  	      	
	  	      	sum = (data.NH3IN.nh3);
	  	      	if(sum >= 0){
					$("#氨氮机入水量").html("设备维护中");//(sum).toFixed(2) + "&nbsp;mg/l"
				} else if(sum < 0){
					$("#氨氮机入水量").html('0.00&nbsp;mg/l');
				}
			}, "json");
		}
	
		
		setInterval(function() {
			//getDataIngToBomin();
			findDataToJTRealdata();
		}, 3000);
		
		function random(lowerValue, upperValue) {
			return (Math.random() * (upperValue - lowerValue) + lowerValue).toFixed(1);
		}
		
		$.post("../../../user/getUserInfo", function(data) {
			if(data.LOGIN_USER){
				$("#username").html(data.LOGIN_USER.realName);
			} else {
				location.href = "../../../../templates/login-old.html";
			}
		}, "json")
		
		function getDateWeek() {
			todayDate = new Date();
			date = todayDate.getDate();
			month = todayDate.getMonth() + 1;
			year = todayDate.getYear();
			var dateweek = "";

			switch (todayDate.getDay()) {
			case 0:
				dateweek += "星期日";
				break;
			case 1:
				dateweek += "星期一";
				break;
			case 2:
				dateweek += "星期二";
				break;
			case 3:
				dateweek += "星期三";
				break;
			case 4:
				dateweek += "星期四";
				break;
			case 5:
				dateweek += "星期五";
				break;
			case 6:
				dateweek += "星期六";
				break;
			}
			dateweek += "<br>";
			if (navigator.appName == "Netscape") {
				dateweek = dateweek + (1900 + year) + "年" + month + "月" + date
						+ "日";
			}
			if (navigator.appVersion.indexOf("MSIE") != -1) {
				dateweek = dateweek + year + "年" + month + "月" + date + "日";
			}

			return dateweek;
		}
		function setTime() {
			setTimeout(function() {
				var d = new Date();
				var hours = d.getHours();
				var minutes = d.getMinutes();
				var seconds = d.getSeconds();

				var hoursStr = "";
				var minutesStr = "";
				var secondsStr = "";
				if (hours < 10) {
					hoursStr = "0" + hours;
				} else {
					hoursStr = hours;
				}
				if (minutes < 10) {
					minutesStr = "0" + minutes;
				} else {
					minutesStr = minutes;
				}
				if (seconds < 10) {
					secondsStr = "0" + seconds;
				} else {
					secondsStr = seconds;
				}
				$("#time").html(
						'<span style="font-size: 40px">' + hoursStr + ':'
								+ minutesStr
								+ '&nbsp;<span style="font-size: 15px">'
								+ secondsStr + '</span>' + '</span>');
				$("#time2").html(getDateWeek());
				setTime();
			}, 1000);
		}
		setTime();
		
		
		var info = { '综合水池容量': '1200.00', '有机水池容量': '240.00' }
		for(var key in info)  {
	      	$("#" + key).html(info[key] + "&nbsp;(立方米)");
	    }
		function findDataToJTRealdata(){
			layui.use(['element', 'carousel', 'form'], function(){
				  var $ = layui.jquery, element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
				  
			      $.post("../../../jianTai/data/index/findDataToJTRealdata", function(data) {
			    	  if(((data.jtRealdata.综合水池液位原值 - 10000) * 0.05 / 1000 + 2) / 4 * parseFloat($("#综合水池容量").html()) > parseFloat($("#综合水池容量").html())){
			    		  $("#综合水池水量").html(info["综合水池容量"] + "&nbsp;(立方米)");
			    	  } else {
			    		  $("#综合水池水量").html((((data.jtRealdata.综合水池液位原值 - 10000) * 0.05 / 1000 + 2) / 4 * parseFloat($("#综合水池容量").html())).toFixed(2) + "&nbsp;(立方米)");
			    	  }
			    	  $("#综合水池余量").html((parseFloat($("#综合水池容量").html()) - parseFloat($("#综合水池水量").html())).toFixed(2) + "&nbsp;(立方米)");
			    	  //element.progress('demo',  '51%');
				      $('#demo').attr('lay-percent',parseFloat(parseFloat($("#综合水池水量").html()) / parseFloat($("#综合水池容量").html()) * 100).toFixed(0) + '%');
				      element.init();
			      })
			      
			});
		}