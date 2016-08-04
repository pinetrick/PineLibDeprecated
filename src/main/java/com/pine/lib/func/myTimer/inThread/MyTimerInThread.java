package com.pine.lib.func.myTimer.inThread;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;

import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;

/**
 * 定时器函数 你可以考虑重写 public void onTimerInThead() 方法 调用方法
 * 
 * MyTimer mt = new MyTimer(); mt.setInterval(1000); mt.setOnTimerListener(new
 * onTimerListener() {
 * 
 * @Override public void onTimerInThead() {
 * 
 *           }
 * @Override public void onTimer() {
 * 
 *           } }).start();
 * 
 * 
 */
public class MyTimerInThread
{
	private static G g = new G(MyTimerInThread.class);
	public Boolean isDebug = false;
	private onTimerListener imt;
	private Boolean Enable = false;
	private int Interval = 1000;
	private Timer timer;
	private TimerTask task;
	private Boolean willStop = false;

	/**
	 * 多少ms执行一次
	 */
	public MyTimerInThread setInterval(int i)
	{
		if (i > 0)
		{
			this.Interval = i;
		}
		return this;
	}


	/**
	 * 启动计时器
	 * 
	 * @return
	 */
	public MyTimerInThread start()
	{
		willStop = false;
		setEnable(true);
		return this;
	}

	/**
	 * 启动计时器一次
	 * 
	 * @return
	 */
	public MyTimerInThread startOnce()
	{
		willStop = true;
		setEnable(true);
		return this;
	}
	
	/**
	 * 停止计时器
	 * 
	 * @return
	 */
	public MyTimerInThread stop()
	{
		setEnable(false);
		return this;
	}


	public MyTimerInThread setEnable(Boolean enable)
	{
		this.Enable = enable;
		if (enable)
		{
			timer = new Timer(true);
			setTask();

			timer.schedule(task, Interval, Interval); // 延时1000ms后执行，1000ms执行一次

			g.d("计时器被启动了" + Interval);
		}
		else
		{
			timer.cancel();
			g.d("计时器已停止");
		}
		return this;

	}


	public MyTimerInThread setOnTimerListener(onTimerListener imt)
	{
		this.imt = imt;
		return this;
	}


	/*
	 * 构造函数
	 */
	public MyTimerInThread()
	{
		M.i().addClass(this);
		setTask();
	}


	public MyTimerInThread(int i)
	{
		this();
		setInterval(i);
	}



	private Handler handler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case 84637:
					if (Enable)
					{
						//g.d("计时器到点了" + Interval);
						if (imt != null)
						{
							imt.onTimer();
						}
						if (willStop)
						{
							stop();
						}
					}
					break;
			}
			return false;
		}
	});



	public void setTask()
	{
		task = new TimerTask() {
			public void run()
			{
				if (imt != null)
				{
					imt.onTimerInThead();
				}
				Message message = new Message();
				message.what = 84637;
				handler.sendMessage(message);
			}
		};
	}

}
