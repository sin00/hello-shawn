package com.ericsson.li.bigdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;  
import java.util.Arrays;
import java.util.Properties;

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
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;







public class AggHbaseTest { 
	private static String inFile;
	private static String outFile;
	private static FileOutputStream fileoutputstream;
	private static String tableName;
	private static int dimensionCnt;
	private static int idxvalueCnt;
	
	private static boolean limit;
	private static String lkey;
	private static String lstattime;
	private static String laggrule;
	private static String lcity;
	
	private static String optlist[];
	
	private static Configuration conf;
	private static Logger logger = Logger.getLogger(AggHbaseTest.class); 
	//public static Properties properties;
	//=============================================//
	private static final String DIMMNESION_RF = "dimension";
	private static final String IDXVALUE_RF = "idxvalue";
	private static final String VC_CITY_DEF = "vc_city";
	private static final String VC_STATTIME_DEF = "vc_stattime";
	private static final String VC_AGG_RULE_DEF = "vc_agg_rule";
	private static final String DIMMNESION_DEF = "vc_dimension";
	private static final String IDXVALUE_DEF = "dec_idxvalue";
	

	public static void main(String[] args) throws IOException {  
		Properties properties  = new Properties();
		properties.load(new FileInputStream(new File(args[0])));
		init(properties);
		run();
    }  
  
	public static void init(Properties properties) {
		inFile = properties.getProperty("infile");
		outFile = properties.getProperty("outfile");
		tableName = properties.getProperty("tableName");
		dimensionCnt = Integer.parseInt(properties.getProperty("dimensioncnt"));
		idxvalueCnt = Integer.parseInt(properties.getProperty("idxvaluecnt"));
		optlist = properties.getProperty("optlist").split("\\|");
		
		//limit = 
		//lkey = Boolean.parseBoolean(s)properties.getProperty("key");
		limit = properties.getProperty("limit").equals("1");
		lkey = properties.getProperty("key");
		lstattime = properties.getProperty("stattime");
		laggrule = properties.getProperty("aggrule");
		lcity = properties.getProperty("city");
		
		PropertyConfigurator.configure(properties.getProperty("log4j")); 
		
        conf = HBaseConfiguration.create();  
        conf.set("hbase.rootdir", "hdfs://172.20.33.21:9000/hbase");  
        // ʹ��eclipseʱ�����������������޷���λ  
        conf.set("hbase.zookeeper.property.clientPort", "12181"); 
        conf.set("hbase.zookeeper.quorum", "172.20.33.25");
        //conf.set("hbase.rootdir", "hdfs://10.200.2.44:9000/hbase");  
        // ʹ��eclipseʱ�����������������޷���λ  
        //conf.set("hbase.zookeeper.property.clientPort", "2181"); 
        //conf.set("hbase.zookeeper.quorum", "10.200.2.44");
	}
	
	public static void run() throws IOException {
		File file = new File(outFile);
		if (!file.exists()) {
			file.createNewFile();
		}
		fileoutputstream = new FileOutputStream(file, false);
		for (String opt : optlist) {
			if ("get".equals(opt)) {
				get();
				continue;
			}
			//��������
			if ("create".equals(opt)) {
				createTable();
				continue;
			}
			
			if ("insert".equals(opt)) {
				insert();
				continue;
			}
			
			if ("delete".equals(opt)) {
				delete();
				continue;
			}
			
			if ("scan".equals(opt)) {
				scan();
				continue;
			}
		}
		fileoutputstream.close();
	}
	
    // hbase�����ر�  
    private static Configuration getConfiguration() {  
        Configuration conf = HBaseConfiguration.create();  
        conf.set("hbase.rootdir", "hdfs://172.20.33.21:9000/hbase");  
        // ʹ��eclipseʱ�����������������޷���λ  
        conf.set("hbase.zookeeper.property.clientPort", "12181"); 
        conf.set("hbase.zookeeper.quorum", "172.20.33.25");
        return conf;  
    }  
 
    public static void createTable() {
    	logger.info("begin create table:"+tableName);
		try {
			HBaseAdmin hBaseAdmin = new HBaseAdmin(conf);
			if (!hBaseAdmin.tableExists(tableName)) {
				logger.info(tableName + "not exist, need create!");
				HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName));

				tableDesc.addFamily(new HColumnDescriptor(DIMMNESION_RF));
				tableDesc.addFamily(new HColumnDescriptor(IDXVALUE_RF));

				hBaseAdmin.createTable(tableDesc);
				logger.info("succeed to create table:" + tableName);

			}		
			hBaseAdmin.close();
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("end create table:"+tableName);
	}
    
    public static void insert() throws IOException {
    	logger.info("begin insert table:"+tableName);
    	BufferedReader br = null;
        HTable table = new HTable(conf, tableName);  
		String line = null;
		try {
			br = new BufferedReader(new FileReader(inFile));
			while ((line = br.readLine()) != null) {
				logger.debug(line);
				insert(table, line.trim());
			}
		} catch (IOException e) {
			logger.warn("Caught exception while parsing the cached file:" + inFile);
		} finally {
	        table.close();
			
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		logger.info("end insert table:"+tableName);
    }
    // ���һ����¼  
    public static void insert(HTable table, String line) throws IOException {
    	if (line.length() < 1) {
    		return;
    	}
    	String[] idx = line.split("\\s+");
    	String stattime = idx[0];
    	String city = idx[1];
    	String[] dimensions = Arrays.copyOfRange(idx, 2, 2+dimensionCnt);
    	String[] idxvalues = Arrays.copyOfRange(idx, 2+dimensionCnt, 2+dimensionCnt+idxvalueCnt);

    	StringBuilder sb = new StringBuilder();
		sb.append(stattime).append("^");
		sb.append(city).append("^");
		

					
    	for (int i = 0; i < dimensionCnt; i++) {
    		sb.append(dimensions[i]).append("^");
    	}
    	
    	String row = sb.toString();
    	logger.debug(row);
    	

        Put put = new Put(Bytes.toBytes(row));  
        
    	put.add(Bytes.toBytes(DIMMNESION_RF), Bytes.toBytes(VC_STATTIME_DEF), Bytes  
                .toBytes(stattime));  
    	put.add(Bytes.toBytes(DIMMNESION_RF), Bytes.toBytes(VC_CITY_DEF), Bytes  
                .toBytes(city));
    	for (int i = 0; i < dimensionCnt; i++) {
    		String dimension = DIMMNESION_DEF+(i+1);
    		logger.debug(dimension+":"+dimensions[i]);
    		put.add(Bytes.toBytes(DIMMNESION_RF), Bytes.toBytes(dimension), Bytes  
                    .toBytes(dimensions[i]));
    	}
    	
    	for (int i = 0; i < idxvalueCnt; i++) { 		
    		String idxvalue = IDXVALUE_DEF+(i+1);
    		logger.debug(idxvalue+":"+idxvalues[i]);
    		put.add(Bytes.toBytes(IDXVALUE_RF), Bytes.toBytes(idxvalue), Bytes  
                    .toBytes(Double.parseDouble(idxvalues[i])));
    	}
    	
    	table.put(put);
  
    }  
  
    public static boolean outResult(Result r, StringBuffer sb, boolean limit) {
    	String key =Bytes.toString(r.getRow());
		String city = Bytes.toString(r.getValue(Bytes.toBytes(DIMMNESION_RF),
				Bytes.toBytes(VC_CITY_DEF)));
		String stattime = Bytes.toString(r.getValue(Bytes.toBytes(DIMMNESION_RF),
				Bytes.toBytes(VC_STATTIME_DEF)));
		String aggrule = Bytes.toString(r.getValue(Bytes.toBytes(DIMMNESION_RF),
				Bytes.toBytes(VC_AGG_RULE_DEF)));
		
		if (limit) {
			if ((lkey != null && lkey.length() > 0) && (!key.equals(lkey))) {
				logger.info("key filter: "+ key);
				return false;
			}
			if ((lcity != null && lcity.length() > 0) && (!city.equals(lcity))) {
				logger.info("city filter: "+ city);
				return false;
			}
			if ((lstattime != null && lstattime.length() > 0) && (!stattime.equals(lstattime))) {
				logger.info("stattime filter: "+ stattime);
				return false;
			}
			if ((laggrule != null && laggrule.length() > 0) && (!aggrule.equals(laggrule))) {
				logger.info("aggrule filter: " + aggrule);
				return false;
			}
		}
		
		if (sb != null) {
			sb.append("key: ").append(key).append("\n");
			sb.append("aggrule: ").append(aggrule).append("\n");
			sb.append("city: ").append(city).append("\n");
			sb.append("stattime: ").append(stattime).append("\n");
		}
		logger.debug("key: " + key);
		logger.debug("city: " + city);
		logger.debug("stattime: " + stattime);
		
		for (int i = 0; i < dimensionCnt; i++) {
			String dimension = DIMMNESION_DEF + (i + 1);
			byte[] cn = r.getValue(Bytes.toBytes(DIMMNESION_RF),
					Bytes.toBytes(dimension));
			logger.debug(dimension + ":" + Bytes.toString(cn));
			if (sb != null) {
				sb.append(dimension).append(": ").append(Bytes.toString(cn)).append("\n");
			}
		}

		for (int i = 0; i < idxvalueCnt; i++) {
			String idxvalue = IDXVALUE_DEF + (i + 1);
			byte[] cn = r.getValue(Bytes.toBytes(IDXVALUE_RF),
					Bytes.toBytes(idxvalue));
			logger.debug(idxvalue + ":" + Bytes.toDouble(cn));
			if (sb != null) {
				sb.append(idxvalue).append(": ").append(Bytes.toDouble(cn)).append("\n");
			}
		}
		
		return true;
    }
    // ��ȡһ����¼  
    public static void get() throws IOException { 
    	StringBuffer sb = new StringBuffer();
    	logger.info("begin get row["+lkey+"] table:"+tableName);
    	if (lkey == null || lkey.length() == 0) {
    		logger.info("need cfg key filter");
    		return;
    	}
        HTable table = new HTable(conf, tableName);  
        Get get = new Get(Bytes.toBytes(lkey));  
        
        sb.setLength(0);
        sb.append("=====get row[").append(lkey).append("]=======\n");
        
        outResult(table.get(get), sb, false);  
        //logger.info(result.toString());  
        table.close();
        fileoutputstream.write(sb.toString().getBytes("utf-8"));//ע����Ҫת����Ӧ���ַ�
        logger.info("end get row["+lkey+"] table:"+tableName);
    }  
  
    // ��ʾ�������  
    public static void scan() throws IOException {  
    	logger.info("begin scan table:"+tableName);
		
		StringBuffer sb = new StringBuffer();
		
        HTable table = new HTable(conf, tableName);  
        Scan scan = new Scan();
        
        ResultScanner scanner = table.getScanner(scan);  
        int count = 0;
        for (Result r : scanner) {
        	sb.setLength(0);
        	sb.append("============��[").append(++count).append("]����¼============").append("\n");
			logger.debug("============��["+(count)+"]����¼============");
			outResult(r, sb, limit);
			fileoutputstream.write(sb.toString().getBytes("utf-8"));//ע����Ҫת����Ӧ���ַ�
        } 
        scanner.close();
        table.close();
        fileoutputstream.close();
        logger.info("end scan table:"+tableName);
    }  
  
    // ɾ���  
    public static void delete() throws IOException { 
    	logger.info("end delete table:"+tableName);
        HBaseAdmin admin = new HBaseAdmin(conf);  
        if (admin.tableExists(tableName)) {  
            try {  
                admin.disableTable(tableName);  
                admin.deleteTable(tableName);  
            } catch (IOException e) {  
                e.printStackTrace();  
                logger.info("delete " + tableName + " ʧ��");  
            }  
        }  
        admin.close();
        logger.info("end delete table:"+tableName);
    }  
}  