package com.pine.lib.func.debug.window.notice;

public class DebugInfoBean
{
	private String showMsg = "显示的功能";
	private String broadcastInfo = "将要发送的广播";



	public String getShowMsg()
	{
		return showMsg;
	}


	public void setShowMsg(String showMsg)
	{
		this.showMsg = showMsg;
	}


	public String getBroadcastInfo()
	{
		return broadcastInfo;
	}


	public void setBroadcastInfo(String broadcastInfo)
	{
		this.broadcastInfo = broadcastInfo;
	}


	@Override
	public String toString()
	{
		return "InfoBean [showMsg=" + showMsg + ", broadcastInfo="
				+ broadcastInfo + "]";
	}

}
