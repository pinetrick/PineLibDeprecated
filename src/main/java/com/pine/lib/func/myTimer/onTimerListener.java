package com.pine.lib.func.myTimer;

public interface onTimerListener {
	/**
	 * 当计时器到点后会被在主线程调用
	 * 更新UI写在这里
	 */
	public void onTimer();
//	/**
//	 * 当计时器到点后会被在线程内调用
//	 * 耗时操作写在这里
//	 */
//	public void onTimerInThead();
}
