$(function(){
	/*alert("test"); */
	$('#button_answer').click(function(){
		var infodata = $('#infodata').val();
		if(infodata == ""){
			$('#show_a').html("请输入题目！");
		}else{
			console.log(infodata);
			$.ajax({
				url:"./question",		//访问路径
				type:"POST",						//访问方式
				dataType:"jsonp",
				data:{"infodata":infodata},
				jsonp: "jsonpCallback",//服务端用于接收callback调用的function名的参数
				success : function(Result){
					console.log(Result);
					$('#show_q').html("问题：" + Result.question);
					$('#show_a').html("答案：" + Result.answer);
				},
				error:function(){
					$('#showcase').html("查询失败，请检查网络尝试重新查询");
				}
			});
		}	
	});
});