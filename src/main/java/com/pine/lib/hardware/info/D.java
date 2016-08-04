package com.pine.lib.hardware.info;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.pine.lib.base.activity.A;
import com.pine.lib.hardware.info.screen.ScreenInfo;
import com.pine.lib.math.MD5.MD5;
import com.pine.lib.storage.SDCard;

/**
 * 驱动器相关信息
 * 
 * @author Administrator
 * 
 */
public class D
{
	private GetHardInfo getHardInfo;
	private static D d;



	public static D i()
	{
		if (d == null)
		{
			d = new D();
		}

		return d;
	}


	private D()
	{
		if (getHardInfo == null)
		{
			getHardInfo = new GetHardInfo(A.c());
		}
	}


	public String get设备识别码集合()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(getIMEI() + "-");
		sb.append(getIMSI() + "-");
		sb.append(getSIM卡的编码() + "-");
		sb.append(getMac());
		return sb.toString();
	}


	public String get设备识别码不为空()
	{
		String l = getIMEI();
		if ((l != null) && (!l.equals("")))
		{
			return "E" + l;
		}

		l = getIMSI();
		if ((l != null) && (!l.equals("")))
		{
			return "S" + l;
		}

		l = getSIM卡的编码();
		if ((l != null) && (!l.equals("")))
		{
			return "C" + l;
		}

		l = getMac();
		if ((l != null) && (!l.equals("")))
		{
			return "M" + MD5.md5(l);
		}

		l = getAndroidId();
		return "A" + l;

	}


	/**
	 * 本数据重装系统会改变
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public String getAndroidId()
	{
		return android.provider.Settings.Secure.getString(A.c()
				.getContentResolver(),
				android.provider.Settings.Secure.ANDROID_ID);

	}


	/**
	 * 获取MAC地址
	 * 
	 * @return
	 */
	public String getMac()
	{
		WifiManager wifi = (WifiManager) A.c().getSystemService(
				Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}


	/**
	 * 获取设备编号
	 * 
	 * @return
	 */
	public String getIMEI()
	{
		return getHardInfo.device_id;
	}


	/**
	 * 获取SIM卡的编码
	 * 
	 * @return
	 */
	public String getSIM卡的编码()
	{

		return getHardInfo.sim_serial_number;
	}


	/**
	 * 获取手机号码
	 * 
	 * @return
	 */
	public String get手机号码()
	{
		return getHardInfo.line1_number;
	}


	/**
	 * 获取getIMSI
	 * 
	 * @return
	 */
	public String getIMSI()
	{

		return getHardInfo.subscriber_id;
	}


	/**
	 * get resolution ratio return format : 1024 * 768
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public String get屏幕分辨率()
	{
		return ScreenInfo.i().get屏幕分辨率();
	}


	/**
	 * get pixel density return format : 0.75
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public float get屏幕像素密度()
	{
		return ScreenInfo.i().getDeviceDensity();
	}


	/**
	 * get light of screen
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public int get屏幕亮度()
	{
		return ScreenInfo.i().getLight();
	}


	/**
	 * get direct of screen return : 横向、纵向
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public String get屏幕方向()
	{
		if (ScreenInfo.i().getDeviceOrientation() == 1)
		{
			return "横向";
		}
		else
		{
			return "纵向";
		}
	}


	/**
	 * get network_operator_name;
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public String get网络运营商名称()
	{
		return getHardInfo.network_operator_name;
	}


	/**
	 * get dpi pre inch return : 横向、纵向
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public float get每英寸DPI()
	{
		return ScreenInfo.i().getDeviceDensityDpi();
	}


	/**
	 * get sdcard size
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public String get存储卡总空间()
	{
		return new SDCard().getSDCardTotalStorage();
	}


	/**
	 * get sdcard free size
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public String get存储卡剩余空间()
	{
		return new SDCard().getSDCardAvailableStorage();
	}


	/**
	 * get storage free size
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public long get内存剩余空间()
	{
		return SDCard.getRealSizeOnPhone();
	}


	/**
	 * get sdcard exist
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public Boolean isSdcardPlug()
	{
		return SDCard.sdCardIsAvailable();
	}


	/**
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return 2.2.4
	 */
	public String get版本String()
	{
		try
		{
			// 获取packagemanager的实例
			PackageManager packageManager = A.c().getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(A.c()
					.getPackageName(), 0);
			String version = packInfo.versionName;
			return version;
		}
		catch (Exception e)
		{

		}
		return "无法获取版本号";
	}


	/**
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return 224
	 */
	public int get版本号()
	{
		try
		{
			// 获取packagemanager的实例
			PackageManager packageManager = A.c().getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(A.c()
					.getPackageName(), 0);
			int version = packInfo.versionCode;
			return version;
		}
		catch (Exception e)
		{

		}
		return -1;
	}


	public String toString()
	{
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("屏幕分辨率：" + get屏幕分辨率() + "\n");
		sBuilder.append("屏幕像素密度：" + get屏幕像素密度() + "\n");
		sBuilder.append("屏幕亮度：" + get屏幕亮度() + "\n");
		sBuilder.append("屏幕方向：" + get屏幕方向() + "\n");
		sBuilder.append("每英寸DPI：" + get每英寸DPI() + "\n");
		sBuilder.append("\n");
		sBuilder.append("存储卡存在性：" + isSdcardPlug() + "\n");
		sBuilder.append("存储卡剩余空间：" + get存储卡剩余空间() + "\n");
		sBuilder.append("存储卡总空间：" + get存储卡总空间() + "\n");
		sBuilder.append("内存剩余空间：" + get内存剩余空间() + "\n");

		sBuilder.append("\n");
		sBuilder.append("IMEI：" + getIMEI() + "\n");
		sBuilder.append("IMSI：" + getIMSI() + "\n");
		sBuilder.append("AndroidId（重装系统改变）：" + getAndroidId() + "\n");
		sBuilder.append("Mac：" + getMac() + "\n");
		sBuilder.append("设备标识码(不为空)：" + get设备识别码不为空() + "\n");
		sBuilder.append("可用识别码集合：" + get设备识别码集合() + "\n");
		sBuilder.append("SIM卡编码：" + getSIM卡的编码() + "\n");
		sBuilder.append("手机号码：" + get手机号码() + "\n");
		sBuilder.append("网络运行商名称：" + get网络运营商名称() + "\n");

		// sBuilder.append(getHardInfo.toString() + "\n");
		return sBuilder.toString();
	}

}
