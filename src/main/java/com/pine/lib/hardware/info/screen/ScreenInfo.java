package com.pine.lib.hardware.info.screen;

import android.content.Context;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.pine.lib.base.activity.A;
import com.pine.lib.func.debug.M;

/**
 * 获取屏幕相关信息
 * 
 * <pre>
 * 构造方法
 * ScreenInfo.i()
 * 
 * </pre>
 */
public class ScreenInfo
{
	private Context context;


	

	/**
	 * 获取设备屏幕像素宽度 可以使用
	 * 
	 * @return
	 */
	public int getDeviceWidth()
	{
		WindowManager wm = (WindowManager) this.context
				.getSystemService("window");
		return wm.getDefaultDisplay().getWidth();
	}


	/**
	 * 获取设备屏幕像素高度 可以使用
	 * 
	 * @return
	 */
	public int getDeviceHeight()
	{
		WindowManager wm = (WindowManager) this.context
				.getSystemService("window");
		return wm.getDefaultDisplay().getHeight();
	}
	/**
	 * get resolution ratio 
	 * return format : 1024 * 768
	 * <pre>
	 * 
	 * </pre>
	 * @return
	 */
	public String get屏幕分辨率()
	{
		return getDeviceHeight() + " * " + getDeviceWidth();
	}

	/**
	 * 设置转屏状态
	 * 
	 * @param isOpen
	 *            开启 关闭
	 */
	public void setTurn(Boolean isOpen)
	{
		if (isOpen)
		{
			Settings.System.putInt(context.getContentResolver(),
					Settings.System.ACCELEROMETER_ROTATION, 1);
		}
		else
		{
			Settings.System.putInt(context.getContentResolver(),
					Settings.System.ACCELEROMETER_ROTATION, 0);
		}
	}


	/**
	 * 获取转屏状态
	 */
	public Boolean isTurn()
	{
		int flag = Settings.System.getInt(context.getContentResolver(),
				Settings.System.ACCELEROMETER_ROTATION, 0);
		if (flag == 0)
		{
			return false;
		}
		else
		{
			return true;
		}

	}


	/**
	 * 获取设备屏幕密度(例如：(0.75/1.0/1.5/2.0)) 可以使用
	 * 
	 * @return
	 */
	public float getDeviceDensity()
	{
		DisplayMetrics metric = new DisplayMetrics();
		metric = this.context.getResources().getDisplayMetrics();
		float density = metric.density;

		return density;
	}


	/**
	 * 获取设备每英寸的dpi 可以使用
	 * 
	 * @return
	 */
	public float getDeviceDensityDpi()
	{
		DisplayMetrics metric = new DisplayMetrics();
		metric = this.context.getResources().getDisplayMetrics();

		float densityDpi = metric.densityDpi;
		return densityDpi;
	}


	/**
	 * 获取设备方向（0 是 portrait,1 是 landscape） 可以使用
	 * 
	 * @return
	 */
	public int getDeviceOrientation()
	{
		WindowManager wm = (WindowManager) this.context
				.getSystemService("window");
		return wm.getDefaultDisplay().getOrientation();
	}


	/**
	 * 获取屏幕亮度 结果 [0--255]
	 */
	public int getLight()
	{
		int flag = Settings.System.getInt(context.getContentResolver(),
				Settings.System.SCREEN_BRIGHTNESS, 0);
		return flag;

	}


	public static ScreenInfo i()
	{
		return new ScreenInfo();
	}


	private ScreenInfo()
	{
		
		this(A.c());
	}


	private ScreenInfo(Context c)
	{
		M.i().addClass(this);
		this.context = c;
	}
}
