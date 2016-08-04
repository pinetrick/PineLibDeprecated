package com.pine.lib.hardware.info.Cpu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.util.regex.Pattern;

import android.util.Log;

import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;

public class CpuInfo
{
	private static G g = new G(CpuInfo.class);



	public CpuInfo()
	{
		M.i().addClass(this);
	}


	public static float getCpuUsed()
	{

		return 0.0f;
	}


	/**
	 * 以字符串形式返回CPU信息
	 * 
	 * @return
	 */
	public static String readCpuInfo()
	{
		String result = "";
		ProcessBuilder cmd;
		try
		{
			String[] args = { "system/bin/cat", "proc/cpuinfo" };
			cmd = new ProcessBuilder(args);
			Process process = cmd.start();
			InputStream in = process.getInputStream();
			byte[] re = new byte[1024];
			while (in.read(re) != -1)
			{
				System.out.println(new String(re));
				result = result + new String(re);
			}
			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * 获取系统总CPU使用时间
	 */
	public static long getTotalCpuTime()
	{
		String[] cpuInfos = null;
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream("/proc/stat")), 1000);
			String load = reader.readLine();
			reader.close();
			cpuInfos = load.split(" ");
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		long totalCpu = Long.parseLong(cpuInfos[2])
				+ Long.parseLong(cpuInfos[3]) + Long.parseLong(cpuInfos[4])
				+ Long.parseLong(cpuInfos[6]) + Long.parseLong(cpuInfos[5])
				+ Long.parseLong(cpuInfos[7]) + Long.parseLong(cpuInfos[8]);
		return totalCpu;
	}


	/**
	 * 获取应用占用的CPU时间
	 * 
	 * @return
	 */
	public static long getAppCpuTime()
	{
		String[] cpuInfos = null;
		try
		{
			int pid = android.os.Process.myPid();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream("/proc/" + pid + "/stat")), 1000);
			String load = reader.readLine();
			reader.close();
			cpuInfos = load.split(" ");
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		long appCpuTime = Long.parseLong(cpuInfos[13])
				+ Long.parseLong(cpuInfos[14]) + Long.parseLong(cpuInfos[15])
				+ Long.parseLong(cpuInfos[16]);
		return appCpuTime;
	}


	/*
	 * 本函数可获取CPU利用率
	 */
	public static float readCpuUsed()
	{

		try
		{

			RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");

			String load = reader.readLine();

			String[] toks = load.split(" ");

			long idle1 = Long.parseLong(toks[5]);

			long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3])
					+ Long.parseLong(toks[4]) + Long.parseLong(toks[6])
					+ Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

			try
			{
				Thread.sleep(360);

			}
			catch (Exception e)
			{
			}

			reader.seek(0);

			load = reader.readLine();

			reader.close();

			toks = load.split(" ");

			long idle2 = Long.parseLong(toks[5]);

			long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3])
					+ Long.parseLong(toks[4]) + Long.parseLong(toks[6])
					+ Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

			return (int) (100 * (cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1)));

		}
		catch (IOException ex)
		{
			ex.printStackTrace();

		}
		return 0;

	}


	/**
	 * 获取CPU型号 不知是否正确
	 * 
	 * @return
	 */
	public String getCpuModel()
	{
		String str1 = "/proc/cpuinfo";
		String str2 = "";
		String[] cpuInfo = { "", "" };
		try
		{
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			str2 = localBufferedReader.readLine();
			String[] arrayOfString = str2.split("\\s+");
			for (int i = 2; i < arrayOfString.length; ++i)
			{
				cpuInfo[0] = (cpuInfo[0] + arrayOfString[i] + " ");
			}
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			int tmp125_124 = 1;
			String[] tmp125_123 = cpuInfo;
			tmp125_123[tmp125_124] = (tmp125_123[tmp125_124] + arrayOfString[2]);
			localBufferedReader.close();
		}
		catch (IOException localIOException)
		{
		}
		return cpuInfo[0];
	}


	/**
	 * 获取某个进程pid的CPU使用情况 不知道做什么用
	 * 
	 * @param pid
	 * @return
	 */
	@Deprecated
	public static int getCpuUsage(int pid)
	{
		try
		{
			RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
			String load = reader.readLine();

			String[] toks = load.split(" ");

			long idle1 = Long.parseLong(toks[5]);
			long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3])
					+ Long.parseLong(toks[4]) + Long.parseLong(toks[6])
					+ Long.parseLong(toks[7]) + Long.parseLong(toks[8]);
			try
			{
				Thread.sleep(360L);
			}
			catch (Exception localException)
			{
			}
			reader.seek(0L);
			load = reader.readLine();
			reader.close();

			toks = load.split(" ");

			long idle2 = Long.parseLong(toks[5]);
			long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3])
					+ Long.parseLong(toks[4]) + Long.parseLong(toks[6])
					+ Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

			float cpu = (float) (cpu2 - cpu1)
					/ (float) (cpu2 + idle2 - (cpu1 + idle1));
			return Math.round(cpu * 100.0F);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}


	// 实时获取CPU当前频率（单位KHZ）
	public static String getCurCpuFreq()
	{
		String result = "N/A";
		try
		{
			FileReader fr = new FileReader(
					"/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			result = text.trim();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}


	// 获取CPU名字
	public static String getCpuName()
	{
		try
		{
			FileReader fr = new FileReader("/proc/cpuinfo");
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			String[] array = text.split(":\\s+", 2);
			for (int i = 0; i < array.length; i++)
			{
			}
			return array[1];
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 获取设备的CPU序列号 不知道做什么用的
	 * 
	 * @return
	 */
	@Deprecated
	public static String getCpuSer()
	{
		String cpuSerial = "";
		String str = "";
		try
		{
			Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			while (str != null)
			{
				str = input.readLine();
				if ((str != null) && (str.startsWith("Serial")))
				{
					cpuSerial = str.substring(str.indexOf(":") + 1,
							str.length()).trim();
				}
			}
		}
		catch (IOException localIOException)
		{
		}
		return cpuSerial;
	}


	/**
	 * 获取设备CPU核心数 不知道做什么
	 * 
	 * @return
	 */
	public static int getCpuCoresNum()
	{
		try
		{
			File dir = new File("/sys/devices/system/cpu/");

			File[] files = dir.listFiles(new FileFilter() {
				public boolean accept(File pathname)
				{
					return Pattern.matches("cpu[0-9]", pathname.getName());
				}
			});
			Log.d("info", "CPU Count: " + files.length);

			return files.length;
		}
		catch (Exception e)
		{
			Log.d("info", "CPU Count: Failed.");
			e.printStackTrace();
		}
		return 1;
	}


	/**
	 * 获取CPU最小频率，单位MHZ 不知道做什么
	 * 
	 * @return
	 */
	public static String getCpuMinFrequence()
	{
		String result = "";
		try
		{
			String[] args = { "/system/bin/cat",
					"/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq" };
			ProcessBuilder cmd = new ProcessBuilder(args);
			Process process = cmd.start();
			InputStream in = process.getInputStream();
			byte[] re = new byte[24];
			while (in.read(re) != -1)
			{
				result = result + new String(re);
			}
			in.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			result = "N/A";
		}
		return Long.parseLong(result.trim()) / 1000L + "MHZ";
	}


	/**
	 * 获取CPU最大频率，单位MHZ 不知道做什么
	 * 
	 * @return
	 */
	public static String getCpuMaxFrequence()
	{
		try
		{
			String[] args = { "/system/bin/cat",
					"/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" };
			ProcessBuilder cmd = new ProcessBuilder(args);

			Process process = cmd.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = reader.readLine();
			return Long.parseLong(line) / 1000L + "MHZ";
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		return "0";
	}
}
