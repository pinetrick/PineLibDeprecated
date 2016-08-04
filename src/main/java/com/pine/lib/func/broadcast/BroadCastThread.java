package com.pine.lib.func.broadcast;

import com.pine.lib.func.debug.M;
import com.pine.lib.func.thread.AbsThread;

public class BroadCastThread extends AbsThread
{
	private BroadCastBean broadCaseBean;
	private String title;
	private Object obj;



	public BroadCastThread(BroadCastBean broadCaseBean, String title, Object obj)
	{
		this.broadCaseBean = broadCaseBean;
		this.title = title;
		this.obj = obj;
		M.i().addClass(this);
	}


	@Override
	public void run()
	{
		callToMainThread(null);

	}


	@Override
	public void runInMainThread(Object callMethod)
	{
		broadCaseBean.getCall().recBroadCast(title, obj);

	}

}
