/**
 * emailの一致確認を行う.
 */
var checkRepeatEmal = function(input) {
	if (input.value != document.getElementById('email').value) {
		input.setCustomValidity('メールアドレスが一致しません。');
	} else {
		input.setCustomValidity('');
	}
}

/**
 * パスワードの一致確認を行う.
 */
var checkPassword = function(input) {
	if (input.value != document.getElementById('password').value) {
		input.setCustomValidity('パスワードが一致しません。');
	} else {
		input.setCustomValidity('');
	}
}

window.onload = function() {
	if (message == null || message == "") {
		return;
	}
	jAlert(message, 'お知らせ');
};