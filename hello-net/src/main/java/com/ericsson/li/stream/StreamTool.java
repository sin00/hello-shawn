package com.ericsson.li.stream;

import java.io.*;  
public class StreamTool {     
    public static void main(String[] args) {  
        try {  
            File file = new File("D:\\workspace\\test\\ceshi.txt");  
            FileInputStream fin = new FileInputStream(file);  
            byte[] filebt = readStream(fin);  
            System.out.println(filebt.length);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }     
    }     
    /** 
     * @功能 读取流 
     * @param inStream 
     * @return 字节数组 
     * @throws Exception 
     */  
    public static byte[] readStream(InputStream inStream) throws Exception {  
   /*     ByteArrayOutputStream outSteam = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = -1;  
        while ((len = inStream.read(buffer)) != -1) {  
            outSteam.write(buffer, 0, len);  
        }  
        outSteam.close();  
        inStream.close();  
        return outSteam.toByteArray();*/  
        int count = 0;  
        while (count == 0) {  
            count = inStream.available();  
        }  
        byte[] b = new byte[count];  
        inStream.read(b);  
        return b;
        
        /*
 int count = 100;  
byte[] b = new byte[count];  
int readCount = 0; // 已经成功读取的字节的个数  
while (readCount < count) {  
    readCount += inStream.read(b, readCount, count - readCount);  
}  
         * */
    } 
    
	public static void copyFile(String fileSrc, String fileDst) throws IOException {
		InputStream inputStream = new FileInputStream(fileSrc);
		OutputStream outputStream = new FileOutputStream(fileDst);
		int bytesWritten = 0;
		int byteCount = 0;
		byte[] bytes = new byte[1024];
		while ((byteCount = inputStream.read(bytes)) != -1)

		{

			outputStream.write(bytes, bytesWritten, byteCount);

			bytesWritten += byteCount;

		}

		inputStream.close();

		outputStream.close();

	}
	
	 private byte[] getEncodedData(InputStream is) throws IOException {
	        byte[] maxBuffer = new byte[1024 * 64];
	        int length = 0;
	        int lengthTemp = 0;
	        while (-1 != (lengthTemp = is.read(maxBuffer))) { // read方法并不保证一次能读取1024*64个字节
	            length += lengthTemp;
	            if (length >= 1024 * 64) {
	                //logger.debug("读入的数据超过1024 * 64");
	                break;
	            }
	        }

	        byte[] endodedData = new byte[length];
	        System.arraycopy(maxBuffer, 0, endodedData, 0, length);

	        //logger.info("receiveData:" + DataUtil.bytesToHexString(endodedData) + '\n');
	        return endodedData;
	    }
}
