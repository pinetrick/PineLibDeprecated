package com.pine.lib.func.debug;

import java.util.ArrayList;
import java.util.Locale;

import com.pine.lib.math.pckhash.GetMyPkgHashCode;

import android.util.Log;
/**
 * 在Application中
 * 
 * <pre>
 * A.s(this);
 * G.setEnableGlobalDebug(true);
 * GetMyPkgHashCode.isRight(-1878995134);
 * </pre>
 */
public class G
{
	private static ArrayList<String> isDebug = new ArrayList<String>();
	private static Boolean enableGlobalDebug = true; // debug 总开关 如果为false
														// 则无法debug
	/**
	 * 在提示悬浮窗口中显示的文字
	 */
	public static String mainInfo = "";

	public String s = "";
	


	/**
	 * 全局调试开关 请在Application初始化的时候设置
	 * 
	 * @param enableGlobalDebug
	 */
	public static void setEnableGlobalDebug(Boolean enableGlobalDebug)
	{
		G.enableGlobalDebug = enableGlobalDebug;
	}


	public static void init()
	{
		add("com.pine.nzbbs");
		add("com.pine.lib.net.pic");
		

		isInit = true;
	}


	public static void add(String s)
	{
		isDebug.add(s.trim().toLowerCase(Locale.getDefault()));
	}



	public Boolean enableDebug = false;



	public G(Class<?> className)
	{
		if (enableGlobalDebug)
		{
			if (!isInit) init();

			s = className.getName().trim();
			String sLow = s.toLowerCase(Locale.getDefault());
			for (int i = 0; i < isDebug.size(); i++)
			{
				if (sLow.startsWith(isDebug.get(i)))
				{
					enableDebug = true;
					break;
				}
			}

			// 找最后一个点
			int x = s.indexOf(".");
			while (x >= 0)
			{
				s = s.substring(x + 1, s.length());
				x = s.indexOf(".");
			}
			s = s + " ----------------------";
		}
	}



	private static Boolean isInit = false;



	public void d(long info)
	{
		d(String.valueOf(info));
	}


	public void d(Double info)
	{
		d(String.valueOf(info));
	}


	public void d(Boolean info)
	{
		d(String.valueOf(info));
	}


	public void d(int info)
	{
		d(String.valueOf(info));
	}

	/**
	 * 这个的级别是info级别的log
	 * 将会在悬浮窗中显示
	 * @param info
	 */
	public void p(String info)
	{
		i(info);
		mainInfo = info;
	}
	
	public void d(String info)
	{
		if (enableGlobalDebug && enableDebug)
		{
			if (info != null)
			{
				Log.d(s, info);
			}
			else
			{
				Log.d(s, "null");
			}
		}
	}


	public void i(String info)
	{
		if (enableGlobalDebug && enableDebug)
		{
			if (info != null)
			{
				Log.i(s, info);
			}
			else
			{
				Log.i(s, "null");
			}
		}
	}


	public void e(String info)
	{
		if (enableGlobalDebug)
		{
			if (info != null)
			{
				Log.e(s, info);
			}
			else
			{
				Log.e(s, "null");
			}
		}
	}


	public void w(String info)
	{
		if (enableGlobalDebug)
		{
			if (info != null)
			{
				Log.w(s, info);
			}
			else
			{
				Log.w(s, "null");
			}
		}
	}


	public static Boolean getEnableGlobalDebug()
	{
		return enableGlobalDebug;
	}

}
