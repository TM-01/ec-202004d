$(function(){
	
	calcTotal();
	$(".size").on("change", function(){
		calcTotal();
	});
	$("#quantity").on("change", function(){
		calcTotal();
	});
	$("#topping").on("change", function(){
		calcTotal();
	});
	
	function calcTotal(){
		var count = $("#topping input:checkbox:checked").length;
		if ($(".size:checked").val() == "M") {
			console.log(parseInt($("#sizeM").text().replace(/,/g,''),10));
			total = (parseInt($("#sizeM").text().replace(/,/g,''),10) + parseInt($("#toppingM").text().replace(/,/g,''),10) * count) * $("#quantity").val();
		} else if ($(".size:checked").val() == "L") {
			total = (parseInt($("#sizeL").text().replace(/,/g,''),10) + parseInt($("#toppingL").text().replace(/,/g,''),10) * count) * $("#quantity").val();
		}
		console.log(total);
		$("#total-price").text("この商品金額：" + total.toLocaleString() + " 円(税抜)");
	};
	
});