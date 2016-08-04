package com.pine.lib.net.state.change;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.pine.lib.base.activity.A;
import com.pine.lib.func.broadcast.BroadCastManager;
import com.pine.lib.func.broadcast.BroadCastRecImp;
import com.pine.lib.func.debug.M;

/**
 * 网络状态改变广播监听器
 * 
 * <pre>
 * 需要权限
 * 			< uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/> 
 *  		< receiver android:name="com.qianfan365.lib.net.state.change.NetStateChangeBroadcastReceiver">  
 *             < intent-filter>  
 *                 < action android:name="android.net.conn.CONNECTIVITY_CHANGE"/>  
 *             < /intent-filter>  
 *         < /receiver>
 *         
 *         
 *  网络状态改变 （NetState）
 * </pre>
 */
public class NetStateChangeBroadcastReceiver extends BroadcastReceiver
		implements BroadCastRecImp
{
	private static NetState netState;// = new NetState();

	public NetStateChangeBroadcastReceiver()
	{
		M.i().addClass(this);
	}

	/**
	 * 返回现在是否有网络可用
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public static Boolean isNetAvailability()
	{
		return getNetState().isNetEnable;
	}


	/**
	 * 网络状态这里获取
	 * 
	 * @return
	 */
	public static NetState getNetState()
	{
		if (netState == null)
		{
			netState = new NetState();
			String type = "";
			ConnectivityManager con = (ConnectivityManager) A.c()
					.getSystemService("connectivity");
			boolean wifi = con.getNetworkInfo(1).isConnectedOrConnecting();
			boolean internet = con.getNetworkInfo(0).isConnectedOrConnecting();

			if (wifi)
			{
				netState.isNetEnable = true;
				netState.isWifiEnable = true;
				netState.isGprsEnable = false;
			}
			else if (internet)
			{
				netState.isWifiEnable = false;
				netState.isNetEnable = true;
				netState.isGprsEnable = true;
			}
			else
			{
				netState.isWifiEnable = false;
				netState.isNetEnable = false;
				netState.isGprsEnable = false;
			}

		}
		return netState;
	}


	@Override
	public void onReceive(Context context, Intent intent)
	{
		BroadCastManager.i().reg("网络状态改变",
				NetStateChangeBroadcastReceiver.class, this);
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobileInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		// NetworkInfo activeInfo = manager.getActiveNetworkInfo();

		getNetState().isWifiEnable = false;
		getNetState().isNetEnable = false;
		getNetState().isGprsEnable = false;
		
		if (mobileInfo.isConnected())
		{
			getNetState().isWifiEnable = false;
			getNetState().isNetEnable = true;
			getNetState().isGprsEnable = true;
		}
		else if (wifiInfo.isConnected())
		{
			getNetState().isNetEnable = true;
			getNetState().isWifiEnable = true;
			getNetState().isGprsEnable = false;
		}

		BroadCastManager.i().send("网络状态改变", getNetState());
	}


	@Override
	public void recBroadCast(String title, Object o)
	{
		if (title.equals("网络状态改变"))
		{
			if (A.a() != null)
			{
				if (A.a() instanceof OnNetworkChange)
				{
					((OnNetworkChange) (A.a())).networkChange(getNetState());
				}
			}
		}

	}

}