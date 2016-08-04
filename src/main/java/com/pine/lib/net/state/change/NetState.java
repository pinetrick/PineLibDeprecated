package com.pine.lib.net.state.change;

public final class NetState
{
	public Boolean isWifiEnable = false;
	public Boolean isGprsEnable = false;
	public Boolean isNetEnable = false;



	@Override
	public String toString()
	{
		if (isWifiEnable)
		{
			return "Wifi网络可用";
		}
		else if (isGprsEnable)
		{
			return "Gprs网络可用";
		}
		else
		{
			return "无网络可用";
		}

	}

}
