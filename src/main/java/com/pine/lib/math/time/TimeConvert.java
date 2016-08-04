package com.pine.lib.math.time;

import java.text.SimpleDateFormat;

import android.text.format.DateFormat;

import com.pine.lib.func.debug.G;

public class TimeConvert
{
	private static G g = new G(TimeConvert.class);



	/**
	 * 将时间戳毫秒数转换为标准的时间格式：yyyy-MM-dd_HH:mm:ss
	 * 
	 * @param millisecond
	 * @return
	 */
	public String toNormalTime(long millisecond)
	{
		return DateFormat.format("yyyy-MM-dd HH:mm:ss", millisecond).toString();
	}


	/**
	 * 将时间戳毫秒数转换为标准的时间格式：yyyy-MM-dd_HH:mm:ss
	 * 
	 * @param millisecond
	 * @return
	 */
	public String tommss(int second)
	{
		int s = second % 60;
		int m = second / 60;
		String ss = String.valueOf(s);
		String mm = String.valueOf(m);

		if (ss.length() == 1) ss = "0" + ss;
		if (mm.length() == 1) mm = "0" + mm;

		return mm + ":" + ss;
	}


	/**
	 * 将时间戳毫秒数转换为指定的时间格式：例如，yyyy-MM-dd_HH:mm:ss
	 * 
	 * @param millisecond
	 * @param model
	 * @return
	 */
	public String toNormalTime(long millisecond, String model)
	{
		return DateFormat.format(model, millisecond).toString();
	}


	/**
	 * 将JAVA里获取到的13位毫秒数转换为PHP使用的10位的时间戳毫秒数
	 * 
	 * @param javaTime
	 * @return
	 */
	public String toPHPTime(String javaTime)
	{
		String phpTime = javaTime.substring(0, 10);
		return phpTime;
	}


	/**
	 * 将JAVA里获取到的13位毫秒数转换为PHP使用的10位的时间戳毫秒数
	 * 
	 * @param javaTime
	 * @return
	 */
	public String phpToStringTime(String phpIntTime)
	{
		return phpToStringTime(phpIntTime, new SimpleDateFormat(
				"MM-dd HH:mm"));
	}


	/**
	 * 将JAVA里获取到的13位毫秒数转换为PHP使用的10位的时间戳毫秒数
	 * 
	 * @param javaTime
	 * @return
	 */
	public String phpToStringTime(String phpIntTime, SimpleDateFormat df)
	{
		try
		{
			g.d("phpIntTime = " + phpIntTime);
			long time = Long.valueOf(phpIntTime) * 1000;

			String dateTime = df.format(time);
			return dateTime;
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
		return "";
	}


	public String getPHPTime()
	{
		Long tsLong = System.currentTimeMillis() / 1000;
		String ts = tsLong.toString();
		return ts;
	}


	public static TimeConvert i()
	{
		return new TimeConvert();
	}

}
