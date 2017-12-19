package com.ericsson.li.cassandra;

import com.datastax.driver.core.Cluster;  
import com.datastax.driver.core.ResultSet;  
import com.datastax.driver.core.Row;  
import com.datastax.driver.core.Session;  
import com.datastax.driver.core.querybuilder.Insert;  
import com.datastax.driver.core.querybuilder.QueryBuilder;  
import com.datastax.driver.core.querybuilder.Select.Where;  
  
public class CassandraBuilder {  
      
    public static void main( String[] args )  
    {  
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
             
           //新增数据  
           Insert insert = QueryBuilder.insertInto("keyspace1", "student").value("name", "lisi").value("age", 11);  
           session.execute(insert);  
           System.out.println("插入语句:   "+insert);  
             
            
           System.out.println("查询数据");  
           //查询数据  
           Where select = QueryBuilder.select().all().from("keyspace1", "student").where(QueryBuilder.eq("name", "lisi"));  
           ResultSet rs = session.execute(select);  
           for(Row row : rs.all()){  
               System.out.println("=>name: "+row.getString("name"));  
               System.out.println("=>age: "+row.getInt("age"));  
           }  
           System.out.println("查询语句:   "+select);  
             
             
           System.out.println("修改数据");  
           //修改数据  
           com.datastax.driver.core.querybuilder.Update.Where update = QueryBuilder.update("keyspace1", "student").with(QueryBuilder.set("age", 21)).where(QueryBuilder.eq("name", "lisi"));  
           session.execute(update);  
           rs = session.execute(select);  
           for(Row row : rs.all()){  
               System.out.println("=>name: "+row.getString("name"));  
               System.out.println("=>age: "+row.getInt("age"));  
           }  
           System.out.println("修改语句:   "+update);  
             
             
           System.out.println("删除数据");  
            //删除数据  
           com.datastax.driver.core.querybuilder.Delete.Where delete =  QueryBuilder.delete().from("keyspace1","student").where(QueryBuilder.eq("name", "lisi"));  
           session.execute(delete);  
           rs = session.execute(select);  
           for(Row row : rs.all()){  
               System.out.println("=>name: "+row.getString("name"));  
               System.out.println("=>age: "+row.getInt("age"));  
           }  
           System.out.println("删除语句:   "+delete);  
             
        } catch (Exception e) {  
          
            e.printStackTrace();  
        }finally{  
          
            //关闭Session和Cluster  
            session.close();  
            cluster.close();  
        }  
                 
    }  
      
}  
