package com.pine.lib.func.debug.window;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Debug;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.pine.lib.R;
import com.pine.lib.base.activity.A;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;
import com.pine.lib.func.debug.window.notice.NoticeBox;
import com.pine.lib.func.myTimer.MyTimer;
import com.pine.lib.func.myTimer.inThread.MyTimerInThread;
import com.pine.lib.func.myTimer.inThread.onTimerListener;
import com.pine.lib.hardware.info.Cpu.CpuInfo;
import com.pine.lib.hardware.info.Mem.Mem;
import com.pine.lib.miui.flywindows.MIUIAuthorityAdmin;
import com.pine.lib.miui.flywindows.MIUIFlyWindows;
import com.pine.lib.storage.SharePreferenceTool;
import com.pine.lib.view.fasttoast.T;
import com.pine.lib.windows.alter.MessageBox;
import com.pine.lib.windows.alter.OnMessageClickListener;

/**
 * 调试悬浮窗
 * 
 * <pre>
 * 需要权限
 * <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
 * </pre>
 */
public class DebugRuntimeWindow implements OnTouchListener, OnClickListener,
		onTimerListener
{
	private static G g = new G(DebugRuntimeWindow.class);
	private boolean isAdded = false; // 是否已增加悬浮窗
	private static WindowManager wm;
	private static WindowManager.LayoutParams params;
	private View btn_floatView;
	private LayoutInflater mInflater;
	private RelativeLayout mem, cpu;
	private Button btn;
	private MyTimerInThread myTimer;



	/**
	 * 创建悬浮窗
	 */
	public void createFloatView()
	{

		myTimer = new MyTimerInThread(1000);
		myTimer.setOnTimerListener(this);
		myTimer.start();

		mInflater = (LayoutInflater) A.c().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		btn_floatView = mInflater.inflate(R.layout.debug_runtime_flywindow,
				null);

		mem = (RelativeLayout) btn_floatView.findViewById(R.id.mem_info);
		cpu = (RelativeLayout) btn_floatView.findViewById(R.id.cpu_info);

		btn = (Button) btn_floatView.findViewById(R.id.btn_debug);

		btn.setText("C100%\nM100%");

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
		params.width = A.c().getResources().getDimensionPixelSize(R.dimen.w12b);
		params.height = A.c().getResources()
				.getDimensionPixelSize(R.dimen.w12b);

		int h = A.c().getResources().getDimensionPixelSize(R.dimen.h44b);

		params.x = 0;
		params.y = -h;
		// 设置悬浮窗的Touch监听
		btn.setOnTouchListener(this);
		btn.setOnClickListener(this);

		try
		{
			wm.addView(btn_floatView, params);
		}
		catch (Exception e)
		{
			MessageBox.i().show("调试器无法运行，请检查是否有创建悬浮窗权限！");
		}
		isAdded = true;

		// 检查MIUI悬浮窗权限
		if (!MIUIFlyWindows.isMiuiFloatWindowOpAllowed())
		{
			Boolean s = SharePreferenceTool.i().getValue("悬浮窗被关闭提示框是否显示过",
					false);
			if (!s)
			{
				// T.t("5秒钟后，将请您设置弹出窗权限");
				MyTimer myTimer = new MyTimer(5000);
				myTimer.setOnTimerListener(
						new com.pine.lib.func.myTimer.onTimerListener() {

							@Override
							public void onTimer()
							{

								MessageBox
										.i()
										.cleanMessageQueueAndLock()
										.setListener(
												new OnMessageClickListener() {

													@Override
													public void messageBoxChoose(
															int id)
													{
														if (id == 1)
														{
															MIUIAuthorityAdmin
																	.openMiuiPermissionActivity(A
																			.a());
														}
														SharePreferenceTool
																.i()
																.setValue(
																		"悬浮窗被关闭提示框是否显示过",
																		true);
													}
												})
										.show("[调试运行时]\n请打开应用程序的悬浮窗权限(只显示1次)",
												"打开", "不打开");

							}
						}).startOnce();

			}

		}

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
				wm.updateViewLayout(btn_floatView, params);
				break;
		}
		return false;
	}


	@Override
	public void onClick(View arg0)
	{
		// NoticeBox.addFuncBtn("复制上次崩溃日志");
		// NoticeBox.addFuncBtn("打开应用设置界面");
		NoticeBox.i().show();

	}



	private int cpuPerc = 0, memPrec = 0;



	public DebugRuntimeWindow()
	{
		M.i().addClass(this);
	}


	@Override
	public void onTimer()
	{

		if (G.mainInfo.equals(""))
		{
			btn.setText("C:" + cpuPerc + "%\nM:" + memPrec + "%");
		}
		else
		{
			btn.setText(G.mainInfo);
		}
		// cpu.setBackgroundColor(Color.rgb(255 - cpuPerc * 2, 255, 255));
		// mem.setBackgroundColor(Color.rgb(255 - memPrec * 2, 255, 255));

		// int h1 = A
		// .c()
		// .getResources()
		// .getIdentifier("f" + cpuPerc + "b", "dimension",
		// A.c().getPackageName());
		// int h2 = A
		// .c()
		// .getResources()
		// .getIdentifier("f" + memPrec + "b", "dimension",
		// A.c().getPackageName());

		// g.d("cpuPerc = " + cpuPerc);
		// g.d("h1 = " + h1);
		// g.d("h2 = " + h2);

		LayoutParams params = (LayoutParams) cpu.getLayoutParams();
		params.height = (int) (cpuPerc * 0.85);
		cpu.setLayoutParams(params);

		params = (LayoutParams) mem.getLayoutParams();
		params.height = (memPrec - 60) * 2;
		mem.setLayoutParams(params);

		// cpu.getLayoutParams().height = 20;
		// mem.getLayoutParams().height = 50;
	}



	// ========================= 用于CPU占用模块 ===================
	/**
	 * 从系统启动结束到现在 系统用了多少CPU
	 */
	private long startCpuUsed = 0;
	/**
	 * 到现在为止 本应用 使用了多少CPU
	 */
	private long nowAppCpuUsed = 0;
	/**
	 * 到现在为止 总共CPU利用时间是多少
	 */
	private long nowAllCpuUsed = 0;
	/**
	 * 平均CPU利用率
	 */
	private float averageTime = 0;
	/**
	 * 1秒内消耗的CPU周期
	 */
	private long cpuPreSec = 0;
	/**
	 * 总内存大小
	 */
	private long totleMem = -1;
	/**
	 * 已用内存大小
	 */
	private long usedMem = -1;
	private Mem memHelper = null;



	@Override
	public void onTimerInThead()
	{
		long nowAppCpuUsed1 = CpuInfo.getAppCpuTime();
		long nowAllCpuUsed1 = CpuInfo.getTotalCpuTime();
		averageTime = nowAppCpuUsed1 * 100 / (nowAllCpuUsed1 - startCpuUsed);
		cpuPreSec = nowAppCpuUsed1 - nowAppCpuUsed;

		cpuPerc = (int) cpuPreSec;

		nowAppCpuUsed = nowAppCpuUsed1;
		nowAllCpuUsed = nowAllCpuUsed1;

		// 方法1 - 效率问题

		//
		// if (totleMem < 0)
		// {
		// memHelper = new Mem(A.c());
		// totleMem = memHelper.getTotalMemoryLong();
		// }
		//
		// memPrec = (int) (memHelper.getAvailMemoryLong() * 100 / totleMem);

		// 方法2：

		// long tmp = Debug.threadCpuTimeNanos();
		//
		// cpuPreSec = tmp - nowAllCpuUsed;
		// nowAllCpuUsed = tmp;

		totleMem = Debug.getNativeHeapSize();
		usedMem = Debug.getNativeHeapAllocatedSize();

		memPrec = (int) (usedMem * 100 / totleMem);

		// Debug.stopAllocCounting();
		// Debug.resetAllCounts();
		// Debug.startAllocCounting();

	}

	// MyTimer myTimer = new MyTimer(500);
	// myTimer.setOnTimerListener(new onTimerListener() {
	//
	// @Override
	// public void onTimer()
	// {
	//
	// g.d("getGlobalAllocCount: " + Debug.getGlobalAllocCount());
	// g.d("getGlobalAllocSize: " + Debug.getGlobalAllocSize());
	// g.d("getGlobalClassInitCount: " + Debug.getGlobalClassInitCount());
	// g.d("getGlobalClassInitTime: " + Debug.getGlobalClassInitTime());
	// // g.d("getGlobalExternalAllocCount: " +
	// Debug.getGlobalExternalAllocCount());
	// // g.d("getGlobalExternalAllocSize: " +
	// Debug.getGlobalExternalAllocSize());
	// // g.d("getGlobalExternalFreedCount: " +
	// Debug.getGlobalExternalFreedCount());
	// // g.d("getGlobalExternalFreedSize: " +
	// Debug.getGlobalExternalFreedSize());
	// g.d("getGlobalFreedCount: " + Debug.getGlobalFreedCount());
	// g.d("getGlobalFreedSize: " + Debug.getGlobalFreedSize());
	// g.d("getLoadedClassCount: " + Debug.getLoadedClassCount());
	// g.d("getNativeHeapAllocatedSize: " + Debug.getNativeHeapAllocatedSize());
	// g.d("getNativeHeapFreeSize: " + Debug.getNativeHeapFreeSize());
	// g.d("getNativeHeapSize: " + Debug.getNativeHeapSize());
	// g.d("getThreadAllocCount: " + Debug.getThreadAllocCount());
	// g.d("getThreadAllocSize: " + Debug.getThreadAllocSize());
	// // g.d("getThreadExternalAllocCount: " +
	// Debug.getThreadExternalAllocCount());
	// // g.d("getThreadExternalAllocSize: " +
	// Debug.getThreadExternalAllocSize());
	// g.d("getThreadGcInvocationCount: " + Debug.getThreadGcInvocationCount());
	//
	//
	// g.d("threadCpuTimeNanos: " + Debug.threadCpuTimeNanos());//CPU利用率
	//
	//
	//
	// Debug.stopAllocCounting();
	//
	// Debug.resetAllCounts();
	// Debug.startAllocCounting();
	//
	// //Debug.printLoadedClasses(100);
	//
	// }
	// }).start();

}
