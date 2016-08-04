package com.pine.lib.windows.waiter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;

import com.pine.lib.R;
import com.pine.lib.base.activity.A;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;
import com.pine.lib.func.myTimer.MyTimer;
import com.pine.lib.func.myTimer.onTimerListener;
import com.pine.lib.windows.waiter.style.FullScreenWaitDialog;
import com.pine.lib.windows.waiter.style.MyAlterDialog;
import com.pine.lib.windows.waiter.style.OnLineWordsWaitDialog;

/**
 * 消息框
 * 
 * <pre>
 * 
 * </pre>
 */
public class WaitBox implements OnKeyListener, onTimerListener
{
	private static Context lastContext;
	private static WaitBox waitBox = null;
	private static G g = new G(WaitBox.class);
	/**
	 * 最短现实的时间
	 */
	private static int minShowTimes = 500;
	private Context context;
	private Boolean cancelable = true;

	private WaitBoxListener listener;
	private int timeOut = 30000;

	private MyTimer minShowMyTimer;
	private MyAlterDialog dialog;

	private WaitBoxStyle style = WaitBoxStyle.OneLineWords;
	private Boolean isShowing = false;
	/**
	 * 计时器超时计时器 默认值30秒
	 */
	private MyTimer timeOutTimer;
	/**
	 * 背景色资源
	 */
	private int backgroundResId = R.drawable.warning;


	/**
	 * 设置对话框是否能够被取消
	 * 
	 * @param cancelable
	 * @return
	 */
	public WaitBox setCancelable(Boolean cancelable)
	{
		this.cancelable = cancelable;
		if (dialog != null)
		{
			dialog.setCancelable(cancelable);
		}
		return this;
	}


	private WaitBox(Context context)
	{
		this.context = context;
		M.i().addClass(this);
	}


	/**
	 * 初始化 单例模式启动！
	 * 
	 * <pre>
	 * 
	 * 必须将基础activity继承QFActivity/QFFragmentActivity
	 * 才能用这个初始化 
	 * 否则 请传入上下文
	 * </pre>
	 * 
	 * @return
	 */
	public static WaitBox i()
	{
		return WaitBox.i(A.a());
	}


	public synchronized static WaitBox i(Context context)
	{
		if (lastContext != context)
		{// 上下文过期
			if (waitBox != null)
			{
				waitBox.cancel();
			}
			waitBox = null;
			lastContext = context;
		}

		if (waitBox == null) waitBox = new WaitBox(context);
		return waitBox;
	}


	/**
	 * 通过这里显示提示框
	 * 
	 * @param message
	 * @param buttons
	 */
	public void show(String message)
	{
		show(message, "");
	}




	/**
	 * 通过这里显示提示框
	 * 
	 * @param message
	 * @param buttons
	 */
	public void show(String message1, String message2)
	{
		if (message1 == null)
		{
			g.e("提示信息1不能为null");
			return;
		}
		if (message2 == null)
		{
			g.e("提示信息2不能为null");
			return;
		}
		if (context == null)
		{
			g.e("等待框无法显示 - 上下文失效，将要显示的消息: " + message1);
			return;
		}
		cancel();

		// 窗体风格设置
		if (style == WaitBoxStyle.OneLineWords)
		{
			dialog = new OnLineWordsWaitDialog(context, R.style.dialog);
		}
		else if (style == WaitBoxStyle.FullScreen)
		{
			dialog = new FullScreenWaitDialog(context, R.style.dialog);
		}
		dialog.setCanceledOnTouchOutside(cancelable);// 设置点击Dialog外部任意区域关闭Dialog
		dialog.show();
		dialog.setOnKeyListener(this);

		dialog.setText(message1, message2);
		dialog.setBackground(backgroundResId);

		isShowing = true;
		enableDismiss = false;

		if (timeOut > 0)
		{
			timeOutTimer = new MyTimer(timeOut);
			timeOutTimer.setOnTimerListener(this).startOnce();
		}

		minShowMyTimer = new MyTimer(minShowTimes);
		minShowMyTimer.setOnTimerListener(new onTimerListener() {

			@Override
			public void onTimer()
			{
				if (enableDismiss)
				{
					isShowing = false;
					dismiss();
				}
				enableDismiss = true;

			}
		}).startOnce();

	}
	/**
	 * 设置对话框背景
	 * @param backgroundResId
	 */
	public WaitBox setBackgroundResId(int backgroundResId)
	{
		this.backgroundResId = backgroundResId;
		return this;
	}


	private Boolean enableDismiss = false;



	/**
	 * 隐藏这个等待框子
	 * 
	 * <pre>
	 * 
	 * </pre>
	 */
	public void cancel()
	{
		if (timeOutTimer != null)
		{
			timeOutTimer.stop();
		}
		if (enableDismiss)
		{
			isShowing = false;
			dismiss();
		}
		else
		{
			enableDismiss = true;
		}
	}


	private void dismiss()
	{
		if (dialog != null && dialog.isShowing())
		{
			try
			{
				dialog.cancel();
			}
			catch (Exception e)
			{
				g.e("请稍后框子无法正常取消");
			}
			
		}
	}


	public WaitBoxStyle getStyle()
	{
		return style;
	}


	public WaitBox setStyle(WaitBoxStyle style)
	{
		this.style = style;
		return this;
	}


	public WaitBoxListener getListener()
	{
		return listener;
	}


	public WaitBox setListener(WaitBoxListener listener)
	{
		this.listener = listener;
		return this;
	}


	@Override
	public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent arg2)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (cancelable == false)
			{
				return true;
			}

			// 消耗事件
			timeOutTimer.stop();
			if (listener != null)
			{
				if (isShowing)
				{
					listener.onWaitBoxCancel();
					isShowing = false;
					
				}
			}
		}

		return false;
	}


	public int getTimeOut()
	{
		return timeOut;
	}


	public WaitBox setTimeOut(int timeOut)
	{
		this.timeOut = timeOut;
		return this;
	}


	@Override
	public void onTimer()
	{

		if (isShowing)
		{
			cancel();
			isShowing = false;
			if (listener != null)
			{
				listener.onWaitBoxTimeOut();
			}
		}

	}




}