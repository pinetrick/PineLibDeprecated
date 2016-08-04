package com.pine.lib.view.button;

import com.pine.lib.func.debug.M;
import com.pine.lib.func.myTimer.MyTimer;
import com.pine.lib.func.myTimer.onTimerListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

public class FlashBtn extends Button
{

	public FlashBtn(Context context, AttributeSet attrs)
	{
		
		super(context, attrs);
		M.i().addClass(this);
	}

	/**
	 * 
	 * 开启View闪烁效果
	 * 
	 * 
	 * 
	 * */

	public void startFlick(int afterMillStop)
	{
		startFlick();
		MyTimer myTimer = new MyTimer(afterMillStop);
		myTimer.setOnTimerListener(new onTimerListener() {
			
			@Override
			public void onTimer()
			{
				stopFlick();
				
			}
		}).startOnce();
		
	
	}
	/**
	 * 
	 * 开启View闪烁效果
	 * 
	 * 
	 * 
	 * */

	public void startFlick()
	{

		Animation alphaAnimation = new AlphaAnimation(1, 0);
		alphaAnimation.setDuration(100);
		alphaAnimation.setInterpolator(new LinearInterpolator());
		alphaAnimation.setRepeatCount(Animation.INFINITE);
		alphaAnimation.setRepeatMode(Animation.REVERSE);
		startAnimation(alphaAnimation);
	}


	/**
	 * 
	 * 取消View闪烁效果
	 * 
	 * 
	 * 
	 * */

	public void stopFlick()
	{

		clearAnimation();
	}

}
