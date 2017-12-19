package com.ericsson.li.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
 
public class NetworkInterfaceTest {
    public static void main(String[] args) throws SocketException {
       NetworkInterface ni = NetworkInterface.getByName("eth0");
       Enumeration<InetAddress> ias = ni.getInetAddresses();
       for(;ias.hasMoreElements();)
       {
           InetAddress ia = ias.nextElement();
           if(ia instanceof InetAddress)
              System.out.println(ia);
       }
    }
}
