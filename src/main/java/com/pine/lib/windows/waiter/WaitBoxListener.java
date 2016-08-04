package com.pine.lib.windows.waiter;

public interface WaitBoxListener
{
	/**
	 * 在等待加载超时的时候被回调
	 */
	public void onWaitBoxTimeOut();
	
	/**
	 * 当用户点击返回键的时候回调
	 */
	public void onWaitBoxCancel();
}

