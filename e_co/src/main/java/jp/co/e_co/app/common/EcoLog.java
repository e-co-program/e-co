package jp.co.e_co.app.common;

public final class EcoLog {

	public final static boolean DEBUG = true;
	
	private EcoLog() {};
	
	public static void printOut(String message) {
		System.out.println(message);
	}
}
