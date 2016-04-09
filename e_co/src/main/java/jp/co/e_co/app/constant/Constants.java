package jp.co.e_co.app.constant;

public final class Constants {
	// true：デバックモード
	public static final boolean DEBUG_MODE = true;
	
	// Redis キー有効期限　seconds 4h
	public static final int REDIS_TIME_OUT = 9600;
	
	// APサーバーURL http://localhost http://133.242.230.16 http://153.120.167.102 http://e-co-lab.com/eco/login
	public static final String BASE_URL = "http://e-co-lab.com";
	
	// プロトコル
	public static final String PROTOCOR = ":8080";
}
