package com.pine.lib.hardware.vibrate;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

import com.pine.lib.base.activity.A;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.myTimer.MyTimer;
import com.pine.lib.func.myTimer.onTimerListener;
import com.pine.lib.view.debugerrbox.E;

/**
 * 振动器类
 * 
 * <pre>
 * 需要权限
 * <uses-permission android:name="android.permission.VIBRATE" />
 * 
 * 静态方法调用 Vibrate.run(this, 500);
 * 
 * </pre>
 */
public class Vibrate
{
	private static G g = new G(Vibrate.class);
	public static MyTimer myTimer;
	public static Boolean needVibrate = false;



	public static void beginUntilCallStopFunc()
	{
		if (needVibrate != true)
		{
			needVibrate = true;
			myTimer = new MyTimer(500);
			myTimer.setOnTimerListener(new onTimerListener() {

				@Override
				public void onTimer()
				{
					if (needVibrate)
					{
						run(200);
						beginUntilCallStopFunc();
					}
				}
			}).start();
		}
	}


	public static void stop()
	{
		needVibrate = false;
		myTimer.stop();
	}


	public static void run(long milliseconds)
	{
		run(A.c(), milliseconds);
	}


	public static void run(final Context activity, long milliseconds)
	{
		try
		{
			Vibrator vib = (Vibrator) activity
					.getSystemService(Service.VIBRATOR_SERVICE);
			vib.vibrate(milliseconds);
		}
		catch (Exception e)
		{
			E.e("请检查振动器权限");
		}

	}


	public static void run(final Activity activity, long[] pattern,
			boolean isRepeat)
	{
		Vibrator vib = (Vibrator) activity
				.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(pattern, isRepeat ? 1 : -1);
	}
}
