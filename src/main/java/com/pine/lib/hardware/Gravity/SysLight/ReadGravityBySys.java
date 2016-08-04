package com.pine.lib.hardware.Gravity.SysLight;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;
/**
 * 
 * <Br>	轻量级屏幕方向控制器
 * <br> 要求开启转屏开关
 * <br> 需要权限
 * <br> <uses-permission android:name="android.permission.CHANGE_CONFIGURATION"></uses-permission>
 * <br> 需要事件捕获
 * <br> <activity
 * <br>      android:name="ListActivity"
 * <br>      android:configChanges="orientation|keyboard" />
 * <br> 
 * <br> 一定要放在OnCreate的设置布局 之后
 * <br> 
 */
public class ReadGravityBySys {
	private static G g = new G(ReadGravityBySys.class);
	private Activity activity;
	
	public ReadGravityBySys(Activity activity)
	{
		M.i().addClass(this);
		this.activity = activity;
	}
	
	/**
	 * <br> 设置屏幕方向
	 * 
	 */
	public void setDirect(LightDirect direct)
	{
		if (direct == LightDirect.HENG)
		{
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		else if (direct == LightDirect.SHU)
		{
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		else if (direct == LightDirect.SYS)
		{
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		}
		else if (direct == LightDirect.LOCK)
		{
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		}
		else if (direct == LightDirect.UNKNOW)
		{
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
		}
		else if (direct == LightDirect.USER)
		{
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
		}
	}
	
	
	/**
	 * 返回一个值 决定当前屏幕方向
	 * @return LightDirect
	 */
	public LightDirect getDirect() {
		if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) 
		{
			g.d( "屏幕方向：横 ");
			return LightDirect.HENG;
		} 
		else if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) 
		{
			g.d( "屏幕方向：竖 ");
			return LightDirect.SHU;
		}
		else if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_UNDEFINED) 
		{
			g.d("屏幕方向：未定义 ");
			return LightDirect.UNKNOW;
		}
		else
		{
			g.d( "屏幕方向：未知 ");
			return LightDirect.USER;
		}
	}
}
