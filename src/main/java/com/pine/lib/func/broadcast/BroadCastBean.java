package com.pine.lib.func.broadcast;

import android.content.Context;

public class BroadCastBean
{
	private int context = -1;
	/**
	 * 广播头
	 */
	private String title = "";
	/**
	 * 广播的回调
	 */
	private BroadCastRecImp call;
	/**
	 * 广播所属类名
	 */
	private String className = "";



	public String getClassName()
	{
		return className;
	}


	public void setClassName(String className)
	{
		this.className = className;
	}


	public String getTitle()
	{
		return title;
	}


	public void setTitle(String title)
	{
		this.title = title;
	}


	public BroadCastBean(int context, String title,BroadCastRecImp rec,String className)
	{
		this.title = title;
		this.context = context;
		this.call = rec;
		this.className = className;

	}


	@Override
	public String toString()
	{
		return "BroadCaseBean [context=" + context + ", title=" + title + ", call=" + call + "]";
	}


	public BroadCastRecImp getCall()
	{
		return call;
	}


	public void setCall(BroadCastRecImp call)
	{
		this.call = call;
	}


	public int getContext()
	{
		return context;
	}


	public void setContext(int context)
	{
		this.context = context;
	}

}
