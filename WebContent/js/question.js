$(function(){
	/*alert("test"); */
	$('#button_answer').click(function(){
		var infodata = $('#infodata').val();
		console.log(infodata);
		$.ajax({
			url:"./question",		//访问路径
			type:"POST",						//访问方式
			dataType:"jsonp",
			data:{"infodata":infodata},
			jsonp: "jsonpCallback",//服务端用于接收callback调用的function名的参数
			success : function(Result){
				console.log(Result);
				$('#showcase').html(Result.answer);
			},
			error:function(){
				$('#showcase').html("查询失败，请尝试重新查询");
			}
		});
	});
});