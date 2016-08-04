package com.pine.lib.windows.floatWindow;

import android.R.integer;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.pine.lib.R;
import com.pine.lib.base.activity.A;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.window.DebugRuntimeWindow;
import com.pine.lib.func.myTimer.MyTimer;
import com.pine.lib.func.myTimer.inThread.MyTimerInThread;
import com.pine.lib.miui.flywindows.MIUIAuthorityAdmin;
import com.pine.lib.miui.flywindows.MIUIFlyWindows;
import com.pine.lib.storage.SharePreferenceTool;
import com.pine.lib.windows.alter.MessageBox;
import com.pine.lib.windows.alter.OnMessageClickListener;

public abstract class BaseFloatWindow implements OnTouchListener
{
	private static G g = new G(BaseFloatWindow.class);
	private boolean isAdded = false; // 是否已增加悬浮窗
	private static WindowManager wm;
	private static WindowManager.LayoutParams params;
	private View floatView;
	private LayoutInflater mInflater;
	private View btn;



	public abstract int getLayoutRes();


	// A.c().getResources().getDimensionPixelSize(R.dimen.w12b);
	public abstract int getWidth();


	public abstract int getHeight();


	public abstract void returnView(View view);


	public BaseFloatWindow()
	{
		View view = create(getLayoutRes(), getWidth(), getHeight());
		returnView(view);
	}


	// 创建悬浮窗
	public View create(int layoutRes, int width, int height)
	{
		mInflater = (LayoutInflater) A.c().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		floatView = mInflater.inflate(layoutRes, null);

		wm = (WindowManager) A.c().getSystemService(Context.WINDOW_SERVICE);
		params = new WindowManager.LayoutParams();

		// 设置window type
		params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
		/*
		 * 如果设置为params.type = WindowManager.LayoutParams.TYPE_PHONE; 那么优先级会降低一些,
		 * 即拉下通知栏不可见
		 */

		params.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明

		// 设置Window flag
		params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		/*
		 * 下面的flags属性的效果形同“锁定”。 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
		 * wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL |
		 * LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE;
		 */

		// 设置悬浮窗的长得宽
		params.width = width;
		params.height = height;

		int h = A.c().getResources().getDimensionPixelSize(R.dimen.h50b);

		params.x = 0;
		params.y = -h;
		// 设置悬浮窗的Touch监听
		floatView.setOnTouchListener(this);

		wm.addView(floatView, params);
		isAdded = true;

		return floatView;

	}


	public void destory()
	{
		wm.removeView(floatView);
	}



	int lastX, lastY;
	int paramX, paramY;



	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				lastX = (int) event.getRawX();
				lastY = (int) event.getRawY();
				paramX = params.x;
				paramY = params.y;
				break;
			case MotionEvent.ACTION_MOVE:
				int dx = (int) event.getRawX() - lastX;
				int dy = (int) event.getRawY() - lastY;
				params.x = paramX + dx;
				params.y = paramY + dy;
				// 更新悬浮窗位置
				wm.updateViewLayout(floatView, params);
				break;
		}
		return false;
	}

}
