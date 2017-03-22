package info.codingfun.restful.util;

import java.security.Key;
import java.security.Security;
import javax.crypto.Cipher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DesUtil {
	private Logger logger = LogManager.getLogger(DesUtil.class.getName());
	
	private static final String ENCRYPT_TYPE = "DES";
	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;

	public DesUtil() throws Exception {	
		String classPath = this.getClass().getResource("/").getPath();
		String strKey = SysConfigUtil.getValue(logger,classPath,"sysConfig","pwd.deskey");
		logger.debug("strKey : "+strKey);
		
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(strKey.getBytes());
		encryptCipher = Cipher.getInstance(ENCRYPT_TYPE);
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);
		decryptCipher = Cipher.getInstance(ENCRYPT_TYPE);
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}
	
	public DesUtil(String strKey) throws Exception {
		logger.debug("strKey : "+strKey);
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(strKey.getBytes());
		encryptCipher = Cipher.getInstance(ENCRYPT_TYPE);
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);
		decryptCipher = Cipher.getInstance(ENCRYPT_TYPE);
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	private byte[] encryptStr(byte[] arr) throws Exception {
		return encryptCipher.doFinal(arr);
	}

	public String encrypt(String strIn) throws Exception {
		return byteArrToHexStr(encryptStr(strIn.getBytes()));
	}

	private byte[] decryptStr(byte[] arr) throws Exception {
		return decryptCipher.doFinal(arr);
	}

	public String decrypt(String strIn) throws Exception {
		return new String(decryptStr(hexStrToByteArr(strIn)));
	}

	private Key getKey(byte[] arrBTmp) {
		byte[] arrB = new byte[8];
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, ENCRYPT_TYPE);
		return key;
	}
	//DES USE
	public static String byteArrToHexStr(byte[] arrB) {
		int iLen = arrB.length;
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}
	//DES USE
	public static byte[] hexStrToByteArr(String strIn) {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}
	
}
