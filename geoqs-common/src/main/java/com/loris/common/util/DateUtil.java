package com.loris.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class DateUtil
{
	private static final Map<Integer, Character> charMap = new HashMap<Integer, Character>();
	private static final Pattern p = Pattern.compile("^(\\d+)\\D*(\\d*)\\D*(\\d*)\\D*(\\d*)\\D*(\\d*)\\D*(\\d*)");
	
	/** 日期格式串 */
	final static Pattern DATE_FORMAT = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{1,2}[ ][0-9]{1,2}[:][0-9]{1,2}[:][0-9]{1,2}");

	/** The DateFormat. */
	final public static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/** the DayFormat. */
	final public static SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	/** The timeFormatter. */
	final public static SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm:ss");

	/** The Minute Formatter. */
	final public static SimpleDateFormat MINUTE_FORMAT = new SimpleDateFormat("HH:mm");
	
	/** 星期与日期的格式 */
	final public static SimpleDateFormat WEEK_DATE_FORMAT = new SimpleDateFormat("EEE yyyy-MM-dd");

	/** The Week Name. */
	final public static String DAY_WEEK_Names[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	static
	{
		DAY_FORMAT.setLenient(false);
	}

	static
	{
		charMap.put(1, 'y');
		charMap.put(2, 'M');
		charMap.put(3, 'd');
		charMap.put(4, 'H');
		charMap.put(5, 'm');
		charMap.put(6, 's');
	}
	
	/**
	 * 获得日期的某一个值
	 * @param date 日期
	 * @param field 值类型
	 * @return 值
	 */
	public static int getValue(Date date, int field)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return calendar.get(field);
	}

	/**
	 * Get the current date time.
	 * 
	 * @return
	 */
	public static String getCurTimeStr()
	{
		return DATE_TIME_FORMAT.format(new Date());
	}
	
	/**
	 * 格式化日期格式，格式按照yyyy-MM-dd hh:mm:ss标准
	 * 
	 * @param date 日期对象
	 * @return 时间格式
	 */
	public static String getTimeString(Date date)
	{
		return DATE_TIME_FORMAT.format(date);
	}
	
	/**
	 * 格式化日期格式，格式按照 yyyy-MM-dd标准
	 * 
	 * @param date 日期对象
	 * @return 日期格式字符串
	 */
	public static String getDayString(Date date)
	{
		return DAY_FORMAT.format(date);
	}

	/**
	 * Get the Week Name.
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekName(String date)
	{
		Date d = tryToParseDate(date);
		if (d == null)
		{
			return "未知";
		}
		return getWeekName(d);
	}

	/**
	 * Get the Week Name.
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekName(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek < 0)
		{
			dayOfWeek = 0;
		}
		else if (dayOfWeek > 6)
		{
			dayOfWeek = 6;
		}
		return DAY_WEEK_Names[dayOfWeek];
	}

	/**
	 * Get the DayOfWeek.
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * Get the current day string.
	 * 
	 * @return
	 */
	public static String getCurDayStr()
	{
		return DAY_FORMAT.format(new Date());
	}

	/**
	 * Is same the Day .
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isSameDay(Date d1, Date d2)
	{
		LocalDate ld1 = new LocalDate(new DateTime(d1));
		LocalDate ld2 = new LocalDate(new DateTime(d2));
		return ld1.equals(ld2);
	}

	/**
	 * Parse
	 * 
	 * @param day
	 * @return
	 */
	public static Date parseDay(String day)
	{
		try
		{
			return DAY_FORMAT.parse(day);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	/**
	 * 比较两个日期字符串的时间先后顺序
	 * 
	 * @param dateString1
	 * @param dateString2
	 * @return 比较的值的大小
	 */
	public static int compareDateString(String dateString1, String dateString2)
	{
		Date d1 = tryToParseDate(dateString1);
		Date d2 = tryToParseDate(dateString2);
		if(d1 == null && d2 == null)
		{
			return 0;
		}
		else if(d1 == null && d2 != null)
		{
			return 1;
		}
		else if(d2 == null)
		{
			return -1;
		}
		else 
		{
			return compareDate(d1, d2);			
		}
	}
	
	/**
	 * 比较两个日期的先后顺序
	 * 
	 * @param d1 日期1
	 * @param d2 日期2
	 * @return 如果d1在d2之后，则返回1;如果两个时间相等，返回0;否则返回－1。
	 */
	public static int compareDate(Date d1, Date d2)
	{
		return Long.compare(d1.getTime(), d2.getTime());
	}

	/**
	 * Add the calendar.
	 * 
	 * @param date
	 * @param field
	 * @param value
	 * @return
	 */
	public static Date add(Date date, int field, int value)
	{
		Calendar calendar = Calendar.getInstance();
		synchronized (calendar)
		{
			calendar.setTime(date);
			calendar.add(field, value);
			return calendar.getTime();
		}
	}
	
	/**
	 * 时间相加减
	 * 
	 * @param date 当前日期
	 * @param time 增加的时间值（按照秒）
	 * @return 增加后的时间值
	 */
	public static Date add(Date date, long time)
	{
		long t = date.getTime();
		t += time;
		return new Date(t);
	}

	/**
	 * Format the day.
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDay(Date date)
	{
		return DAY_FORMAT.format(date);
	}

	/**
	 * Format the date time.
	 * 
	 * @param time
	 * @return
	 */
	public static String formatDate(long time)
	{
		return DATE_TIME_FORMAT.format(new Date(time));
	}

	/**
	 * Get the Hour and Minute time String.
	 * 
	 * @param date
	 * @return
	 */
	public static String formatHourAndMinute(Date date)
	{
		return MINUTE_FORMAT.format(date);
	}

	/**
	 * Check if the String is validate day value.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isValidateDay(String str)
	{
		try
		{
			DAY_FORMAT.parse(str);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * 获取两个日期之间的日期
	 * 
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return 日期集合
	 */
	public static List<Date> getBetweenDates(Date start, Date end)
	{
		List<Date> result = new ArrayList<Date>();

		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(start);
		tempStart.set(Calendar.HOUR_OF_DAY, 0);
		tempStart.set(Calendar.MINUTE, 0);
		tempStart.set(Calendar.SECOND, 0);
		tempStart.set(Calendar.MILLISECOND, 0);

		tempStart.add(Calendar.DAY_OF_YEAR, 1);

		Calendar tempEnd = Calendar.getInstance();
		tempEnd.setTime(end);
		tempEnd.set(Calendar.HOUR_OF_DAY, 0);
		tempEnd.set(Calendar.MINUTE, 0);
		tempEnd.set(Calendar.SECOND, 0);
		tempEnd.set(Calendar.MILLISECOND, 0);

		result.add(start);
		while (tempStart.before(tempEnd))
		{
			result.add(tempStart.getTime());
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
		}
		result.add(end);

		return result;
	}

	/**
	 * 计算两个日期之间相隔的天数
	 * 
	 * @param dateStart 开始日期
	 * @param dateEnd 结束日期
	 * @return 相隔的天数
	 */
	public static int getDiscrepantDays(Date dateStart, Date dateEnd)
	{
		return Math.abs((int) ((dateEnd.getTime() - dateStart.getTime()) / (1000 * 3600 * 24)));
	}

	/**
	 * Get the Date list.
	 * 
	 * @param date
	 * @param daynum
	 * @return
	 */
	public static List<Date> getDates(Date start, int daynum)
	{
		List<Date> result = new ArrayList<>();

		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(start);

		if (daynum > 0)
		{
			for (int i = 0; i < daynum; i++)
			{
				tempStart.add(Calendar.DAY_OF_YEAR, 1);
				result.add(tempStart.getTime());
			}
		}
		else
		{
			for (int i = 0; i > daynum; i--)
			{
				tempStart.add(Calendar.DAY_OF_YEAR, -1);
				result.add(tempStart.getTime());
			}
		}

		return result;
	}

	/**
	 * 计算一个日期加上一个天数之后的日期
	 * 
	 * @param start
	 * @param addDayNum
	 * @return
	 */
	public static Date addDateNum(Date start, int addDayNum)
	{
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(start);
		tempStart.add(Calendar.DAY_OF_YEAR, addDayNum);
		return tempStart.getTime();
	}

	/**
	 * Get the day string. 把一个日期型的字符串转换成标准的日期字符串 2018-01-12
	 * 
	 * @param str
	 * @return
	 */
	public static String getDayString(String str)
	{
		try
		{
			Date date = DAY_FORMAT.parse(str);
			return DAY_FORMAT.format(date);
		}
		catch (Exception e)
		{
			return "";
		}
	}
	
	/**
	 * 从一个字符串中解析日期数据
	 * @param str 含有日期的数据
	 *j 
	 *u @return 解析的数据
	 */
	public static String parseFirstDateString(String str)
	{
		Matcher matcher = DATE_FORMAT.matcher(str);
		if(matcher.find())
		{
			return matcher.group();
		}
		return null;
	}
	
	/**
	 * 解析所有的日期格式字符串
	 * @param str 含有日期的字符串
	 * @return 字符串列表
	 */
	public static List<String> parseAllDateString(String str)
	{
		List<String> dates = new ArrayList<>();
		Matcher matcher = DATE_FORMAT.matcher(str);
		while(matcher.find())
		{
			dates.add(matcher.group());
		}
		return dates;
	}

	/**
	 * 任意日期字符串转换为Date，不包括无分割的纯数字（13位时间戳除外） ，日期时间为数字，年月日时分秒，但没有毫秒
	 * 
	 * @param dateString
	 *            日期字符串
	 * @return Date
	 */
	public static Date tryToParseDate(String dateString)
	{
		if(StringUtils.isBlank(dateString))
		{
			return null;
		}
		dateString = dateString.trim().replaceAll("[a-zA-Z]", " ");
		if (Pattern.matches("^[-+]?\\d{13}$", dateString))
		{
			// 支持13位时间戳
			return new Date(Long.parseLong(dateString));
		}
		Matcher m = p.matcher(dateString);
		StringBuilder sb = new StringBuilder(dateString);
		if (m.find(0))
		{
			// 从被匹配的字符串中，充index =
			// 0的下表开始查找能够匹配pattern的子字符串。m.matches()的意思是尝试将整个区域与模式匹配，不一样。
			int count = m.groupCount();
			for (int i = 1; i <= count; i++)
			{
				for (Map.Entry<Integer, Character> entry : charMap.entrySet())
				{
					if (entry.getKey() == i)
					{
						sb.replace(m.start(i), m.end(i), replaceEachChar(m.group(i), entry.getValue()));
					}
				}
			}
			
			String format = sb.toString();
			SimpleDateFormat sf = new SimpleDateFormat(format);
			try
			{
				return sf.parse(dateString);
			}
			catch (ParseException e)
			{
				// System.out.println("日期字符串转Date出错");
				// e.printStackTrace();
				return null;
			}
		}
		else
		{
			Date date = null;
			try
			{
				if((date = WEEK_DATE_FORMAT.parse(dateString)) != null)
					return date;
			}
			catch(ParseException e)
			{				
			}
			
			try
			{
				if((date = DATE_TIME_FORMAT.parse(dateString)) != null)
					return date;
			}
			catch(ParseException e)
			{				
			}
			
			try
			{
				if((date = DAY_FORMAT.parse(dateString)) != null)
					return date;
			}
			catch(ParseException e)
			{				
			}
			
			return null;
		}		
	}
	
	/**
	 * 从字符串查找日期
	 * @param str 字符串
	 * @return
	 */
	public static String findDateFromString(String str, String regex)
	{
		//String reg = "\\d{4}[-]\\d{2}[-]\\d{2}";//日期正则表达式
		Pattern pattern = Pattern.compile (regex);
		Matcher matcher = pattern.matcher (str);//使用正则表达式判断日期
		if (matcher.find ()){
			return matcher.group(0);
		}
		return "";
	}
	
	
	/**
	 * 计算起始时间，这里将以yearToInterval年为单位，获得该年之前的起始日期
	 * 
	 * @param d
	 * @param yearToInterval
	 * @return
	 */
	public static Date getFirstDateBefore(Date d, int yearToInterval)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.YEAR, -yearToInterval);
		//calendar.set(calendar.get(Calendar.YEAR), 0, 1, 0, 0, 0);
		return calendar.getTime();
	}

	/**
	 * 将指定字符串的所有字符替换成指定字符，跳过空白字符
	 * 
	 * @param s
	 *            被替换字符串
	 * @param c
	 *            字符
	 * @return 新字符串
	 */
	public static String replaceEachChar(String s, Character c)
	{
		StringBuilder sb = new StringBuilder("");
		for (Character c1 : s.toCharArray())
		{
			if (c1 != ' ')
			{
				sb.append(String.valueOf(c));
			}
		}
		return sb.toString();
	}
	
	/**
	 * 按照给定的格式解析日期数据
	 * @param value 时间
	 * @param format 格式
	 * @return 解析之后 的数据
	 * @throws ParseException
	 
	public static Date parse(String value, String format) throws ParseException
	{
		return new SimpleDateFormat(format).parse(value);
	}*/
	

	/**
	 * 获取YYYY格式
	 *
	 * @return
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 获取YYYY格式
	 *
	 * @return
	 */
	public static String getYear(Date date) {
		return formatDate(date, "yyyy");
	}

	/**
	 * 获取YYYY-MM-DD格式
	 *
	 * @return
	 */
	public static String getDay() {
		return formatDate(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 获取YYYY-MM-DD格式
	 *
	 * @return
	 */
	public static String getDay(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}

	/**
	 * 获取YYYYMMDD格式
	 *
	 * @return
	 */
	public static String getDays() {
		return formatDate(new Date(), "yyyyMMdd");
	}

	/**
	 * 获取YYYYMMDD格式
	 *
	 * @return
	 */
	public static String getDays(Date date) {
		return formatDate(date, "yyyyMMdd");
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 *
	 * @return
	 */
	public static String getTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss.SSS格式
	 *
	 * @return
	 */
	public static String getMsTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * 获取YYYYMMDDHHmmss格式
	 *
	 * @return
	 */
	public static String getAllTime() {
		return formatDate(new Date(), "yyyyMMddHHmmss");
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 *
	 * @return
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String formatDate(Date date, String pattern) {
		String formatDate = null;
		if (StringUtils.isNotBlank(pattern)) {
			formatDate = DateFormatUtils.format(date, pattern);
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
		}
		return formatDate;
	}

	/**
	 * @Title: compareDate
	 * @Description:(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws
	 * @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if (parseDate(s) == null || parseDate(e) == null) {
			return false;
		}
		return parseDate(s).getTime() >= parseDate(e).getTime();
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static Date parseDate(String date) {
		return parse(date,"yyyy-MM-dd");
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static Date parseTime(String date) {
		return parse(date,"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static Date parse(String date, String pattern) {
		try {
			return DateUtils.parseDate(date,pattern);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static String format(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * 把日期转换为Timestamp
	 *
	 * @param date
	 * @return
	 */
	public static Timestamp format(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 校验日期是否合法
	 *
	 * @return
	 */
	public static boolean isValidDate(String s) {
		return parse(s, "yyyy-MM-dd HH:mm:ss") != null;
	}

	/**
	 * 校验日期是否合法
	 *
	 * @return
	 */
	public static boolean isValidDate(String s, String pattern) {
        return parse(s, pattern) != null;
	}

	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(
					startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 *
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd");
		Date beginDate = null;
		Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * 得到n天之后的日期
	 *
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * 得到n天之后是周几
	 *
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}
	
	/**
	 * 获得某一天的xxxx-xx-xx 00:00:00的日期值
	 * @param d 时间
	 * @return 零时刻的日期
	 */
	public static Date getDateZero(Date d)
	{
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
	}
}
