window.onload = function() {
	if (message == null || message == "") {
		return;
	}
	jAlert(message, 'お知らせ');
};