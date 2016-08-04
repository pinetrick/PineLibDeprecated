package com.pine.lib.func.thread;

import com.pine.lib.func.debug.M;

import android.os.Handler;
import android.os.Message;

/**
 * 线程调用方法
 * 
 * <pre>
 * 调用callToMainThread(Object o)函数可以将数据映射到主线程
 * </pre>
 */
public abstract class AbsThread extends Thread
{

	public AbsThread()
	{
		M.i().addClass(this);
	}



	private Object callMethod = null;
	private Handler handler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case 87546:
				{

					runInMainThread(callMethod);

				}
			}
			return false;
		}
	});



	/**
	 * 调用这个函数可以将数据映射到主线程
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param o
	 */
	public void callToMainThread(Object o)
	{
		callMethod = o;
		Message message = new Message();
		message.what = 87546;
		handler.sendMessage(message);
	}


	/**
	 * 当线程开始的时候调用
	 */
	public abstract void run();


	/**
	 * 当线程Call时转到主线程调用
	 */
	public abstract void runInMainThread(Object callMethod);

}
