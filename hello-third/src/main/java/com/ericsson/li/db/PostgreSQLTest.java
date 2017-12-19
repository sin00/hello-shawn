package com.ericsson.li.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import java.sql.SQLException;
import java.sql.ResultSetMetaData;

import java.sql.*;

public class PostgreSQLTest {
	private static Connection conn = null;
	public static boolean connect()  {
		String driver = "org.postgresql.Driver";
		String url = "jdbc:postgresql://192.168.88.128:5432/litx";
		String user = "litx";
		String password = "litx";
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean disConnect()  {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean reConnect() {
		disConnect();
		return connect();
	}
	
	public static void do3() {		
		try {
			System.out.println("hhhhhhhhh");
			connect();

			String strTime = "2016-01-15T00:00:00+04:00";
			//String strTime = "2016-01-15T00:00:00";
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			//TimeZone tz = TimeZone.getTimeZone("GMT+8:00");
			//TimeZone tz = TimeZone.getTimeZone("+5:00");
			//sdf.setTimeZone(TimeZone.getTimeZone("+5:00"));
			//sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
			sdf.setTimeZone(TimeZone.getTimeZone("GMT" + strTime.substring(19)));
			java.util.Date date = sdf.parse(strTime);
			java.sql.Timestamp tt = new java.sql.Timestamp(date.getTime());
			
			String sql = "insert into test_user(id, name, passwd, insert_time) values(?,?,?,?)";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setObject(1, 5);
			pstmt.setObject(2, "huba");
			pstmt.setObject(3, "hb");
			pstmt.setObject(4, tt);
			
			//KeyHolder keyHolder = new GeneratedKeyHolder();
			
            if (pstmt.executeUpdate() == 0) {
            	System.out.println("update executeUpdate 0["+ sql + "]");
            }
            pstmt.close();

			conn.close();

		} catch (SQLException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
	} 
	
	public static void do2() {
		String driver = "org.postgresql.Driver";
		String url = "jdbc:postgresql://192.168.88.128:5432/litx";
		String user = "litx";
		String password = "litx";

		try {
			Class.forName(driver);
			System.out.println("-----------------1");
			Connection conn1 = DriverManager.getConnection(url, user, password);
			System.out.println("-----------------2");

			if (!conn1.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			System.out.println("-----------------3");

			Statement statement = conn.createStatement();

			String sql = "select * from userinfo";

			ResultSet rs = statement.executeQuery(sql);

			System.out.println("-----------------");

			String name = null;

			while (rs.next()) {
				System.out.println(rs.getString("id") + "\t" + rs.getString("name"));
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
	
	public static void do1() {
		String sql = "select * from userinfo";
		connect();
		while (true) {
			try {
				Thread.sleep(2000);
				System.out.println("-----------------");
				if (conn.isClosed())
					System.out.println("connect close!!!!!!");
				Statement statement = conn.createStatement();
				ResultSet rs = statement.executeQuery(sql);

				String name = null;

				while (rs.next()) {
					System.out.println(rs.getString("id") + "\t" + rs.getString("name"));
				}

				rs.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//reConnect();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	public static void main(String[] args) {
		do3();
	}
	
	public static void main1(String[] args) { 
		    
		String driver = "org.postgresql.Driver";
		String url = "jdbc:postgresql://192.168.88.128:5432/litx";
		String user = "litx";
		String password = "litx";

		try {
			Class.forName(driver);
			System.out.println("-----------------1");
			Connection conn = DriverManager.getConnection(url, user, password);
			System.out.println("-----------------2");

			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			System.out.println("-----------------3");

			Statement statement = conn.createStatement();

			String sql = "select * from userinfo";

			ResultSet rs = statement.executeQuery(sql);

			System.out.println("-----------------");

			String name = null;

			while (rs.next()) {
				System.out.println("AAAAA" + rs.getString("id") + "\t" + rs.getString("name"));
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
