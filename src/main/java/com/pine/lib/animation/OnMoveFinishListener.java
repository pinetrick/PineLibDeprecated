package com.pine.lib.animation;

import android.view.View;

public interface OnMoveFinishListener
{
	/**
	 * 移动结束时回调
	 * <pre>
	 * 
	 * </pre>
	 * @param view 被移动的VIew
	 * @return 是否继续后续动画
	 */
	public Boolean moveFinish(View view);
	
}
