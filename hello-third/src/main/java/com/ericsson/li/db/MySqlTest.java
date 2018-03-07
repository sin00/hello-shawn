package com.ericsson.li.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;

import java.sql.*;

public class MySqlTest {

	public static void main(String[] args) {

		// �������
		String driver = "com.mysql.jdbc.Driver";

		// URLָ��Ҫ���ʵ���ݿ���scutcs
		//String url = "jdbc:mysql://172.20.33.21:3306/rapdb";
		String url = "jdbc:mysql://192.168.88.128:3306/test";
		// MySQL����ʱ���û���
		String user = "root";

		// MySQL����ʱ������
		String password = "root";

		try {
			// ���������
			Class.forName(driver);
			System.out.println("-----------------1");
			// ������ݿ�
			Connection conn = DriverManager.getConnection(url, user, password);
			System.out.println("-----------------2");

			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			System.out.println("-----------------3");

			// statement����ִ��SQL���
			Statement statement = conn.createStatement();

			// Ҫִ�е�SQL���
			String sql = "select * from test";

			// ���
			ResultSet rs = statement.executeQuery(sql);

			System.out.println("-----------------");

			String name = null;

			while (rs.next()) {

				// ѡ��sname�������
				name = rs.getString("vc_idxname");

				// ����ʹ��ISO-8859-1�ַ�name����Ϊ�ֽ����в������洢�µ��ֽ������С�
				// Ȼ��ʹ��GB2312�ַ����ָ�����ֽ�����
				name = new String(name.getBytes("ISO-8859-1"), "GB2312");

				// ������
				System.out.println(rs.getString("vc_idxid") + "\t" + name);
			}

			rs.close();
			conn.close();

		} catch (ClassNotFoundException e) {

			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
	} 
}


class MySqlTest1 {
    //public static String url = "jdbc:mysql://localhost:3306/test";//characterEncoding=GBK
	public static String url ="jdbc:mysql://172.20.33.21:3306/rapdb";
    public static String username = "rap";
    public static String password = "rap";
    public static Connection con;
    public static Statement stmt;
    public static ResultSet rs;
    
    public static void main(String[] args) throws SQLException {
        connect();
        operation();
        stmt.close();
        con.close();
    }
    public static void operation() {
        String sql_select = "select * from bam_idx_def where vc_idxid=CM-01-0001-0002";
        //String sql_insert = "insert into tablename (col1,col2..) values('1','2'...)";
        //String sql_update = "update tablename set colname='update' where id=1";
        //insert(sql_insert);
        select(sql_select);
        //update(sql_update);
    }
    public static void connect() {
        // ��λ��
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("������ɹ�!"); 
        } catch (ClassNotFoundException e) {
            System.out.println("������ʧ��!");
            e.printStackTrace();
        }
        // ��������
        try {
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            System.out.println("��ݿ����ӳɹ�!"); 
        } catch(SQLException e) {
            System.out.println("��ݿ�����ʧ��!"); 
        }
    }
    public static void select(String sql) {
        try {
            rs = stmt.executeQuery(sql);
            ResultSetMetaData meta_data = rs.getMetaData();//����
            for (int i_col = 1; i_col <= meta_data.getColumnCount(); i_col++) {
                System.out.print(meta_data.getColumnLabel(i_col) + "   ");
            }
            System.out.println();
            while (rs.next()) {
                for (int i_col = 1; i_col <= meta_data.getColumnCount(); i_col++) {
                    System.out.print(rs.getString(i_col) + "  ");
                }
                System.out.println();
            }
            rs.close();
        }catch (Exception e) {
            System.out.println("��ݲ�ѯʧ��!");
        }
    }
    public static void insert(String sql) {
        try {
            stmt.clearBatch();
            stmt.addBatch(sql);
            stmt.executeBatch();
            System.out.println("��ݲ���ɹ�!");
        }catch (Exception e) {
            System.out.println("��ݲ���ʧ��!");
        }
        
    }
    public static void update(String sql) {
        try {
            stmt.executeUpdate(sql);
            System.out.println("��ݸ��³ɹ�!");
        }catch (Exception e) {
            System.out.println("��ݸ���ʧ��!");
        }
    }
}
