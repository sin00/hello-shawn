package com.ericsson.li.type;

public class HexTest {
	
	public static void main(String[] args) {
		test16Str();
		System.out.println(convertHexToString("4c3654579334340d38484e8b30305332739a6afe766e766f"));
	}
	
	 /**
     * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
     * @param src byte[] data
     * @return hex string
     */  
	
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    /**
     * Convert hex string to byte[]
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (hexcharToint(hexChars[pos]) << 4 | hexcharToint(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
     private static int hexcharToint(char c) {
        return "0123456789ABCDEF".indexOf(c);
    }
     
    private static void test16Str() {
    	System.out.println(hexStringToBytes2("2B383631343539313636343439350000"));
    	System.out.println(hexStringToBytes2("2B383631343533373739343135300000"));
    	System.out.println(hexStringToBytes2("2B383631343533373739333937390000"));
    	System.out.println(hexStringToBytes2("2B383631343539313636343436350000"));
    	System.out.println(hexStringToBytes2("2B383600000000000000000000000000"));
    	
    	
    	System.out.println(hexStringToBytes2("2B3836"));
    	System.out.println(hexStringToBytes2("2B387"));
    	System.out.println(hexStringToBytes2("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"));
    	
    	System.out.println(hexStringToBytes2("4c365457KKK9334340d38484e8b30305332739a6afe766e766f"));
    	System.out.println(hexStringToBytes2("4c3654579334340d38484e8b30305332739a6afe766e766f"));
    	
    	System.out.println(hexStringToBytes2("4c3654579334340d38484e8b30305332739a6afe766e766f"));
    	
    	System.out.println("2abc".matches("^([0-9|a-f|A-F]*)$"));
    	System.out.println("-2abc".matches("^([0-9|a-f|A-F]*)$"));
    	
    	System.out.println("2abcf".matches("^([0-9|a-f|A-F]*)$"));
    	System.out.println("2abcg".matches("^([0-9|a-f|A-F]*)$"));
    	
    	//31353961626a6b78797a =>159abjkxyz
    	System.out.println(hexStringToString("31353961626A6B78797A"));
    	System.out.println(hexStringToString("31353961626A6B78797"));
    	System.out.println(hexStringToString("31GG3961626A6B78797A"));
    	
    	
    	
    }
    
    public static byte[] hexStringToBytes1(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (hexcharToint(hexChars[pos]) << 4 | hexcharToint(hexChars[pos + 1]));
        }
        return d;
    }
    
    public static String hexStringToBytes2(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
       // byte[] d = new byte[length];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            char a = (char) (hexcharToint(hexChars[pos]) << 4 | hexcharToint(hexChars[pos + 1]));
            sb.append(a);
        }
        return sb.toString();
    }
    
    public static String convertHexToString(String hex) {  
    	  
        StringBuilder sb = new StringBuilder();  
        StringBuilder temp = new StringBuilder();  
    
        //49204c6f7665204a617661 split into two characters 49, 20, 4c...  
        for( int i=0; i < hex.length()-1; i+=2 ){  
    
            //grab the hex in pairs  
            String output = hex.substring(i, (i + 2));  
            //convert hex to decimal  
            int decimal = Integer.parseInt(output, 16);  
            //System.out.println(decimal);
            //convert the decimal to character  
            sb.append((char)decimal);  
    
            temp.append(decimal);  
        }  
    
        return sb.toString();  
        }  

	public static void isHexTest(String hex) {
		String regex = "^[A-Fa-f0-9]{4}|[A-Fa-f0-9]{7}$";
		String s = "123b123";
		if (hex.matches(regex)) {
			System.out.println(s.toUpperCase() + "是4位或者7位16进制数");
		} else {
			System.out.println(s.toUpperCase() + "不是4位或者7位16进制数");
		}
		//return hex.matches("[A-Fa-f0-9]");
		
	}
	
    public static String hexStringToString(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            int v1 = hexcharToint(hexChars[pos]);
            int v2 = hexcharToint(hexChars[pos + 1]);
            if (v1 == -1 | v2 == -1) {
            	break;
            }
            char a = (char) (v1 << 4 | v2);
            sb.append(a);
        }
        return sb.toString();
    }
}
