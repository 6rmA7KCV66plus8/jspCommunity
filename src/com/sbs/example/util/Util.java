package com.sbs.example.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Util {
	public static Map getJsonMapFromFile(InputStream is) {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return mapper.readValue(is, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getJsonText(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		String rs = "";
		try {
			rs = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static int sendMail(String smtpServerId, String smtpServerPw, String from, String fromName, String to, String title, String body) {
		Properties prop = System.getProperties();
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.port", "587");

		Authenticator auth = new MailAuth(smtpServerId, smtpServerPw);

		Session session = Session.getDefaultInstance(prop, auth);

		MimeMessage msg = new MimeMessage(session);

		try {
			msg.setSentDate(new Date());

			msg.setFrom(new InternetAddress(from, fromName));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject(title, "UTF-8");
			msg.setContent(body, "text/html; charset=UTF-8");

			Transport.send(msg);

		} catch (AddressException ae) {
			System.out.println("AddressException : " + ae.getMessage());
			return -1;
		} catch (MessagingException me) {
			System.out.println("MessagingException : " + me.getMessage());
			return -2;
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException : " + e.getMessage());
			return -3;
		}

		return 1;
	}
	// 임시비밀번호 랜덤값
	public static String getTempPassword(int length) {
		int index = 0;
		char[] charArr = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		
		StringBuffer sb = new StringBuffer();
		
		for(int i=0; i < length; i++) {
			index = (int)(charArr.length * Math.random());
			sb.append(charArr[index]);
		}
		return sb.toString();
	}
	
	//
	public static String sha256(String base) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(base.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();
			
			for(int i=0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception ex) {
			return "";
		} 
	}

	public static int getAsInt(Object value, int defaultValue) {
		if( value instanceof Integer) { // value가 instanceof Integer 형태이다
			return (int)value; // int로 형변환을 하면 된다
		} else if( value instanceof Long) { // value가 instanceof Long 형태이다
			return Long.valueOf((long)value).intValue(); // int로 형변환을 하면 된다
		} else if( value instanceof Float) { // value가 instanceof Float 형태이다
			return Float.valueOf((float)value).intValue(); // int로 형변환을 하면 된다
		} else if( value instanceof Double) { // value가 instanceof Double 형태이다
			return Double.valueOf((double)value).intValue(); // int로 형변환을 하면 된다
		} else if( value instanceof String) { // value가 instanceof String 형태이다
			try {
			return Integer.parseInt((String)value); // int로 형변환을 하면 된다
			} catch ( NumberFormatException e) {	
			}
		}
		
		return defaultValue;
	}

	public static boolean isEmpty(Object obj) {
		if( obj == null) {
			return true;
		}
		if(obj instanceof String) {
			if( ((String)obj).trim().length() == 0){
				return true;
			}
		}
		return false;
	}

	public static String getUrlEncoded(String url) {
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return url;
		}
	}

	public static String getNowDateStr() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String dateStr = format1.format(System.currentTimeMillis());
		
		return dateStr;
	}
	// 비밀번호 변경한지 얼마나 지났는지 알아내는 부분
	public static int getPassedSecondsFrom(String from) {
		SimpleDateFormat fDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date n;
		try {
			n = fDate.parse(from);
		} catch (ParseException e) {
			return -1;
		}
		return(int) ((new Date().getTime() - n.getTime()) / 1000);
	}
	// 게시물에 댓글 작성시 작성한 댓글 Focus
	public static String getNewUriRemoved(String url, String paramName) {
		String deleteStrStarts = paramName + "=";
		int delStartPos = url.indexOf(deleteStrStarts);

		if (delStartPos != -1) {
			int delEndPos = url.indexOf("&", delStartPos);

			if (delEndPos != -1) {
				delEndPos++;
				url = url.substring(0, delStartPos) + url.substring(delEndPos, url.length());
			} else {
				url = url.substring(0, delStartPos);
			}
		}

		if (url.charAt(url.length() - 1) == '?') {
			url = url.substring(0, url.length() - 1);
		}

		if (url.charAt(url.length() - 1) == '&') {
			url = url.substring(0, url.length() - 1);
		}

		return url;
	}

	public static String getNewUrl(String url, String paramName, String paramValue) {
		url = getNewUriRemoved(url, paramName);

		if (url.contains("?")) {
			url += "&" + paramName + "=" + paramValue;
		} else {
			url += "?" + paramName + "=" + paramValue;
		}

		url = url.replace("?&", "?");

		return url;
	}

	public static String getNewUrlAndEncoded(String url, String paramName, String pramValue) {
		return getUrlEncoded(getNewUrl(url, paramName, pramValue));
	}
	// request안에 있는 파라미터 정보를 Map으로 바꿔줌
	public static Map<String, Object> getParamMap(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();

		Enumeration<String> parameterNames = request.getParameterNames();

		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			Object paramValue = request.getParameter(paramName);

			param.put(paramName, paramValue);
		}
		return param;
	}
}
