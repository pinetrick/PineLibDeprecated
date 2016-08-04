package com.pine.lib.math.time;

import android.text.format.Time;

public class GetTime
{

	public static String getYMD()
	{
		return getYMD("");
	}


	public static String getYMD(String 连接符)
	{
		Time time = new Time("GMT+8");
		time.setToNow();
		int year = time.year;
		int month = time.month + 1;
		int day = time.monthDay + 1;
		// int minute = time.minute;
		// int hour = time.hour;
		// int sec = time.second;
		String r = year + 连接符 + month + 连接符 + day;

		return r;
	}
}
