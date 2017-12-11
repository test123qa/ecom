package com.api.framework;

public class ConnectSettings {

	public static class mosaic {

		static String qMngrStr = "MOS01D";
		static String hostName = "10.9.14.40";
		static String channel = "MOSAIC.SVRCONN";
		static int port = 30011;
		static String userid = "mosaicmq";
	}
	
	public static class mosaicl {

		static String qMngrStr = "MOS01LOCAL";
		static String hostName = "localhost";
		static String channel = "MOSAIC.SVRCONN";
		static int port = 30011;
		static String userid = "mosaicmq";
	}
	
	public static class lime {

		static String qMngrStr = "LIM01D";
		static String hostName = "10.9.14.40";
		static String channel = "LIM01D.SVRCONN";
		static int port = 30012;
		static String userid = "cm9d9w8";
	}
}
