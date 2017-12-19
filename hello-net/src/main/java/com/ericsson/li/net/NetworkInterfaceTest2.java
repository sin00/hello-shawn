package com.ericsson.li.net;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
 
public class NetworkInterfaceTest2 {
    public static void main(String[] args) throws SocketException {
       Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
       for(;nis.hasMoreElements();)
       {
           NetworkInterface ni = nis.nextElement();
           Enumeration<InetAddress> ias = ni.getInetAddresses();
           for(;ias.hasMoreElements();)
           {
              InetAddress ia = ias.nextElement();
              if(ia instanceof Inet4Address && !ia.getHostAddress().equals("127.0.0.1"))
              {
                  System.out.println("ip v4 : "+ia);
              }else if(ia instanceof Inet6Address && !ia.equals(""))
              {
                  System.out.println("ip v6 : "+ ia);
              }
           }
          
       }
    }
}
