package com.ericsson.li.cassandra;

import java.util.List;  

import com.datastax.driver.core.Cluster;  
import com.datastax.driver.core.PreparedStatement;  
import com.datastax.driver.core.ResultSet;  
import com.datastax.driver.core.Row;  
import com.datastax.driver.core.Session;  
  
public class CassandraPreCQL {  
  
    public static void main(String[] args) {  
        Cluster cluster = null;  
        Session session = null;  
          
        try {  
              
            //定义一个Cluster类  
           cluster = Cluster.builder().addContactPoint("192.168.88.128").build();  
              
            //需要获取Session对象  
           session = cluster.connect();  
               
            //创建键空间  
           String createKeySpaceCQL = "create keyspace if not exists keyspace1 with replication={'class':'SimpleStrategy', 'replication_factor': 1}";  
           session.execute(createKeySpaceCQL);  
             
           //创建列族  
           String createTableCQL = "create table if not exists keyspace1.student(name varchar primary key, age int)";  
           session.execute(createTableCQL);             
             
            //插入数据  
            PreparedStatement statement = session.prepare("insert into keyspace1.student(name,age) values(?,?)");  
            session.execute(statement.bind("zhangsan",40));  
              
            //查询,修改,删除数据  
            String queryCQL = "select * from keyspace1.student";  
            ResultSet rs = session.execute(queryCQL);  
            List<Row> dataList = rs.all();  
            for (Row row : dataList) {  
                System.out.println("==>name: "+ row.getString("name"));  
                System.out.println("==>age: "+row.getInt("age"));  
            }  
              
              
        } catch (Exception e) {  
          
            e.printStackTrace();  
        }finally{  
          
            //关闭Session和Cluster  
            session.close();  
            cluster.close();  
        }  
                 
  
    }  
  
}  
