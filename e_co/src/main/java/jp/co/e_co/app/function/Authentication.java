package jp.co.e_co.app.function;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import jp.co.e_co.app.common.Utils;

public class Authentication {
	
	private final HttpServletRequest mHttpServletRequest;
	
	private final String mRequestId;

	public Authentication(@NotNull HttpServletRequest httprRequestd, @NotNull String requestId) {
		mHttpServletRequest = httprRequestd;
		mRequestId = requestId;
	}
	
	/**
	 * 認証処理を実行する.
	 *
	 * @return true:認証 false:非認証
	 */
	public boolean execute() {
		// セッションIDの確認
		if (!matchSessionId()) {
			return false;
		}
		return true;
	}
	
	/**
	 * cookieのsessionIDとリクエストのsessionIDが一致しているか確認.
	 *
	 * @return true：一致　false：非一致
	 */
	private boolean matchSessionId() {
		String ecoSession = Utils.extractEcoSessionId(mHttpServletRequest);
		System.out.println("eco session: " + ecoSession);
		if (!mRequestId.equals(ecoSession)) {
			System.out.println("cookieのセッションIDとメモリのセッションIDが一致しません。");
			return false;
		}
		return true;
	}
}
