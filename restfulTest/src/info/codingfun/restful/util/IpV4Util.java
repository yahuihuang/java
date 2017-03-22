package info.codingfun.restful.util;

import java.util.regex.Pattern;  

/**
* <pre>
* IP地址範圍：
* 0.0.0.0～255.255.255.255，包括了mask地址。
*
* IP地址劃分:
* A類地址：1.0.0.1～126.255.255.254
* B類地址：128.0.0.1～191.255.255.254
* C類地址：192.168.0.0～192.168.255.255
* D類地址：224.0.0.1～239.255.255.254
* E類地址：240.0.0.1～255.255.255.254
*
* 如何判斷兩個IP地址是否是同一個網段中:
* 要判斷兩個IP地址是不是在同一個網段，就將它們的IP地址分別與子網掩碼做與運算，得到的結果一網絡號，如果網絡號相同，就在同一子網，否則，不在同一子網。
* 例：假定選擇了子網掩碼255.255.254.0，現在分別將上述兩個IP地址分別與掩碼做與運算，如下圖所示：
* 211.95.165.24 11010011 01011111 10100101 00011000
* 255.255.254.0 11111111 11111111 111111110 00000000
* 與的結果是: 11010011 01011111 10100100 00000000
*
* 211.95.164.78 11010011 01011111 10100100 01001110
* 255.255.254.0 11111111 11111111 111111110 00000000
* 與的結果是: 11010011 01011111 10100100 00000000
* 可以看出,得到的結果(這個結果就是網絡地址)都是一樣的，因此可以判斷這兩個IP地址在同一個子網。
*
* 如果沒有進行子網劃分，A類網絡的子網掩碼為255.0.0.0，B類網絡的子網掩碼為255.255.0.0，C類網絡的子網掩碼為255.255.255.0，缺省情況子網掩碼為255.255.255.0
*
*/
public class IpV4Util {
	// IpV4的正則表達式，用於判斷IpV4地址是否合法
	private static final String IPV4_REGEX = "((\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})";
	// 系統子網掩碼，它與ip組成1個地址
	private int mask;
	
	// 1代表A類，2代表B類，3代表C類；4代表其它類型
	public final static int IP_A_TYPE = 1;
	public final static int IP_B_TYPE = 2;
	public final static int IP_C_TYPE = 3;
	public final static int IP_OTHER_TYPE = 4;
	
	// A類地址範圍：1.0.0.1---126.255.255.254
	private static int[] IpATypeRange;
	// B類地址範圍：128.0.0.1---191.255.255.254
	private static int[] IpBTypeRange;
	// C類地址範圍：192.168.0.0～192.168.255.255
	private static int[] IpCTypeRange;
	
	// A,B,C類地址的默認mask
	private static int DefaultIpAMask;
	private static int DefaultIpBMask;
	private static int DefaultIpCMask;
              
    // 初始化
    static {
         IpATypeRange = new int[2];
         IpATypeRange[0] = getIpV4Value("1.0.0.1");
         IpATypeRange[1] = getIpV4Value("126.255.255.254");
                   
         IpBTypeRange = new int[2];
         IpBTypeRange[0] = getIpV4Value("128.0.0.1");
         IpBTypeRange[1] = getIpV4Value("191.255.255.254");
                   
         IpCTypeRange = new int[2];
         IpCTypeRange[0] = getIpV4Value("192.168.0.0");
         IpCTypeRange[1] = getIpV4Value("192.168.255.255");
                   
         DefaultIpAMask = getIpV4Value("255.0.0.0");
         DefaultIpBMask = getIpV4Value("255.255.0.0");
         DefaultIpCMask = getIpV4Value("255.255.255.0");
    }
              
    /**
    * 默認255.255.255.0
    */
    public IpV4Util() {
         mask = getIpV4Value("255.255.255.0");
    }
              
    /**
    * @param mask 任意的如"255.255.254.0"等格式，如果格式不合法，拋出UnknownError異常錯誤
    */
    public IpV4Util(String masks) {
         mask = getIpV4Value(masks);
         if (mask == 0)  {
              throw new UnknownError();
         }
    }
              
    public int getMask() {
         return mask;
    }
              
    /**
    * 比較2個ip地址是否在同1個網段中，如果2個都是合法地址，2個都是非法地址時，可以正常比較；
    * 如果有其一不是合法地址則返回false；
    * 注意此處的ip地址指的是如“192.168.1.1”地址，並不包括mask
    * @return
    */
    public boolean checkSameSegment(String ip1,String ip2) {
         return checkSameSegment(ip1,ip2,mask);
    }
              
    /**
    * 比較2個ip地址是否在同1個網段中，如果2個都是合法地址，2個都是非法地址時，可以正常比較；
    * 如果有其一不是合法地址則返回false；
    * 注意此處的ip地址指的是如“192.168.1.1”地址
    * @return
    */
    public static boolean checkSameSegment(String ip1,String ip2, int mask) {
    	// 判斷IPV4是否合法
        if (!ipV4Validate(ip1)) {
             return false;
        }
        if (!ipV4Validate(ip2)) {
              return false;
        }
        int ipValue1 = getIpV4Value(ip1);
        int ipValue2 = getIpV4Value(ip2);
        return (mask & ipValue1) == (mask & ipValue2);
    }
              
    /**
    * 比較2個ip地址是否在同1個網段中，如果2個都是合法地址，2個都是非法地址時，可以正常比較；
    * 如果有其一不是合法地址則返回false；
    * 注意此處的ip地址指的是如“192.168.1.1”地址
    * @return
    */
    public static boolean checkSameSegmentByDefault(String ip1,String ip2)  {
         int mask = getDefaultMaskValue(ip1);     // 獲取默認的Umask
         return checkSameSegment(ip1,ip2,mask);
    }
              
    /**
    * 獲取ip值與mask值與的結果
    * @param ipV4
    * @return 32bit值
    */
    public int getSegmentValue(String ipV4) {
         int ipValue = getIpV4Value(ipV4);
         return (mask & ipValue);
    }
              
    /**
    * 獲取ip值與mask值與的結果
    * @param ipV4
    * @return 32bit值
    */
    public static int getSegmentValue(String ip, int mask) {
         int ipValue = getIpV4Value(ip);
         return (mask & ipValue);
    }
              
    /**
    * 判斷ipV4或者mask地址是否合法，通過正則表達式方式進行判斷
    * @param ipv4
    */
    public static boolean ipV4Validate(String ipv4)  {
        return ipv4Validate(ipv4,IPV4_REGEX);
    }
             
    private static boolean ipv4Validate(String addr,String regex) {
    	if (addr == null) {
             return false;
        } else {
             return Pattern.matches(regex, addr.trim());
        }
    }
             
    /**
    * 比較2個ip地址，如果2個都是合法地址，則1代表ip1大於ip2，-1代表ip1小於ip2,0代表相等；
    * 如果有其一不是合法地址，如ip2不是合法地址，則ip1大於ip2，返回1，反之返回-1；2個都是非法地址時，則返回0；
    * 注意此處的ip地址指的是如“192.168.1.1”地址，並不包括mask
    * @return
    */
    public static int compareIpV4s(String ip1,String ip2) {
         int result = 0;
         int ipValue1 = getIpV4Value(ip1);     // 获取ip1的32bit值
         int ipValue2 = getIpV4Value(ip2); // 获取ip2的32bit值
         if(ipValue1 > ipValue2)
         {
              result =  -1;
         }
         else if(ipValue1 <= ipValue2)
         {
              result = 1;
         }
         return result;
    }
              
    /**
    * 檢測ipV4 的類型，包括A類，B類，C類，其它（C,D和廣播）類等
    * @param ipV4
    * @return 返回1代表A類，返回2代表B類，返回3代表C類；返回4代表D類
    */
    public static int checkIpV4Type(String ipV4) {
         int inValue = getIpV4Value(ipV4);
         if(inValue >= IpCTypeRange[0] && inValue <= IpCTypeRange[1]) {
              return IP_C_TYPE;
         } else if(inValue >= IpBTypeRange[0] && inValue <= IpBTypeRange[1]) {
              return IP_B_TYPE;
         } else if(inValue >= IpATypeRange[0] && inValue <= IpATypeRange[1]) {
              return IP_A_TYPE;
         }
         return IP_OTHER_TYPE;
    }
              
    /**
    * 獲取默認mask值，如果IpV4是A類地址，則返回{@linkplain #DefaultIpAMask}，
    * 如果IpV4是B類地址，則返回{@linkplain #DefaultIpBMask}，以此類推
    * @param anyIpV4 任何合法的IpV4
    * @return mask 32bit值
    */
    public static int getDefaultMaskValue(String anyIpV4) {
         int checkIpType = checkIpV4Type(anyIpV4);
         int maskValue = 0;
         switch (checkIpType) {
              case IP_C_TYPE:
                   maskValue = DefaultIpCMask;
                   break;
              case IP_B_TYPE:
                   maskValue = DefaultIpBMask;
                   break;
              case IP_A_TYPE:
                   maskValue = DefaultIpAMask;
                   break;
              default:
                   maskValue = DefaultIpCMask;
         }
         return maskValue;
    }
              
    /**
    * 獲取默認mask地址，A類地址對應255.0.0.0，B類地址對應255.255.0.0，
    * C類及其它對應255.255.255.0
    * @param anyIp
    * @return mask 字符串表示
    */
    public static String getDefaultMaskStr(String anyIp) {
         return trans2IpStr(getDefaultMaskValue(anyIp));
    }
              
    /**
    * 將ip 32bit值轉換為如“192.168.0.1”等格式的字符串
    * @param ipValue 32bit值
    * @return
    */
    public static String trans2IpStr(int ipValue) {
         // 保證每一位地址都是正整數
         return ((ipValue >> 24) & 0xff) + "." + ((ipValue >> 16) & 0xff) + "." + ((ipValue >> 8) & 0xff) + "." + (ipValue & 0xff);
    }
              
    /**
    * 將ip byte數組值轉換為如“192.168.0.1”等格式的字符串
    * @param ipBytes 32bit值
    * @return
    */
    public static String trans2IpV4Str(byte[] ipBytes) {
        // 保證每一位地址都是正整數
        return (ipBytes[0] & 0xff) + "." + (ipBytes[1] & 0xff) + "." + (ipBytes[2] & 0xff) + "." + (ipBytes[3] & 0xff);
    }
    
    public static int getIpV4Value(String ipOrMask) {
         byte[] addr = getIpV4Bytes(ipOrMask);
         int address1  = addr[3] & 0xFF;
         address1 |= ((addr[2] << 8) & 0xFF00);
         address1 |= ((addr[1] << 16) & 0xFF0000);
         address1 |= ((addr[0] << 24) & 0xFF000000);
         return address1;
    }
    
    public static byte[] getIpV4Bytes(String ipOrMask) {
         try {
              String[] addrs = ipOrMask.split("\\.");
              int length = addrs.length;
              byte[] addr = new byte[length];
              for (int index = 0; index < length; index++)
              {
                   addr[index] = (byte) (Integer.parseInt(addrs[index]) & 0xff);
              }
              return addr;
         } catch (Exception e) {
         }
         return new byte[4];
    }
}
