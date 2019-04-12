package tdh.util;

public class StringUtils {
	public static String trim(Object obj) {
		return obj == null ? "" : obj.toString().trim();
	}
}
