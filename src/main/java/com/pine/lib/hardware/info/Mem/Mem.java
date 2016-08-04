package com.pine.lib.hardware.info.Mem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.pine.lib.func.debug.M;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.text.format.Formatter;
import android.util.Log;

public class Mem
{
	private Context context;



	public Mem(Context context)
	{
		M.i().addClass(this);
		this.context = context;
	}


	/**
	 * 获取android当前可用内存大小
	 */
	public String getAvailMemory()
	{
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo mi = new MemoryInfo();
		am.getMemoryInfo(mi);
		return Formatter.formatFileSize(context, mi.availMem);
	}


	/**
	 * 获取android当前可用内存大小
	 */
	public long getAvailMemoryLong()
	{
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo mi = new MemoryInfo();
		am.getMemoryInfo(mi);
		return mi.availMem;
	}

	/**
	 * 获取可用内存（单位：M）
	 * 不准确
	 * @return
	 */
	public String getAvailableMemory() {
		ActivityManager am = (ActivityManager) context
				.getSystemService("activity");
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(mi);
		long MEM_UNUSED = mi.availMem / 1048576L;
		return MEM_UNUSED + "MB";
	}

	/**
	 * 获取总内存大小
	 * 
	 * @return
	 */
	public String getTotalMemory()
	{
		String str1 = "/proc/meminfo";
		String str2;
		String[] arrayOfString;
		long initial_memory = 0;
		try
		{
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			for (String num : arrayOfString)
			{
				Log.i(str2, num + "\t");
				initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;
				localBufferedReader.close();
			}
		}
		catch (IOException e)
		{

		}
		return Formatter.formatFileSize(context, initial_memory);
	}


	/**
	 * 获取总内存大小
	 * 
	 * @return
	 */
	public long getTotalMemoryLong()
	{
		String str1 = "/proc/meminfo";
		String str2;
		String[] arrayOfString;
		long initial_memory = 0;
		try
		{
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			for (String num : arrayOfString)
			{
				Log.i(str2, num + "\t");
				initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;
				localBufferedReader.close();
			}
		}
		catch (IOException e)
		{

		}
		return initial_memory;
	}

}
