$(function(){
	var date = new Date();
	date.setHours(date.getHours()+3);
	var date2 = new Date(date.getFullYear(),date.getMonth(),date.getDate(),18,0,0);
	if(date > date2){
		var year = date.getFullYear();
		var month = ('0' + (date.getMonth()+1)).slice(-2);
		var day = ('0' + (date.getDate()+1)).slice(-2);
		$("#date").val(year + "-" + month + "-" + day);
		$("#ten").prop("checked",true);
	} else{
		var year = date.getFullYear();
		var month = ('0' + (date.getMonth()+1)).slice(-2);
		var day = ('0' + date.getDate()).slice(-2);
		$("#date").val(year + "-" + month + "-" + day);
		if(date.getHours()<10 && date.getMinutes() < 60){
			$("#ten").prop("checked",true);			
		} else if(date.getHours()<11 && date.getMinutes() < 60){
			$("#eleven").prop("checked",true);						
		} else if(date.getHours()<12 && date.getMinutes() < 60){
			$("#twelve").prop("checked",true);						
		} else if(date.getHours()<13 && date.getMinutes() < 60){
			$("#thirteen").prop("checked",true);						
		} else if(date.getHours()<14 && date.getMinutes() < 60){
			$("#fourteen").prop("checked",true);						
		} else if(date.getHours()<15&& date.getMinutes() < 60){
			$("#fifteen").prop("checked",true);						
		} else if(date.getHours()<16 && date.getMinutes() < 60){
			$("#sixteen").prop("checked",true);						
		} else if(date.getHours()<17 && date.getMinutes() < 60){
			$("#seventeen").prop("checked",true);						
		} else if(date.getHours()<18 && date.getMinutes() < 60){
			$("#eighteen").prop("checked",true);						
		}
	}
});