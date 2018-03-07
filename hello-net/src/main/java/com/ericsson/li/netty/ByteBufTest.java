package com.ericsson.li.netty;

import io.netty.buffer.ByteBuf;  
import io.netty.buffer.Unpooled;  
  
  
public class ByteBufTest {  
  
    public static void main(String[] args) {  
      
        //实例初始化  
        ByteBuf buffer =   Unpooled.buffer(100);  
        String value ="ByteBufTest";  
        buffer.writeBytes(value.getBytes());  
        System.out.println("readerIndex1:"+buffer.readerIndex());  
        System.out.println("writerIndex1:"+buffer.writerIndex());  
        byte[] vArray = new byte[buffer.writerIndex()];  
        buffer.readBytes(vArray);  
        System.out.println("readerIndex2:"+buffer.readerIndex());  
        System.out.println("writerIndex2:"+buffer.writerIndex());  
        System.out.println(new String(vArray));  
        buffer.writeBytes("hello".getBytes());
        byte[] a = new byte[2];
        buffer.readBytes(a);
        System.out.println("readerIndex3:"+buffer.readerIndex());  
        System.out.println("writerIndex3:"+buffer.writerIndex());   
          
    }  
  
}
