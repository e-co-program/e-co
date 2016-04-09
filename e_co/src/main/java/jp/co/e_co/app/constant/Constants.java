package jp.co.e_co.app.constant;

public final class Constants {
	// true：デバックモード
	public static final boolean DEBUG_MODE = true;
	
	// Redis キー有効期限　seconds 4h
	public static final int REDIS_TIME_OUT = 9600;
	
	// APサーバーURL 
	// ローカル=http://localhost 開発=http://133.242.230.16　
	// 本番=http://153.120.167.102 本番ドメイン=http://e-co-lab.com/eco/login
	public static final String BASE_URL = "http://e-co-lab.com";
	
	// プロトコル
	public static final String PROTOCOR = ":8080";
	
	// sampleディレクトリパス　開発：C:\\inetpub\\wwwroot\\sample　本番：C:\\eco\\sample
	public static final String SAMPLE_DIRE_PATH = "C:\\eco\\sample";
}
