package com.sbs.example.jspCommunity;

public class App {
	public static boolean isProductMode() {
		String profilesActive = System.getProperty("spring.profiles.active");
		
		if(profilesActive == null) {
			return false;
		}
		
		if(profilesActive.equals("production") == false ) { // 개발 모드가 아니다
			return false;
		}
		return true; // 그 외엔 true
	}
	
	public static String getSiteName()	{
		return "JSP Community";
	}

	public static String getContextName() {
		if(isProductMode()) { // isProductMode가 참이면
			return ""; // Contextname은 root니까 없다
		}
		
		return "jspCommunity";
	}
	
	//개발서버의 getAppUrl()는 getSiteProtocol() http +"://"+ getSIteDomain() localhost +":"+  getSitePort() 8181 +"/"+ getContextname() jspCommunit를 합치면 http://localhost:8181/jspCommunuty 이렇게 출력된다

	//운영서버의 getAppUrl()는 getSiteProtocol() https +"://"+ getSIteDomain() law.oa.gg를 합치면 https://law.oa.gg 나머지(포트, 컨텍스트네임)는 false(line:44)이므로 생략
	
	public static String getMainUrl() {
		return getAppUrl(); // 이용자가 usr/home/main에 들어가야 했지만 안들어 가도 된다, index.html을 믿고
	}
	
	public static String getLoginUrl() {
		return getAppUrl() + "/usr/member/login";
	}

	public static String getAppUrl() {
		String appUrl = getSiteProtocol() + "://" + getSiteDomain(); // 먼저 getSiteProtocol를 불러옴
	
		if(getSitePort() != 81 && getSitePort() != 443) { // 포트가 81도 아니고 443도 아니면 생략할 수 가 없다
			appUrl += ":" + getSitePort(); // 운영 모드일 떈 false니깐 생략
		}
		
		if(getContextName().length() > 0) { // 0 이상이니까 값이 있다
			appUrl += "/" + getContextName(); // 운영 모드일 땐 얘도 생략
		}
		
		return appUrl;
	}
	
	private static String getSiteProtocol() {
		if(isProductMode()) { // 현재 모드가 isProductMode면
			return "https"; //https로
		}
		return "http"; // isProductMode가 아니면 http로
	}
	
	private static int getSitePort() {
		if(isProductMode()) { // isProductMode가 참이면
			return 443;
		}
		return 8181;
	}

	private static String getSiteDomain() {
		if(isProductMode()) {
			return "law.oa.gg";
		}
		
		return "localhost";
	}
}
