package com.hy.shop.commom.util;

import java.text.SimpleDateFormat;
public class Util {

	private static int fileCounter = 1;
	// 파일명 변경 메소드
	public static String fileRename(String originFileName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddmmss");
		String date = sdf.format(new java.util.Date(System.currentTimeMillis()));

		String ext = originFileName.substring(originFileName.lastIndexOf("."));

		String fileCounterString = String.format("_%d", fileCounter++);
		return "thumbnail_" + date + fileCounterString + ext;
	}

	// 크로스 사이트 스트립트 공격을 방지 하기 위한 메소드
	public static String XSSHandling(String content) {
		if(content != null) {
			content = content.replaceAll("&", "&amp;");
			content = content.replaceAll("<", "&lt;");
			content = content.replaceAll(">", "&gt;");
			content = content.replaceAll("\"", "&quot;");
		}
		return content;
	}


	// 크로스 사이트 스트립트 해제
	public static String XSSClear(String content) {
		if(content != null) {
			content = content.replaceAll("&amp;", "&");
			content = content.replaceAll("&lt;", "<" );
			content = content.replaceAll("&gt;", ">");
			content = content.replaceAll("&quot;", "\"");
		}
		return content;
	}


	// 개행문자 처리
	public static String newLineHandling(String content) {
		return content.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
	}

	// 개행문자 해제
	public static String newLineClear(String content) {
		return content.replaceAll("<br>", "\n");
	}




}