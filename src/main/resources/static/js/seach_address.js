/**
 * 郵便番号から住所を取得する.
 * 
 */
$(function(){
	$("#get_address_btn").on("click",function(){
		$.ajax({
			url:"https://zipcoda.net/api",
			dataType:"jsonp",
			data:{
				zipcode:$("#zipcode").val()
			},
			async:true
		}).done(function(data){
			console.dir(JSON.stringify(data));
			$("#address").val(data.items[0].pref+data.items[0].address);
		}).fail(function(XMLHttpRequest,textStatus,errorThrown){

			alert("正しい住所を得られませんでした。");
			console.log("XMLHttpRequest:"+XMLHttpRequest.status);
			console.log("textStatus    :"+textStatus);
			console.log("errorThrown   :"+errorThrown.message);			
		});
	});
});			