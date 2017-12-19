package com.ericsson.li.bigdata;

import java.io.IOException;  

import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.hbase.HBaseConfiguration;  
import org.apache.hadoop.hbase.HColumnDescriptor;  
import org.apache.hadoop.hbase.HTableDescriptor;  
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;  
import org.apache.hadoop.hbase.client.HBaseAdmin;  
import org.apache.hadoop.hbase.client.HTable;  
import org.apache.hadoop.hbase.client.Put;  
import org.apache.hadoop.hbase.client.Result;  
import org.apache.hadoop.hbase.client.ResultScanner;  
import org.apache.hadoop.hbase.client.Scan;  
import org.apache.hadoop.hbase.util.Bytes;  
import org.apache.hadoop.hbase.client.HBaseAdmin;


public class HbaseDemo {  
  
    public static void main(String[] args) throws IOException {  
        String tableName = "wlan_log";  
        String columnFamily = "cf";  
        System.out.println("1==========================\n");  
        //HbaseDemo.create(tableName, columnFamily);  
  
         //HbaseDemo.put(tableName, "row1", columnFamily, "cl1", "data");  
        // HbaseDemo.get(tableName, "row1");  
         HbaseDemo.scan(tableName);  
        // HbaseDemo.delete(tableName);  
    }  
  
    // hbase�����ر�  
    private static Configuration getConfiguration() {  
        Configuration conf = HBaseConfiguration.create();  
        conf.set("hbase.rootdir", "hdfs://172.20.33.21:9000/hbase");  
        // ʹ��eclipseʱ�����������������޷���λ  
        conf.set("hbase.zookeeper.property.clientPort", "12181"); 
        conf.set("hbase.zookeeper.quorum", "172.20.33.21");
        return conf;  
    }  
    
    public void createTable1(String tableName) {
		try {
			HBaseAdmin hBaseAdmin = new HBaseAdmin(getConfiguration());
			if (!hBaseAdmin.tableExists(tableName)) {
				System.out.println(tableName + "not exist, need create!");
				HTableDescriptor tableDesc = new HTableDescriptor(
						TableName.valueOf(tableName));

				tableDesc.addFamily(new HColumnDescriptor("DIMMNESION_RF"));
				tableDesc.addFamily(new HColumnDescriptor("IDXVALUE_RF"));

				hBaseAdmin.createTable(tableDesc);
				System.out.println("succeed to create table:" + tableName);

			}
			
			hBaseAdmin.close();
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("end create table ......");
	}
    
    public void createTable(String tableName) {
		try {
			HBaseAdmin hBaseAdmin = new HBaseAdmin(getConfiguration());
			if (!hBaseAdmin.tableExists(tableName)) {
				System.out.println(tableName + "not exist, need create!");
				HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName));

				tableDesc.addFamily(new HColumnDescriptor("DIMMNESION_RF"));
				tableDesc.addFamily(new HColumnDescriptor("AggDef.IDXVALUE_RF"));

				hBaseAdmin.createTable(tableDesc);
				System.out.println("succeed to create table:" + tableName);

			}
			
			hBaseAdmin.close();
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("end create table ......");
	}
  
    // ����һ�ű�  
    public static void create(String tableName, String columnFamily)  
            throws IOException {  
        HBaseAdmin admin = new HBaseAdmin(getConfiguration());  
        if (admin.tableExists(tableName)) {  
            System.out.println("table exists!");  
        } else {  
            HTableDescriptor tableDesc = new HTableDescriptor(tableName);  
            tableDesc.addFamily(new HColumnDescriptor(columnFamily));  
            System.out.println("===========3");
            admin.createTable(tableDesc);  
            System.out.println("create table success!");  
        }  
        System.out.println("===========4");
    }  
  
    // ���һ����¼  
    public static void put(String tableName, String row, String columnFamily,  
            String column, String data) throws IOException {  
        HTable table = new HTable(getConfiguration(), tableName);  
        Put p1 = new Put(Bytes.toBytes(row));  
        p1.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes  
                .toBytes(data));  
        table.put(p1);  
        System.out.println("put'" + row + "'," + columnFamily + ":" + column  
                + "','" + data + "'");  
    }  
  
    // ��ȡһ����¼  
    public static void get(String tableName, String row) throws IOException {  
        HTable table = new HTable(getConfiguration(), tableName);  
        Get get = new Get(Bytes.toBytes(row));  
        Result result = table.get(get);  
        System.out.println("Get: " + result.toString());  
    }  
  
    // ��ʾ�������  
    public static void scan(String tableName) throws IOException {  
       /* HTable table = new HTable(getConfiguration(), tableName);  
        Scan scan = new Scan();  
        ResultScanner scanner = table.getScanner(scan);  
        for (Result result : scanner) {  
            System.out.println("Scan: " + result);  
        }  */
    	
        HTable table = new HTable(getConfiguration(), tableName);  
        Scan scan = new Scan();  
        ResultScanner scanner = table.getScanner(scan);  
        for (Result r : scanner) {  
        	System.out.println(r);
                byte[] key = r.getRow();
                byte[] c1 = r.getValue(Bytes.toBytes("cf"), Bytes.toBytes("cll"));
                byte[] c2 = r.getValue(Bytes.toBytes("cf"), Bytes.toBytes("cl2"));
                byte[] c3 = r.getValue(Bytes.toBytes("cf"), Bytes.toBytes("cl3"));
                System.out.println("Scan:==========================\n");  
                System.out.println("key: " + Bytes.toString(key) + "\n");  
                System.out.println("cf:c1: " + Bytes.toString(c1) + "\n");  
                System.out.println("cf:c2: " + Bytes.toString(c2) + "\n");  
                System.out.println("cf:c3: " + Bytes.toString(c3) + "\n");  
        } 
    }  
  
    // ɾ���  
    public static void delete(String tableName) throws IOException {  
        HBaseAdmin admin = new HBaseAdmin(getConfiguration());  
        if (admin.tableExists(tableName)) {  
            try {  
                admin.disableTable(tableName);  
                admin.deleteTable(tableName);  
            } catch (IOException e) {  
                e.printStackTrace();  
                System.out.println("Delete " + tableName + " ʧ��");  
            }  
        }  
        System.out.println("Delete " + tableName + " �ɹ�");  
    }  
  
}  