package com.pine.lib.func.taskschedule;

import android.content.Context;
import android.content.Intent;

import com.pine.lib.base.activity.PineBroadcastReceiver;
import com.pine.lib.func.debug.G;
import com.pine.lib.storage.SharePreferenceTool;

/*
 * 需要rev
 * <receiver android:name="com.pine.lib.func.taskschedule.OnBroadcastRev" android:process=":remote" />
 * 
 */
public class OnBroadcastRev extends PineBroadcastReceiver
{
	private static G g = new G(OnBroadcastRev.class);



	@Override
	public void onReceive(Context context, Intent arg1)
	{
		super.onReceive(context, arg1);

		Intent intent = new Intent();
		try
		{
			String classNameString = SharePreferenceTool.i().getValue(
					"广播要启动的activity类名", "");
			g.d("schedule");
			Class class1 = Class.forName(classNameString);
			intent.setClass(context, class1);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
