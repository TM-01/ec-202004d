/**
 * 
 */
$(function(){
	var check=0;
	console.log(check);
	$(".pay").hide();
	$("#credit_btn").hide();
	$(".h3-credit").hide();
	$("input[name='paymentMethod']").on("change",function(){
		if(check==0){
			$(".pay").show();
			$("#decision_btn").hide();
			$("#credit_btn").show();
			$(".h3-credit").show();
			check=1;
			console.log(check);
		}else{
			$(".pay").hide();
			$("#decision_btn").show();
			$("#credit_btn").hide();
			$(".h3-credit").hide();
			check=0;
		}
		
	});
	'use strict';

	  /*
	    今日の日付データを変数todayに格納
	   */
	  var optionLoop, this_day, this_month, this_year, today;
	  today = new Date();
	  this_year = today.getFullYear();
	  this_month = today.getMonth() + 1;
	  this_day = today.getDate();

	  /*
	    ループ処理（スタート数字、終了数字、表示id名、デフォルト数字）
	   */
	  optionLoop = function(start, end, id, this_day) {
	    var i, opt;

	    opt = null;
	    for (i = start; i <= end ; i++) {
	      if (i === this_day) {
	        opt += "<option value='" + i + "' selected>" + i + "</option>";
	      } else {
	        opt += "<option value='" + i + "'>" + i + "</option>";
	      }
	    }
	    return document.getElementById(id).innerHTML = opt;
	  };


	  /*
	    関数設定（スタート数字[必須]、終了数字[必須]、表示id名[必須]、デフォルト数字[省略可能]）
	   */
	  optionLoop(this_year, this_year+100, 'card_exp_year', this_year);
	  optionLoop(1, 12, 'card_exp_month', this_month);
	  optionLoop(1, 31, 'id_day', this_day);
	  
});