/**
 * ページング用
 */

$(function() {
	var pageNumber = 0;
	// 表示したい行数
	var rows = $("#result").val() / 3;
	// rows行の時の最大のページ数（ (Max行数+rows-1)/rows）
	var maxPages = parseInt((item_list_table.rows.length + rows - 1) / rows);
	// 
	var pages = 0;
	// trタグにidを自動採番
	$("tr").each(function(i) {
		$(this).attr("id", "tr" + (i));
	});	
	// ページ遷移用のボタン
	$("#pagination").twbsPagination({
		totalPages : maxPages,
		visiblePages : 5,
		first : "<<",
		next : ">",
		prev : "<",
		last : ">>",
		//　クリックされたときの挙動
		onPageClick : function(event, page) {
			pageNumber = page - 1;
			draw();
		}
	});
	// 1ページに表示する件数を指定する
	$("#result").change(function() {
		rows = $("#result").val() / 3;
		pages = maxPages;
		maxPages = parseInt((item_list_table.rows.length + rows - 1) / rows);
		draw();
	});
	// ページごとにテーブルを指定された行のみ表示する
	function draw() {
		$("tr").hide();
		for (let i = 0; i < rows; i++) {
			$("#tr" + ((pageNumber * rows) + i)).show();
		}
	}
	draw();
});
