package jp.co.e_co.app.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import jp.co.e_co.app.constant.Constants;

public final class Utils {

	/**
	 * HttpServletRequestからeco-sessionを取得する.
	 *
	 * @param httpServletRequest {@link HttpServletRequest}
	 * @return eco-session
	 */
	public static String extractEcoSessionId(@NotNull HttpServletRequest httpServletRequest) {
		String response = "";
		Cookie[] cookies = httpServletRequest.getCookies();
		for (Cookie cookie : cookies) {
			try {
				System.out.println("cookie name: " + cookie.getName());
				System.out.println("cookie value: " + URLDecoder.decode(cookie.getValue(), "UTF-8"));
				if (cookie.getName().equals("eco-session")) {
					response = URLDecoder.decode(cookie.getValue(), "UTF-8");
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return response;
			}
		}
		return response;
	}
	
	/**
	 * 今年度を返す.
	 *
	 * @return int 今年度
	 */
	public static int getFiscalYear() {
		Date date = new Date();
		SimpleDateFormat sdfY = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdfM = new SimpleDateFormat("MM");
		
		String yearStr = sdfY.format(date);
		String monthStr = sdfM.format(date);
		int year = Integer.parseInt(yearStr);
		int month = Integer.parseInt(monthStr);
		if (month < 4) {
			year--;
		}
		return year;
	}
	
	/**
	 * APサーバーURLを返す.
	 *
	 * @return APサーバーURL
	 */
	public static String getBaseUrl() {
		if (Constants.ACTION_TYPE) {
			return Constants.BASE_URL;
		}
		return Constants.ROCAL_BASE_URL + Constants.PROTOCOR;
	}
	
	/**
	 * サンプルディレクトリのパスを返す.
	 *
	 * @return サンプルディレクトリのパス
	 */
	public static String getSamplDirePath() {
		if (Constants.ACTION_TYPE) {
			return Constants.SAMPLE_DIRE_PATH;
		}
		return Constants.ROCAL_SAMPLE_DIRE_PATH;
	}
}
