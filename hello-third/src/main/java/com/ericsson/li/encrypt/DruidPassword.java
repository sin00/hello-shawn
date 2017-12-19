package com.ericsson.li.encrypt;

import com.alibaba.druid.filter.config.ConfigTools;

public class DruidPassword {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//密码明文        
		String password = "123456";        
		System.out.println("密码[ "+password+" ]的加密信息如下：\n");        
		String [] keyPair = ConfigTools.genKeyPair(512);        
		//私钥        
		String privateKey = keyPair[0];        
		//公钥        
		String publicKey = keyPair[1];        
		//用私钥加密后的密文        
		password = ConfigTools.encrypt(privateKey, password);       
		System.out.println("privateKey:"+privateKey);        
		System.out.println("publicKey:"+publicKey);        
		System.out.println("encrypt-password:"+password);
		System.out.println("decrypt-password:"+ConfigTools.decrypt(publicKey, password));
		
		String aa = "12345678";
		String bb = ConfigTools.encrypt(aa);
		System.out.println("aa:"+aa);        
		System.out.println("bb:"+bb); 
		System.out.println("bb->aa:"+ConfigTools.decrypt(bb)); 
		
		ConfigTools.main(new String[] {"12345678"});
		
	}

}
