/**
 * 
 */

$.ajax({
	type:"get",	// method = "POST"
	url: 'http://localhost:8080/getMenuInfo',	// 送信先のURL
	data: {},	// JSONデータ本体
	contentType: 'application/json', // リクエストの Content-Type
	dataType: "json",	// レスポンスをJSONとしてパースする
	success: function(data) {
		console.log(data);
	},
	error: function() {
		alert("通信失敗");
	},
	complete: function() {
		console.log();
	}
});