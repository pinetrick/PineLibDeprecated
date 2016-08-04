package com.pine.lib.base.activity;

import afinal.annotation.view.Select;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

import com.pine.lib.func.crash.CrashHandler;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.window.CrashBean;
import com.pine.lib.func.debug.window.DebugRuntimeWindow;
import com.pine.lib.storage.sqlite.SqliteManager;

/**
 * 上下文存储类 你可以从这里得到上下文
 * 
 * <pre>
 * 
 * </pre>
 */
public class A
{
	private static Context context;
	private static Activity activity;
	private static Application application;
	private static DebugRuntimeWindow debugRuntimeWindow = null;
	


	/**
	 * 获取当前活动Activity
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public static Activity a()
	{
		return activity;
	}


	/**
	 * 获取应用Application
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public static Application app()
	{
		return application;
	}


	/**
	 * 这个是应用上下文 ，你可以用a()获取当前activity上下文
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public static Context c()
	{
		if (application != null)
		{
			return application.getApplicationContext();
		}
		else if (activity != null)
		{
			return activity.getApplicationContext();
		}
		else 
		{
			return context;
		}
		
	}

	/**
	 * 设置Activity
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param activity
	 */
	public static void s(Context context)
	{
		A.context = context;
		

	}
	/**
	 * 设置Activity
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param activity
	 */
	public static void s(Activity activity)
	{
		A.activity = activity;
		if (G.getEnableGlobalDebug())
		{
			if (debugRuntimeWindow == null)
			{
				debugRuntimeWindow = new DebugRuntimeWindow();
				debugRuntimeWindow.createFloatView();
			}

		}

		// 设置保持屏幕常亮
		if (A.app() instanceof ConfigApplication)
		{
			if (G.getEnableGlobalDebug())
			{
				if (((ConfigApplication) A.app()).KeepScreenOnWhenDbg())
				{
					activity.getWindow().addFlags(
							WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
				}
			}
		}

	}


	/**
	 * 设置Application
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param Application
	 */
	public static void s(Application application)
	{
		A.application = application;
		if (G.getEnableGlobalDebug())
		{
			SqliteManager.i("crash.db").createTable(CrashBean.class);
			CrashHandler.i(application.getApplicationContext());
		}
		// else//发布后这个错误交给 百度统计去收集吧
		// {
		// ReleaseCrashHandler.i(application.getApplicationContext());
		// }
	}
}
