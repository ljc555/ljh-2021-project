package io.renren.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 静态方法
 */
public class StaticMethod {
	static Logger log = LoggerFactory.getLogger(StaticMethod.class);
	/**
	 *
	 * 时间转换方法：根据输入的格式(String _dtFormat)得到当前时间格式得到当前的系统时间 Add By ChengJiWu
	 *
	 * @param _dtFormat
	 * @return
	 */
	public static String getCurrentDateTime(String _dtFormat) {
		String currentdatetime = "";
		try {
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dtFormat = new SimpleDateFormat(_dtFormat);
			currentdatetime = dtFormat.format(date);
		} catch (Exception e) {
			log.error("Time format error,_dtFormat" + _dtFormat + "," + e.getMessage());
		}
		return currentdatetime;
	}

	public static String getCurrentDateTime(Date date,String _dtFormat) {
		String currentdatetime = "";
		try {
			SimpleDateFormat dtFormat = new SimpleDateFormat(_dtFormat);
			currentdatetime = dtFormat.format(date);
		} catch (Exception e) {
			log.error("Time format error,_dtFormat" + _dtFormat + "," + e.getMessage());
		}
		return currentdatetime;
	}

	/**
	 * 时间转换方法：得到默认的时间格式为("yyyy/MM/dd HH:mm:ss")的当前时间
	 *
	 * @return
	 */
	public static String getCurrentDateTime() {
		return getCurrentDateTime("yyyy/MM/dd HH:mm:ss");
	}

	/**
	 * 获取现在时间
	 *
	 * @return返回长时间格式 yyyy/MM/dd HH:mm:ss
	 */
	public static Date getCurrentDateTime2() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String nowdayTime = dateFormat.format(new Date());
        try {
            Date nowDate = dateFormat.parse(nowdayTime);

            return nowDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
	}

	/**
	 * 字符转换函数
	 *
	 * @param s
	 * @return output:如果字符串为null,返回为空,否则返回该字符串
	 */
	public static String nullObject2String(final Object s)
	{
		String str = "";
		try
		{
			str = s.toString();
		}
		catch (final Exception e)
		{
			str = "";
		}
		return str;
	}

}
