package com.ericsson.li.type;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;

/**
 * @author litx
 */
public class DateTest {

	public static void main(String[] args) throws ParseException {

		/*
		 * int currentMaxDays = getCurrentMonthDay();
		 * 
		 * int maxDaysByDate = getDaysByYearMonth(2012, 2);
		 * 
		 * String week = getDayOfWeekByDate("2012-12-25");
		 * 
		 * System.out.println("��������" + currentMaxDays);
		 * System.out.println("2012��2������" + maxDaysByDate);
		 * System.out.println("2012-12-25�ǣ�" + week); getWeekInterval();
		 */
		/*
		 * System.out.println(getDayofWeek("20150207"));
		 * System.out.println(getDayofWeek("20150208"));
		 * System.out.println(getDayofWeek("20150209"));
		 * 
		 * System.out.println(getWeekOfYear("20150101"));
		 * System.out.println(getWeekOfYear("20150209"));
		 */
		// DateEqueue();

		// System.out.println(getEsbBackFile());
		// System.out.println(getBackFileName("d:/", "esb.back", new Date()));
		// System.out.println(getBackDirName("d:\\", new Date()));
		// splitDay();
		// getStrDay();
		// getSerialNumber();
		// timeArea();
		//testTimeRange();
		//outDate();
		//subtraction();
		System.out.println(new Date(1504751100000L));
		testDateTime();

	}
	
	public static void subtraction() {
		Calendar nowDate=Calendar.getInstance(),oldDate=Calendar.getInstance();
		nowDate.setTime(new Date());//设置为当前系统时间 
		oldDate.set(1990, 5, 19);//设置为1990年（6）月29日
		long timeNow=nowDate.getTimeInMillis();
		long timeOld=oldDate.getTimeInMillis();
		long 相隔天数=(timeNow-timeOld)/(1000*60*60*24);//化为天
		//nowDate.

		System.out.println("相隔"+相隔天数+"天");
		
		

		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			System.out.println("1476230636:" + sdf.parse("2016-10-12 00:03:56").getTime() / 1000);
			System.out.println("1476230936:" + sdf.parse("2016-10-12 00:08:56").getTime() / 1000);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long curtime = System.currentTimeMillis();
		
		System.out.println("system current time:" + curtime);
		System.out.println("system current time:" + curtime / 1000);
		System.out.println(new Date().getTime());
		System.out.println("curtime:" +  new Date(curtime));
		
	}
	
	public static void outDate() {
		getTimeByDate();
		getTimeByCalendar();
	}

	public static void getTimeByDate() {
		Date date = new Date();
		DateFormat df1 = DateFormat.getDateInstance();// 日期格式，精确到日
		System.out.println(df1.format(date));
		DateFormat df2 = DateFormat.getDateTimeInstance();// 可以精确到时分秒
		System.out.println(df2.format(date));
		DateFormat df3 = DateFormat.getTimeInstance();// 只显示出时分秒
		System.out.println(df3.format(date));
		DateFormat df4 = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL); // 显示日期，周，上下午，时间（精确到秒）
		System.out.println(df4.format(date));
		DateFormat df5 = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG); // 显示日期,上下午，时间（精确到秒）
		System.out.println(df5.format(date));
		DateFormat df6 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT); // 显示日期，上下午,时间（精确到分）
		System.out.println(df6.format(date));
		DateFormat df7 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM); // 显示日期，时间（精确到分）
		System.out.println(df7.format(date));
	}

	public static void getTimeByCalendar() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);// 获取年份
		int month = cal.get(Calendar.MONTH) + 1;// 获取月份
		int day = cal.get(Calendar.DATE);// 获取日
		int hour = cal.get(Calendar.HOUR);// 小时
		int minute = cal.get(Calendar.MINUTE);// 分
		int second = cal.get(Calendar.SECOND);// 秒
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);// 一周的第几天 
		System.out.println("现在的时间是：公元"+year+"年"+month+"月"+day+"日      "+hour+"时"+minute+"分"+second+"秒       day of week:"+dayOfWeek);
	}

	public static void getSerialNumber() {
		System.out.println(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String strTime = format.format(new Date());

		Random random = new Random();
		String rd = String.format("%02d", random.nextInt(100));

		String serialNumber = strTime + rd;
		System.out.println(serialNumber);
		System.out.println("len:" + serialNumber.length());

		System.out.println(strTime.substring(0, 14));

		for (int i = 0; i < 10; i++) {
			System.out.println(String.format("%02d", random.nextInt(10)));
		}
	}

	public static String getBackFileName(String dir, String flag, Date date) {
		try {
			StringBuilder sb = new StringBuilder();
			DateFormat format = new SimpleDateFormat("yyyyMMdd.HHmmss");
			String time = format.format(date);
			System.out.println(time);
			sb.append(dir);
			sb.append(time.split("\\.")[0]).append(File.separator);
			sb.append(flag).append(".");
			sb.append(time).append(".");
			sb.append(Thread.currentThread().getId());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getBackDirName(String dir, Date date) {
		try {
			StringBuilder sb = new StringBuilder();
			DateFormat format = new SimpleDateFormat("yyyyMM");
			String time = format.format(date);
			System.out.println(time);
			sb.append(dir).append(File.separator).append(time).append(File.separator);
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getEsbBackFile() {
		String file = null;
		try {
			Date date = new Date();
			DateFormat format = new SimpleDateFormat("yyyyMMdd.HHmmss");
			String time = format.format(date);
			file = "ESB.back." + Thread.currentThread().getId() + "." + time;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return file;
	}

	private static void DateEqueue() {
		try {
			Date date1 = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date2 = sdf.parse("20151207");

			System.out.println("data1:" + date1.toString());
			System.out.println("data2:" + date2.toString());
			if (date1.equals(date2)) {
				System.out.println("ok");
			} else {
				System.out.println("no ok");
			}

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);

			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date2);

			boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
			boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
			boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

			if (isSameDate) {
				System.out.println("Calendar ok");
			} else {
				System.out.println("Calendar no ok");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * ���2015���1��
	 */
	public static void getWeekInterval() {
		System.out.println("=======");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.clear(); 
		cal.set(Calendar.YEAR, 2015);
		cal.set(Calendar.WEEK_OF_YEAR, 1);

		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		System.out.println(sdf.format(cal.getTime()));

		System.out.println(cal.getTime());
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

		System.out.println(cal.getTime());
		System.out.println(sdf.format(cal.getTime()));

		/*
		 * Date date = null; try{ date = sdf.parse("20091104");//��ʼ����
		 * }catch(Exception e){
		 * 
		 * }
		 */

	}

	/**
	 * ��ȡ���µ� ����
	 */
	public static int getCurrentMonthDay() {

		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * ����� �� ��ȡ��Ӧ���·� ����
	 */
	public static int getDaysByYearMonth(int year, int month) {

		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * ������� �ҵ���Ӧ���ڵ� ����
	 */
	public static String getDayOfWeekByDate(String date) {
		String dayOfweek = "-1";
		try {
			SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
			Date myDate = myFormatter.parse(date);
			SimpleDateFormat formatter = new SimpleDateFormat("E");
			String str = formatter.format(myDate);
			dayOfweek = str;

		} catch (Exception e) {
			System.out.println("����!");
		}
		return dayOfweek;
	}

	public static int getDayofWeek(String strDay) {
		try {
			Calendar a = Calendar.getInstance();
			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");

			a.setTime(sd.parse(strDay));
			return a.get(Calendar.DAY_OF_WEEK);
		} catch (Exception e) {
			System.out.println("����!");
		}
		return -1;
	}

	public static int getWeekOfYear(String strDay) {
		try {
			Calendar a = Calendar.getInstance();
			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");

			a.setTime(sd.parse(strDay));
			return a.get(Calendar.WEEK_OF_YEAR);
		} catch (Exception e) {
			System.out.println("����!");
		}
		return -1;
	}

	public static XMLGregorianCalendar stringToXMLGregorianCalendar(String str, String strFormat)
			throws ParseException, DatatypeConfigurationException {
		DateFormat format = new SimpleDateFormat(strFormat);
		Date date = format.parse(str);
		System.out.println(date.toString());

		DatatypeFactory dataTypeFactory = DatatypeFactory.newInstance();

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(date.getTime());
		return dataTypeFactory.newXMLGregorianCalendar(gc);
	}

	public static void splitDay() {
		int du = 4;
		int count = 24 / du;
		if ((24 % du) != 0) {
			count += 1;
		}
		System.out.println("du:" + du + ",count:" + count);
		String beginStr;
		String endStr;
		String strDay = "20151210";
		for (int i = 0; i < count; i++) {
			int begin = i * du;
			int end = (i + 1) * du - 1;
			end = end > 23 ? 23 : end;
			beginStr = String.format("%02d:00:00", begin);
			endStr = String.format("%02d:59:59", end);
			System.out.println("begin:" + beginStr + " -> end: " + endStr);

			try {
				String qq = String.format("%s %02d0000", strDay, begin);
				System.out.println("====>" + qq);
				stringToXMLGregorianCalendar(qq, "yyyyMMdd HHmmss");
				String pp = String.format("%s %02d5959", strDay, end);
				System.out.println("====>" + pp);
				stringToXMLGregorianCalendar(pp, "yyyyMMdd HHmmss");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void getStrDay() {
		// yyyyMMddHHmmss yyyyMMdd HHmmss
		/*
		 * SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
		 * System.out.println(dateFormat.format(new Date()));
		 * System.out.println(new Date().toString());
		 */
		String tmpDate = "20150709";
		tmpDate.trim();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		format.setLenient(false);
		Date a;
		try {
			a = format.parse(tmpDate);
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd");
			System.out.println(format2.format(a));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void timeArea() {
		// yyyyMMddHHmmss yyyyMMdd HHmmss

		String str = "2016-01-15T13:00:00+08:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd HHmmss");
		Date aa = null;
		;
		try {
			aa = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(aa.toString());
		System.out.println(sdf2.format(aa));

		/*
		 * String dateString = "Fri Feb 01 00:00:00 GMT+08:00 2013";
		 * SimpleDateFormat sdf = new SimpleDateFormat(
		 * "EEE MMM dd HH:mm:ss z yyyy", Locale.US); TimeZone tz =
		 * TimeZone.getTimeZone("GMT+8"); sdf.setTimeZone(tz); String str =
		 * sdf.format(Calendar.getInstance().getTime());
		 * System.out.println(str); Date s; try { s = sdf.parse(dateString); sdf
		 * = new SimpleDateFormat("yyyy-MM-dd");
		 * System.out.println(sdf.format(s)); } catch (ParseException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */

	}

	public static void testTimeRange() {

		Calendar start = Calendar.getInstance();
		start.set(2014, 6, 11, 0, 0, 0);
		// start.set(2014, 6, 11);
		Long startTIme = start.getTimeInMillis();

		Calendar end = Calendar.getInstance();
		end.set(2014, 7, 11, 0, 0, 0);
		// end.set(2014, 7, 11);
		Long endTime = end.getTimeInMillis();

		Long oneDay = 1000 * 60 * 60 * 24l;

		Long time = startTIme;
		while (time <= endTime) {
			Date d = new Date(time);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(df.format(d));
			time += oneDay;
		}
	}
	
    public static void testDateTime() throws ParseException {
    	DateTime dtEvent = new DateTime(new Date());
    	System.out.println( dtEvent.toString("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    	
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");  
        Date date = df.parse("2017-08-24T08:49:06.638Z");    
        System.out.println(date.getTime());
        
        date = df.parse("2017-09-18T08:35:00.495Z");    
        System.out.println(date.getTime());
        System.out.println(new Date(1505872080000L));
        
        
        dtEvent = new DateTime(date);
        System.out.println( dtEvent.toString("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }
    
    
}
