package info.codingfun.restful.util;

public class DataUtil {
	public static String StringNullIf(String sOriginStr,String sReplaceStr) {
		if (sOriginStr == null || sOriginStr.trim().length() <= 0)
			return sReplaceStr;
		else
			return sOriginStr.trim();
	}
	
	public static boolean isStringEmpty(String sOriginStr) {
		return (sOriginStr == null || sOriginStr.trim().length() <= 0) ? true : false;
	}
	
	public static String getExtension(String fileName) {
        int startIndex = fileName.lastIndexOf(46) + 1;
        int endIndex = fileName.length();
        return fileName.substring(startIndex, endIndex);
    }
}
