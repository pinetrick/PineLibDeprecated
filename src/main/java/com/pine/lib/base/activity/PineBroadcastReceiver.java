package com.pine.lib.base.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

import com.pine.lib.func.broadcast.BroadCastManager;
import com.pine.lib.func.broadcast.BroadCastNumber;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;
import com.pine.lib.func.gesture.GestureHelper;
import com.pine.lib.net.state.change.NetState;
import com.pine.lib.net.state.change.NetStateChangeBroadcastReceiver;
import com.pine.lib.view.fasttoast.T;

/**
 * 本LIB的初始化上下文设置的地方，提供一些基础的函数 请将所有Activity继承这里
 * 
 * <pre>
 * 
 * </pre>
 */
public class PineBroadcastReceiver extends BroadcastReceiver

{
	private static G g = new G(PineBroadcastReceiver.class);



	@Override
	public void onReceive(Context context, Intent arg1)
	{
		A.s(context);
	}

	
	
	
	
	
	
	
	
	
	
	
	
}
