package com.ericsson.li.type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.omg.IOP.Encoding;

public class ByteBitTest {
	
	byte flag;
	byte[] startTag;;
	byte cmdTag;
	byte rstTag;
	byte[] serialNum;
	byte[] vin;
	byte[] rsa;
	byte[] dataLen;
	byte[] data;
	byte bcc;
	

    public static void test() throws Throwable {
        byte[] data = new byte[12]; //测试数据准备，假设LZ所说的串是保存在byte数组中
        data[0] = Integer.valueOf("00011101", 2).byteValue(); //头3个数据用LZ的例子数据
        data[1] = Integer.valueOf("11000011", 2).byteValue();
        data[2] = Integer.valueOf("11000011", 2).byteValue();
        for (int i=3; i<data.length; i++) { //有规律的数据方便检验程序的正确性
            data[i] = (i%2 == 0) ? (byte)0x00 : (byte)0xff;
        }
 
        System.out.println("test data:"); //测试数据打印
        for (int i=0; i<data.length; i++) {
            System.out.printf("%s ", bitString((byte)data[i]));
        }
        System.out.println();
         
        byte key = Integer.valueOf("01110111", 2).byteValue(); //在bit数据中找到的key
        System.out.println("key:");
        System.out.printf("key=%s\n", bitString(key));
 
        //为了检验程序，程序中对于每次移位以及取数据后做了打印处理
        System.out.println("--------test start--------"); //测试开始
        byte b = 0;
        for (int i=0, bit=0; i<data.length; i++) {
            if (i == 0) {
                b = data[i]; //第一个数据直接取byte的值
            } else {
                System.out.printf("before bit offset:b=%s\n", bitString(b));
                if (bit > 0) { //如果发生移位，并且不是移动整个byte的时候
                    b <<= bit; //取下一个byte前bit位来弥补上一个byte不足的数据
                    b |= (data[i] >> (8-bit)) & (0x00ff >> (8-bit));
                    System.out.printf("%d bit offset:b=%s\n", bit, bitString(b));
                    if (b == key) break;
                }
                for (int j=bit; j<8; j++) { //从下一个数据的bit位开始循环
                    b <<= 1; //上次数据左移1位
                    b |= ((data[i] >> (8-j-1)) & 1); //用下一个数据的1位来补到上次数据的后1位
                    System.out.printf("1 bit offset:b=%s\n", bitString(b));
                    if (b == key) { //如果找到key，则退出循环，并保存移动的bit数
                        bit = (j+1)%8;
                        break;
                    }
                    if (j == 7 && bit > 0) bit = 0;
                }
            }
            if (b == key) { //如果找到key
                System.out.printf("find key=%s\n", bitString(b));
                char[] c = new char[4]; //取64位信息保存到字符数组中
         
                for (int j=i, k=0, cnt=0; j<i+9 && j<data.length; j++) { //从当前的byte开始循环8个byte取64位数据
                    if (j==i) {
                        b = data[j]; //第一个byte数据直接保存
                    } else {
                        if (bit > 0) { //如果发生了移位，即不是整byte的时候
                            b <<= bit; //上一个byte左移bit位
                            b |= (data[j] >> (8-bit)) & (0x00ff >> (8-bit)); //用下一个的前bit位补到上一个数据的后bit位
                        }
                        if (cnt == 0) { //第一个byte的时候
                            c[k] = (char)b; //保存到字符数组中，并让字符左移8位，相当于字符的高8位保存
                            c[k] <<= 8;
                            cnt++;
                        } else if (cnt == 1) { //第二个byte的时候，保存字符的低8位
                            c[k] |= b;
                            k++;
                            cnt = 0;
                        }
                        b = data[j];
                    }
                }
 
                for (int j=0; j<c.length; j++) { //打印64 bit的结果
                    System.out.printf("char data = %s\n", bitString(c[j]));
                }
                i += 8;
                if (i < data.length) b = data[i];
            }
        } 
        System.out.println("--------test end--------");
    }
	
    public static String bitString(byte b) { //获取byte的二进制信息
        StringBuilder buf = new StringBuilder(Integer.toBinaryString(b & 0x000000ff));
        while (buf.length() < 8) {
            buf.insert(0, "0");
        }
        return buf.toString();
    }
 
    public static String bitString(char c) {//获取char的二进制信息
        StringBuilder buf = new StringBuilder(Integer.toBinaryString(c & 0x0000ffff));
        while (buf.length() < 16) {
            buf.insert(0, "0");
        }
        return buf.toString();
    }
	

	
    /** 
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit 
     */  
    public static byte[] getBooleanArray(byte b) {  
        byte[] array = new byte[8];  
        for (int i = 7; i >= 0; i--) {  
            array[i] = (byte)(b & 1);  
            b = (byte) (b >> 1);  
        }  
        return array;  
    }  
    /** 
     * 把byte转为字符串的bit 
     */  
    public static String byteToBit(byte b) {  
        return ""  
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)  
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)  
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)  
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);  
    }  
    
    
    public static void testByte(byte a) {
    	System.out.println(a);
    	int i = a&0xff;
    	System.out.println(i);
    }
    
    public static void testChar() {
    	
    	int a = (int)'你';
    	char d = (char)97;
    	System.out.println(a + d);
    	
    	char c = 1;
    	while (c != 0) {
    		//System.out.println((int)c + ":" + c);
    		
    		c++;
    	}
    	
    	System.out.println((int)c + ":" + c);
    }
    
	public static void testGBK2UTF8() throws UnsupportedEncodingException {
		String chinese = "中文";// java内部编码(unicode?)
		System.out.println("utf8-chinese:" + new String(chinese.getBytes("UTF-8"), "ISO-8859-1"));
		System.out.println("gbk-chinese:" + new String(chinese.getBytes("GBK"), "ISO-8859-1"));
		
		String gbkChinese = new String(chinese.getBytes("GBK"), "ISO-8859-1");// 转换成gbk编码
		System.out.println("gbkChinese:" + gbkChinese);
		String unicodeChinese = new String(gbkChinese.getBytes("ISO-8859-1"), "GBK");// java内部编码
		System.out.println("unicodeChinese:" + unicodeChinese);// 中文
		String utf8Chinese = new String(unicodeChinese.getBytes("UTF-8"), "ISO-8859-1");// utf--8编码
		System.out.println("utf8Chinese:" + utf8Chinese);// 乱码
		unicodeChinese = new String(utf8Chinese.getBytes("ISO-8859-1"), "UTF-8");// java内部编码
		System.out.println("unicodeChinese:" + unicodeChinese);// 中文
	}
    
	public static void gbkTest() {
		String tt = "##";
		
		byte[] beginSign = new byte[]{0x23, 0x23};
		String oo;
		
		String gbktt;
		try {
			gbktt = new String(tt.getBytes("GBK"), "ISO-8859-1");
			System.out.println("gbktt:" + gbktt);
			oo = new String(beginSign, "ISO-8859-1");
			System.out.println("oo:" + oo);
			
			if (oo.equals(gbktt)) {
				System.out.println("equal!");
			} else {
				System.out.println("not equal!");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 转换成gbk编码
	}
	
	public static void shortByteTest() {
		short a = 122,b = 135;
		byte[] out = new byte[3];
		out[0] = (byte) ( a >> 4 );//把a的高8位放在第一个字节
		out[1] = (byte) ( a << 4 );//先把a左移四位，在右边补上4个0，第二个字节的高4位就是a的低4位了，第二个字节的高4位已经生成，低4位还是0
		out[1] |= (byte) ( b >> 8 & 0x0f );//b右移8位，并与0x0f进行与操作，实际上就只保留了b的高4位，并且是在字节的低4位上，跟第二步得到的字节进行或操作，就生成了第二个字节
		out[2] = (byte) b;//把b的高4位截断就得到了低8位然后再把这个字节数组写入文件，就可以用3个字节表示两个整数了。
		
		a =(short)( (out[0] & 0xff) << 4 | ( out[1] & 0xf0 )>>4);
		b = (short)((out[1] & 0x0f) << 8 | ( out[2] & 0xff));
		System.out.println("a=" + a +",b=" + b);
	}
	
	public static byte[] int2byte1(int res) {  
		//小端
		byte[] targets = new byte[4];  
		  
		targets[0] = (byte) (res & 0xff);// 最低位   
		targets[1] = (byte) ((res >> 8) & 0xff);// 次低位   
		targets[2] = (byte) ((res >> 16) & 0xff);// 次高位   
		targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。   
		return targets;   
		}   
	
	public static int byte2int1(byte[] res) {
		// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000
		//小端
		int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
				| ((res[2] << 24) >>> 8) | (res[3] << 24);
		return targets;
	}
	
	public static byte[] int2byte(int res) {  
		byte[] targets = new byte[4];  
		targets[0] = (byte) (res >>> 24);// 最高位,无符号右移。     
		targets[1] = (byte) ((res >> 16) & 0xff);// 次高位   
		targets[2] = (byte) ((res >> 8) & 0xff);// 次低位   
		targets[3] = (byte) (res & 0xff);// 最低位   
		return targets;   
		}   
	
	public static int byte2int(byte[] res) {
		// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000
		int targets = (res[0] << 24) | ((res[1] << 24) >>> 8) | ((res[2] << 8) & 0xff00) | (res[3] & 0xff);
		return targets;
	}
	
    public static byte[] char2byte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        
        return b;
        
        
    }
    
    public static char byte2char(byte[] b) {
        char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
        return c;
    }
    
    public static String byte2HexString(byte b) {
    	return "0x" + Integer.toHexString(b&(0xFF)).toUpperCase();
    }
    
	public static String byte2HexString(byte[] b) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		int count = b.length - 1;
		for (i = 0; i < count; i++) {
			sb.append("0x").append(Integer.toHexString(b[i]&(0xFF)).toUpperCase()).append(",");
		}
		sb.append("0x").append(Integer.toHexString(b[count]&(0xFF)).toUpperCase());
    	return sb.toString();
    }
    
    public static String byte2BitString2(byte b) { //获取byte的二进制信息 
        return ""  
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)  
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)  
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)  
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);  
    }
	
    public static void bbbbbb() {
    	byte[] bk = new byte[0];
    	bn = bk;
    	System.out.println(bk.length);
    	byte b = (byte)0xFE;
    	System.out.println((int)0xFE);
    	System.out.println(b);
    	System.out.println((int)b);
    	System.out.println(byteToBit(b));
    }
    
    public static void ByteArrayOutputStreamTest() throws IOException {
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	byte[] aa = new byte[]{0x01, 0x02};
    	byte[] bb = new byte[]{0x03, 0x04};
    	bos.write(aa);
    	bos.write(bb);
    	
    	System.out.println(byte2HexString(bos.toByteArray()));
    }
    
    public static void defaultTest() {
    	byte aa[] = new byte[40];
    	System.out.println(byte2HexString(aa));
    }
    
    static byte[] bn;
    
    public static void testInt2Byte(int a) {
    	
    	System.out.println("testInt2Byte:" + a);
    	
    	byte b = (byte)a;
    	System.out.println("testInt2Byte b-bit:" + byte2BitString2(b));
    	System.out.println("testInt2Byte b:" + b);
    	System.out.println("testInt2Byte a+b:" + (a + b));
    }
    
    private static class AA {
    	byte [] b;

		public byte[] getB() {
			return b;
		}

		public void setB(byte[] b) {
			this.b = b;
		}
    	
		public void print() {
			for (int i = 0; i < b.length; i++) {
				int k = b[i];
				System.out.println("testInt2Byte b[" + i + "]:"+ k);
			}
		}
    }
    
    private static void testAA() {
    	byte[] bb = new byte[3];
    	bb[0] = 0;
    	bb[1] = 1;
    	bb[2] = 2;
    	AA aa = new AA();
    	aa.setB(bb);
    	aa.print();
    	System.out.println("========");
    	aa.getB()[2] = 3;
    	aa.print();
    }
    
    private static void testBB() {
    	byte[] bb = null;
    	testBB(bb);
    	
		byte b = 48;
		int k = b & 0xFF;
		int gear = b & 0x0F;
/*		int breakFlag = b & 0x10 >>> 3;
		int powerFlag = b & 0x20  >>> 4;*/
		
		int breakFlag = (b>>>4) & 0x01;
		int powerFlag = (b>>>5) & 0x01;
    	
    	System.out.println("gear:" + gear);
    	System.out.println("breakFlag:" + breakFlag);
    	System.out.println("powerFlag:" + powerFlag);
    	System.out.println("BB:" + bb);
    }
    
    private static void testBB(byte[] bb) {
    	bb = new byte[3];
    	bb[0]  =1;
    }
    
    public static void main(String[] args) throws Throwable {  
    	//testAA();
    	testBB();
    	/*defaultTest();
    	
    	ByteArrayOutputStreamTest();
    	
    	
    	bbbbbb();
    	System.out.println(bn.length);
    	
        byte b = 0x35; // 0011 0101  
        // 输出 [0, 0, 1, 1, 0, 1, 0, 1]  
        System.out.println(Arrays.toString(getBooleanArray(b)));  
        // 输出 00110101  
        System.out.println(byteToBit(b));  
        // JDK自带的方法，会忽略前面的 0  
        System.out.println(Integer.toBinaryString(0x35));  
        
        gbkTest();
        shortByteTest();
        
        System.out.println(byte2int(int2byte(5)));
        System.out.println(byte2int(int2byte(-125)));
        System.out.println(byte2int(int2byte(-120005)));
        System.out.println(byte2char(char2byte('5')));
        System.out.println(byte2char(char2byte('a')));
        
        //testChar();
        
        //testByte((byte)234);
        //testGBK2UTF8();
		//test();
        
        testInt2Byte(254);*/

    } 

}
