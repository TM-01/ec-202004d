/**
 * パスワード非同期チェック
 */
$(function() {
	$("#inputPassword").on("keyup", function() {
		check_password();
	});

	$("#inputConfirmationPassword").on("keyup", function() {
		check_password();
	});

	$("#inputPassword").on(
			"keyup",
			function() {
				console.log($("#inputConfirmationPassword").val().length);
				if ($("#inputPassword").val() === $(
						"#inputConfirmationPassword").val()) {
					$("#register_btn").prop("disabled", false);
				} else {
					$("#register_btn").prop("disabled", true);
				}
				if ($("#inputConfirmationPassword").val().length == 0) {
					$("#disagreementMessage").val("確認用パスワードを入力してください");
				}
			})

	$("#inputConfirmationPassword").on(
			"keyup",
			function() {
				if ($("#inputPassword").val() === $(
						"#inputConfirmationPassword").val()) {
					$("#register_btn").prop("disabled", false);
				} else {
					$("#register_btn").prop("disabled", true);
				}
			})

	function check_password() {
		var hostUrl = "http://localhost:8080/showUserRegister/check";
		var inputPassword = $("#inputPassword").val();
		var inputConfirmationPassword = $("#inputConfirmationPassword").val();
		$.ajax({
			url : hostUrl,
			type : "GET",
			dataType : "json",
			data : {
				password : inputPassword,
				conPassword : inputConfirmationPassword
			},
			async : true
		// 非同期で処理を行う
		}).done(function(data) {
			// コンソールに取得データを表示
			console.log(data);
			console.dir(JSON.stringify(data));
			$("#robustnessMessage").html(data.robustnessMessage);
			$("#disagreementMessage").html(data.disagreementMessage);
			if ($("#inputConfirmationPassword").val().length == 0) {
				$("#disagreementMessage").val("確認用パスワードを入力してください");
			}
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert("エラーが発生しました！");
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
		});
	}
});