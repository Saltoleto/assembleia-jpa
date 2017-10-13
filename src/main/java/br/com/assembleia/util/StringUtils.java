package br.com.assembleia.util;

public class StringUtils {
	public static boolean isEmptyOrNull(String str){
		return str == null || "".equals(str);
	}
}
