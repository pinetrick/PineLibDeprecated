package com.pine.lib.view.loading;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.pine.lib.R;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;
import com.pine.lib.func.myTimer.MyTimer;
import com.pine.lib.func.myTimer.onTimerListener;

/**
 * 一个加载进度条效果 仿android studio
 * 
 * <pre>
 * 
 * </pre>
 */
public class LoadingView extends View implements onTimerListener
{
	private static G g = new G(LoadingView.class);
	private MyTimer myTimer = new MyTimer(50);
	private int deviation = 0;
	/**
	 * 没有用 防止new 消耗内存
	 */
	private Paint paint = new Paint();
	/**
	 * 界面的图片 防止new 消耗内存
	 */
	private Bitmap bmp = null;
	private int width = -1;
	private int height = -1;
	/**
	 * 进度条的数量
	 */
	private int num = -1;



	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas)
	{
		g.d("界面重绘了");

		if (bmp == null)
		{
			width = canvas.getWidth();
			height = canvas.getHeight();

			bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.loading_bg);
			bmp = Bitmap.createScaledBitmap(bmp, height, height, true);
			num = width / height;
		}

		deviation += 15;

		if (deviation > height * 2)
		{
			deviation = height;
		}

		for (int i = 0; i < num + 2; i++)
		{
			canvas.drawBitmap(bmp, i * height - deviation, 0, paint);
		}
		
		super.onDraw(canvas);
	}


	/**
	 * 设置速度 默认50
	 * 
	 * <pre>
	 * 值越大 速度越慢
	 * </pre>
	 * 
	 * @param spend
	 */
	public void setSpend(int spend)
	{
		myTimer = new MyTimer(spend);
	}


	public void start()
	{
		if (myTimer != null)
		{
			myTimer.setOnTimerListener(this).start();
		}
	}


	public void stop()
	{
		if (myTimer != null)
		{
			myTimer.stop();
		}
	}


	@Override
	public void onTimer()
	{
		postInvalidate();
	}


	public LoadingView(Context context)
	{
		super(context);
		M.i().addClass(this);
	}


	public LoadingView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		M.i().addClass(this);
	}


	public LoadingView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		M.i().addClass(this);
	}

}
