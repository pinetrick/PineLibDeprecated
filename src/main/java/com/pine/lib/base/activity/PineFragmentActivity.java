package com.pine.lib.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

import com.pine.lib.func.broadcast.BroadCastManager;
import com.pine.lib.func.broadcast.BroadCastNumber;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;
import com.pine.lib.func.gesture.GestureHelper;
import com.pine.lib.func.gesture.OnFlingListener;
import com.pine.lib.net.state.change.NetState;
import com.pine.lib.net.state.change.NetStateChangeBroadcastReceiver;
import com.pine.lib.net.state.change.OnNetworkChange;
import com.pine.lib.windows.alter.MessageBox;
import com.pine.lib.windows.alter.OnMessageClickListener;

/**
 * 本LIB的初始化上下文设置的地方，提供一些基础的函数 请将所有Activity继承这里
 * 
 * <pre>
 * 
 * </pre>
 */
public class PineFragmentActivity extends FragmentActivity implements
		OnNetworkChange, OnFlingListener
{
	private static G g = new G(PineFragmentActivity.class);

	/**
	 * 这是第几个Activity
	 */
	protected int thisNumber;
	protected GestureHelper gh;



	protected void delayFinish(int delay)
	{
		ActicityExtend.delayFinish(this, delay);
	}


	public void finish(int outTo)
	{
		ActicityExtend.finish(this, outTo);

	}


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		A.s(this);
		M.i().addClass(this);
		PineActivity.startActivityNumber++;
		thisNumber = PineActivity.startActivityNumber;
		BroadCastNumber.setNumber(thisNumber);
		super.onCreate(savedInstanceState);

	}


	@Override
	protected void onPause()
	{
		BaiduTongjiResumeCall.pause();
		super.onPause();
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


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		gh.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
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
	 * 当网络改变的时候回调
	 */
	@Override
	public void networkChange(NetState netState)
	{
		// 请在子类中重写
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
}
