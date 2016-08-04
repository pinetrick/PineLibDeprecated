package com.pine.lib.base.activity;

import com.pine.lib.R;
import com.pine.lib.func.intentcall.I;
import com.pine.lib.func.myTimer.MyTimer;
import com.pine.lib.func.myTimer.onTimerListener;

import android.app.Activity;

public class ActicityExtend
{
	public static void finish(Activity activity, int to)
	{
		activity.finish();
		I.i(R.anim.in_from_none, to);
	}
	
	public static void delayFinish(final Activity activity, int delay)
	{
		MyTimer myTimer = new MyTimer(delay);
		myTimer.setOnTimerListener(new onTimerListener() {
			
			@Override
			public void onTimer()
			{
				activity.finish();
				
			}
		}).startOnce();
		
		
	}
}
