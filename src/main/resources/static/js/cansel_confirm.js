/**
 *　注文キャンセル確認ダイアログ
 */
$(function(){

	if($("#status").val() == 9 || $("#status").val() == 3){
		$("#cansel_btn").hide();
	}else{
		$("#cansel_btn").show();
	}
	
	$("#cansel_btn").click(function(){
		if(confirm('本当にキャンセルしてもよろしいですか？')){
			/* はい */
//			var orderId = $("#cansel_btn").val();
//			location.href='/orderCansel?orderId=' + orderId
			document.orderCancelForm.submit();
		}
	});
});