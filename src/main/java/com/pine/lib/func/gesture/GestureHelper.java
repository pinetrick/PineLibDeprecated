package com.pine.lib.func.gesture;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

public class GestureHelper implements OnGestureListener {
	private GestureDetector gesture_detector;
	private int screen_width;
	private OnFlingListener listener_onfling;

	public GestureHelper(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		screen_width = dm.widthPixels;

		gesture_detector = new GestureDetector(context, this);
	}

	public void setOnFlingListener(OnFlingListener listener) {
		listener_onfling = listener;
	}

	public boolean onTouchEvent(MotionEvent event) {
		return gesture_detector.onTouchEvent(event);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		try {
			// 触发条件 ：
			// X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒
			final int FLING_MIN_DISTANCE = (int) (screen_width / 5.0f), FLING_MIN_VELOCITY = 200;
			if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
					&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
				listener_onfling.OnFlingLeft();
			} else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
					&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
				listener_onfling.OnFlingRight();
			}
		} catch (Exception e) {

		}

		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
}
