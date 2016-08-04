package com.pine.lib.animation;

import java.lang.ref.WeakReference;

import android.R.integer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;

import com.pine.lib.func.debug.M;
import com.pine.lib.math.Vector.Vector2;

public class MoveAnimation
{
	private WeakReference<View> view;
	private MoveAnimation nextMov;
	/**
	 * 当前实例是否被设置，如果被设置将设置下一个
	 */
	private Boolean isUsed = false;
	private int durationMillis;
	private int startAfterMillis;
	private Vector2<Float> begin;
	private Vector2<Float> end;
	private Boolean 动画结束后还原;
	private OnMoveFinishListener onMoveFinishListener;



	public MoveAnimation(View v)
	{
		M.i().addClass(this);
		view = new WeakReference<View>(v);
	}


	public MoveAnimation go()
	{
		if (view.get().getVisibility() == View.GONE)
		{
			view.get().setVisibility(View.VISIBLE);
		}
		TranslateAnimation animation = new TranslateAnimation(begin.x, end.x,
				-begin.y, -end.y);
		animation.setInterpolator(new OvershootInterpolator());
		animation.setDuration(durationMillis);
		animation.setStartOffset(startAfterMillis);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation)
			{
			}


			@Override
			public void onAnimationRepeat(Animation animation)
			{
			}


			@Override
			public void onAnimationEnd(Animation animation)
			{
				if (!动画结束后还原)
				{
					int left = view.get().getLeft() - (int) (begin.x - end.x);
					int top = view.get().getTop() - (int) (begin.y - end.y);
					int width = view.get().getWidth();
					int height = view.get().getHeight();
					view.get().clearAnimation();
					view.get().layout(left, top, left + width, top + height);
				}
				if (onMoveFinishListener != null)
				{
					if (onMoveFinishListener.moveFinish(view.get()))
					{
						if (nextMov != null)
						{
							nextMov.go();
						}
					}
				}
				

			}
		});
		if (view.get() != null)
		{
			view.get().startAnimation(animation);
		}
		return this;
	}


	/**
	 * 快速移动
	 * 
	 * <pre>
	 * 起始位置是坐标原点，做动画
	 * </pre>
	 * 
	 */
	public MoveAnimation fastMove(int durationMillis, int startAfterMillis,
			final Vector2<Float> begin, final Vector2<Float> end,
			final Boolean 动画结束后还原)
	{
		if (!isUsed)
		{
			isUsed = true;
			this.durationMillis = durationMillis;
			this.startAfterMillis = startAfterMillis;
			this.begin = begin;
			this.end = end;
			this.动画结束后还原 = 动画结束后还原;
		}
		else
		{
			if (nextMov == null) nextMov = new MoveAnimation(view.get());
			nextMov.fastMove(durationMillis, startAfterMillis, begin, end,
					动画结束后还原);
		}
		return this;
	}


	public MoveAnimation setOnMoveFinishListener(
			OnMoveFinishListener onMoveFinishListener)
	{
		if (this.onMoveFinishListener == null)
		{
			this.onMoveFinishListener = onMoveFinishListener;
		}
		else
		{
			if (nextMov == null) nextMov = new MoveAnimation(view.get());
			nextMov.setOnMoveFinishListener(onMoveFinishListener);
		}
		return this;
	}
}
