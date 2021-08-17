package com.sbs.example.jspCommunity;

public class App {
	public static String getSite()	{
		return "JSP Community";
	}

	public static String getContextname() {
		return "jspCommunity";
	}
	
	public static String getMainUrl() {
		return "http://" + getSiteDomain() + ":" + getSitePort() + "/" + getContextname() + "/usr/home/main";
	}
	
	public static String getLoginUrl() {
		return "http://" + getSiteDomain() + ":" + getSitePort() + "/" + getContextname() + "/usr/member/login";
	}

	private static int getSitePort() {
		return 8181;
	}

	private static String getSiteDomain() {
		return "localhost";
	}
}
