package com.pine.lib.base.activity;

import afinal.FinalActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.pine.lib.func.broadcast.BroadCastManager;
import com.pine.lib.func.broadcast.BroadCastNumber;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;
import com.pine.lib.func.gesture.GestureHelper;
import com.pine.lib.func.gesture.OnFlingListener;
import com.pine.lib.net.state.change.NetState;
import com.pine.lib.net.state.change.NetStateChangeBroadcastReceiver;
import com.pine.lib.net.state.change.OnNetworkChange;

/**
 * 本LIB的初始化上下文设置的地方，提供一些基础的函数 请将所有Activity继承这里
 * 
 * <pre>
 * 
 * </pre>
 */
public class PineActivity extends Activity implements OnNetworkChange,
		OnFlingListener

{
	private static G g = new G(PineActivity.class);
	/**
	 * 累计一共启动了多少个Activity
	 */
	public static int startActivityNumber = 0;
	/**
	 * 这是第几个Activity
	 */
	protected int thisNumber;
	protected GestureHelper gh;



	public void finish(int outTo)
	{
		ActicityExtend.finish(this, outTo);

	}


	protected void delayFinish(int delay)
	{
		ActicityExtend.delayFinish(this, delay);
	}


	@Override
	protected void onPause()
	{
		BaiduTongjiResumeCall.pause();
		super.onPause();
	}


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		A.s(this);
		M.i().addClass(this);
		startActivityNumber++;
		thisNumber = startActivityNumber;
		BroadCastNumber.setNumber(thisNumber);
		super.onCreate(savedInstanceState);
	}


	public Boolean onReturnBtnClick(int keyCode, KeyEvent event)
	{
		return super.onKeyDown(keyCode, event);
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			return onReturnBtnClick(keyCode, event);
		}
		return super.onKeyDown(keyCode, event);
	}


	@Override
	protected void onResume()
	{
		A.s(this);
		BroadCastNumber.setNumber(thisNumber);
		BaiduTongjiResumeCall.resume();
		gestureListener();

		super.onResume();
	}


	private void gestureListener()
	{
		gh = new GestureHelper(this);
		gh.setOnFlingListener(this);
	}


	/**
	 * 向左滑动 - 手势识别
	 */
	@Override
	public void OnFlingLeft()
	{
		
	}


	/**
	 * 向右滑动 - 手势识别
	 */
	@Override
	public void OnFlingRight()
	{

	}

	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		gh.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}





	@Override
	protected void onDestroy()
	{
		if (BroadCastManager.isI())
		{
			BroadCastManager.i().remove(thisNumber);
		}
		super.onDestroy();
	}


	/**
	 * 当网络改变的时候回调
	 */
	public void networkChange()
	{
		networkChange(NetStateChangeBroadcastReceiver.getNetState());
	}


	/**
	 * 当网络改变的时候回调 可以在网络改变时查看网络当前状态 3g wifi
	 */
	@Override
	public void networkChange(NetState netState)
	{
		// 请在子类中重写
	}

}
