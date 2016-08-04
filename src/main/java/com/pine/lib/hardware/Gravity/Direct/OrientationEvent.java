package com.pine.lib.hardware.Gravity.Direct;

import android.content.Context;
import android.hardware.SensorManager;
import android.view.OrientationEventListener;

import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;

/**
 * 手机方向转动方向感应
 * 
 * CPU占用 比重力感应小得多
 */
public class OrientationEvent {
	private static G g = new G(OrientationEvent.class);
	
	private OnOrientationChangeListener onOrientationChangeListener = null;
	
	public OrientationEvent(Context context)
	{
		M.i().addClass(this);
		OrientationEventListener listener = new OrientationEventListener(context,
				SensorManager.SENSOR_DELAY_UI) {

			@Override
			public void onOrientationChanged(int orientation) {
				g.d("方向感应 - " + orientation);
				if (onOrientationChangeListener != null)
				{
					onOrientationChangeListener.changeTo(orientation);
				}
			}
			
			
		};
		
		listener.enable();
	}
	
	public void setOnOrientationChangeListener(OnOrientationChangeListener onOrientationChangeListener)
	{
		this.onOrientationChangeListener = onOrientationChangeListener;
	}
	
}
