package com.pine.lib.hardware.info.Switch.wifi;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.pine.lib.base.activity.A;
import com.pine.lib.hardware.info.Switch.SwitchChangeInterface;

public class Wifi implements SwitchChangeInterface
{

	@Override
	public Boolean isOpen()
	{
		WifiManager wifiManager = (WifiManager) A.c().getSystemService("wifi");

		if (wifiManager != null)
		{
			int wifiState = wifiManager.getWifiState();
			if (wifiState == 3)
			{
				return true;
			}

		}

		return false;
	}


	@Override
	public Boolean openThis()
	{
		WifiManager wifiManager = (WifiManager) A.c().getSystemService(
				Context.WIFI_SERVICE);
		if (!wifiManager.isWifiEnabled())
		{
			wifiManager.setWifiEnabled(true);
			return true;
		}
		return false;
	}


	@Override
	public Boolean closeThis()
	{
		WifiManager wifiManager = (WifiManager) A.c().getSystemService(
				Context.WIFI_SERVICE);
		if (wifiManager.isWifiEnabled())
		{
			wifiManager.setWifiEnabled(false);
			return true;
		}
		return false;
	}


	/**
	 * 获取wifi的MAC地址 使用是wlan mac
	 * 
	 * @return
	 */
	public String getWifiMacAddress()
	{
		WifiManager wifi_service = (WifiManager) A.c()
				.getSystemService("wifi");
		WifiInfo wifiInfo = wifi_service.getConnectionInfo();
		return wifiInfo.getMacAddress();
	}


	/**
	 * 打开跳转到wifi设置界面 可以使用
	 */
	public void openWifiSetting()
	{
		Intent intent = new Intent("android.settings.WIFI_SETTINGS");

		A.a().startActivity(intent);
	}


	/**
	 * wifi打开的前提下，获取wifi的所有相关信息(如地址，id，网速等) 可以使用
	 * 
	 * @param wifiOpen
	 * @return
	 */
	public WifiInfo getWifiInfo(boolean wifiOpen)
	{
		if (wifiOpen)
		{
			WifiManager wifi_service = (WifiManager) A.c().getSystemService(
					"wifi");
			WifiInfo wifiInfo = wifi_service.getConnectionInfo();
			StringBuilder sb = new StringBuilder();
			sb.append(wifiInfo.getBSSID());
			sb.append(wifiInfo.getSSID());
			sb.append(wifiInfo.getIpAddress());

			sb.append(wifiInfo.getMacAddress());
			sb.append(wifiInfo.getNetworkId());
			sb.append(wifiInfo.getLinkSpeed());
			sb.append(wifiInfo.getRssi());

			return wifiInfo;
		}

		return null;
	}


	/**
	 * 获取wifi的ip地址
	 * 
	 * @return
	 */
	public String getWifiIp()
	{
		WifiManager wifiManager = (WifiManager) A.c().getSystemService("wifi");
		int ip = wifiManager.getConnectionInfo().getIpAddress();
		return intToIp(ip);
	}


	private static String intToIp(int i)
	{
		return (i & 0xFF) + "." + (i >> 8 & 0xFF) + "." + (i >> 16 & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}


	/**
	 * 获取wifi的连接速度 可以使用 速度mbps
	 * 
	 * @return
	 */
	public int getWifiLinkSpeed()
	{
		WifiManager wifi_service = (WifiManager) A.c().getSystemService("wifi");
		WifiInfo wifiInfo = wifi_service.getConnectionInfo();
		return wifiInfo.getLinkSpeed();
	}


	/**
	 * 获取wifi的Rssi（信号强度:0 到 -100） 可以使用
	 * 
	 * @return
	 */
	public int getWifiRssi()
	{
		WifiManager wifi_service = (WifiManager) A.c().getSystemService("wifi");
		WifiInfo wifiInfo = wifi_service.getConnectionInfo();
		return wifiInfo.getRssi();
	}
}
