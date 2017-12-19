package com.ericsson.li.db;


import java.io.Serializable;
import java.lang.reflect.Field;  
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.ResultSetMetaData;  
import java.sql.SQLException;  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
  

class UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	private String passwd;
	
	public UserInfo() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", username=" + name + ", pswd="
				+ passwd + "]";
	}
}
  
public class JdbcUtils {  
/*    private static final String USERNAME = "litx";  
    private static final String PASSWORD = "xtil";  
    private static final String DRIVER = "com.mysql.jdbc.Driver";  
    private static final String URL = "jdbc:mysql://192.168.88.128:3306/test";  */
    
    private static final String USERNAME = "litx";  
    private static final String PASSWORD = "litx";  
    private static final String DRIVER = "org.postgresql.Driver";  
    private static final String URL = "jdbc:postgresql://192.168.88.128:5432/litx";  
    
    private Connection connection;  
    private PreparedStatement pstmt;  
    private ResultSet resultSet;  
    public JdbcUtils() {  
        // TODO Auto-generated constructor stub  
        try{  
            Class.forName(DRIVER);  
            System.out.println("��ݿ����ӳɹ���");  
  
        }catch(Exception e){  
  
        }  
    }  
      
    /** 
     * @return 
     */  
    public Connection getConnection(){  
        try {  
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);  
        } catch (SQLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return connection;  
    }  
  
      
    /** 
     * @param sql 
     * @param params 
     * @return 
     * @throws SQLException 
     */  
    public boolean updateByPreparedStatement(String sql, List<Object>params)throws SQLException{  
        boolean flag = false;  
        int result = -1;  
        pstmt = connection.prepareStatement(sql);  
        int index = 1;  
        if(params != null && !params.isEmpty()){  
            for(int i=0; i<params.size(); i++){  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        result = pstmt.executeUpdate();  
        flag = result > 0 ? true : false;  
        return flag;  
    }  
  
    /** 
     * 
     * @param sql 
     * @param params 
     * @return 
     * @throws SQLException 
     */  
    public Map<String, Object> findSimpleResult(String sql, List<Object> params) throws SQLException{  
        Map<String, Object> map = new HashMap<String, Object>();  
        int index  = 1;  
        pstmt = connection.prepareStatement(sql);  
        if(params != null && !params.isEmpty()){  
            for(int i=0; i<params.size(); i++){  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        resultSet = pstmt.executeQuery();//���ز�ѯ���  
        ResultSetMetaData metaData = resultSet.getMetaData();  
        int col_len = metaData.getColumnCount();  
        while(resultSet.next()){  
            for(int i=0; i<col_len; i++ ){  
                String cols_name = metaData.getColumnName(i+1);  
                Object cols_value = resultSet.getObject(cols_name);  
                if(cols_value == null){  
                    cols_value = "";  
                }  
                map.put(cols_name, cols_value);  
            }  
        }  
        return map;  
    }  
  
    /**
     * @param sql 
     * @param params 
     * @return 
     * @throws SQLException 
     */  
    public List<Map<String, Object>> findModeResult(String sql, List<Object> params) throws SQLException{  
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        int index = 1;  
        pstmt = connection.prepareStatement(sql);  
        if(params != null && !params.isEmpty()){  
            for(int i = 0; i<params.size(); i++){  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        resultSet = pstmt.executeQuery();  
        ResultSetMetaData metaData = resultSet.getMetaData();  
        int cols_len = metaData.getColumnCount();  
        while(resultSet.next()){  
            Map<String, Object> map = new HashMap<String, Object>();  
            for(int i=0; i<cols_len; i++){  
                String cols_name = metaData.getColumnName(i+1);  
                Object cols_value = resultSet.getObject(cols_name);  
                if(cols_value == null){  
                    cols_value = "";  
                }  
                map.put(cols_name, cols_value);  
            }  
            list.add(map);  
        }  
  
        return list;  
    }  
  
    /**
     * @param sql 
     * @param params 
     * @param cls 
     * @return 
     * @throws Exception 
     */  
    public <T> T findSimpleRefResult(String sql, List<Object> params,  
            Class<T> cls )throws Exception{  
        T resultObject = null;  
        int index = 1;  
        pstmt = connection.prepareStatement(sql);  
        if(params != null && !params.isEmpty()){  
            for(int i = 0; i<params.size(); i++){  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        resultSet = pstmt.executeQuery();  
        ResultSetMetaData metaData  = resultSet.getMetaData();  
        int cols_len = metaData.getColumnCount();  
        while(resultSet.next()){  
            //ͨ������ƴ���һ��ʵ��  
            resultObject = cls.newInstance();  
            for(int i = 0; i<cols_len; i++){  
                String cols_name = metaData.getColumnName(i+1);  
                Object cols_value = resultSet.getObject(cols_name);  
                if(cols_value == null){  
                    cols_value = "";  
                }  
                Field field = cls.getDeclaredField(cols_name);  
                field.setAccessible(true); //��javabean�ķ���Ȩ��  
                field.set(resultObject, cols_value);  
            }  
        }  
        return resultObject;  
  
    }  
  
    /**
     * @param sql  
     * @param params 
     * @param cls 
     * @return 
     * @throws Exception 
     */  
    public <T> List<T> findMoreRefResult(String sql, List<Object> params,  
            Class<T> cls )throws Exception {  
        List<T> list = new ArrayList<T>();  
        int index = 1;  
        pstmt = connection.prepareStatement(sql);  
        if(params != null && !params.isEmpty()){  
            for(int i = 0; i<params.size(); i++){  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        resultSet = pstmt.executeQuery();  
        ResultSetMetaData metaData  = resultSet.getMetaData();  
        int cols_len = metaData.getColumnCount();  
        while(resultSet.next()){  
            T resultObject = cls.newInstance();  
            for(int i = 0; i<cols_len; i++){  
                String cols_name = metaData.getColumnName(i+1);  
                Object cols_value = resultSet.getObject(cols_name);  
                if(cols_value == null){  
                    cols_value = "";  
                }  
                Field field = cls.getDeclaredField(cols_name);  
                field.setAccessible(true); //��javabean�ķ���Ȩ��  
                field.set(resultObject, cols_value);  
            }  
            list.add(resultObject);  
        }  
        return list;  
    }  
  
    /** 
     */  
    public void releaseConn(){  
        if(resultSet != null){  
            try{  
                resultSet.close();  
            }catch(SQLException e){  
                e.printStackTrace();  
            }  
        }  
    }  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) throws SQLException {  
        // TODO Auto-generated method stub  
        JdbcUtils jdbcUtils = new JdbcUtils();  
        jdbcUtils.getConnection();  
  
        /*******************��*********************/  
/*        String sql = "insert into userinfo (name, passwd) values (?, ?), (?, ?), (?, ?)"; 
        List<Object> params = new ArrayList<Object>(); 
        params.add("100"); 
        params.add("123xiaoming"); 
        params.add("101"); 
        params.add("zhangsan"); 
        params.add("102"); 
        params.add("lisi000"); 
        try { 
            boolean flag = jdbcUtils.updateByPreparedStatement(sql, params); 
            System.out.println(flag); 
        } catch (SQLException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        }  
  */
  
        /*******************ɾ*********************/  
/*       String sql = "delete from userinfo where name = ?"; 
        List<Object> params = new ArrayList<Object>(); 
        params.add("wangwu"); 
        boolean flag = jdbcUtils.updateByPreparedStatement(sql, params);*/ 
  
        /*******************��*********************/  
        /*      String sql = "update userinfo set passwd = ? where name = ? "; 
        List<Object> params = new ArrayList<Object>(); 
        params.add("lisi88888"); 
        params.add("����"); 
        boolean flag = jdbcUtils.updateByPreparedStatement(sql, params); 
        System.out.println(flag);*/  
  
        /****************************************/  

        /*      String sql2 = "select * from userinfo "; 
        List<Map<String, Object>> list = jdbcUtils.findModeResult(sql2, null); 
        System.out.println(list);*/  
  
        String sql = "select * from userinfo where name = ? ";  
        List<Object> params = new ArrayList<Object>();  
        params.add("张三");  
        UserInfo userInfo;  
        try {  
            userInfo = jdbcUtils.findSimpleRefResult(sql, params, UserInfo.class);  
            System.out.print(userInfo);  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
  
  
    }  
  
} 