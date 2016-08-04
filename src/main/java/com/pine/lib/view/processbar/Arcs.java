package com.pine.lib.view.processbar;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;

public class Arcs extends View
{
	private static G g = new G(Arcs.class);
	private Paint 前景色画笔;
	private Paint 背景色画笔;

	private RectF 外测边界;
	private float 圆弧扫过的角度 = 0;
	private int 最大值 = 200;
	private int 变色中间刻度值 = 100;
	private int 递增值 = 0;
	private int 当前值 = 0;
	private float 画笔粗细 = 30;

	private final float 速度递增常量 = 2;



	private void init()
	{
//		前景色画笔.setStrokeCap(Paint.Cap.ROUND);
		
		前景色画笔 = new Paint(Paint.ANTI_ALIAS_FLAG);
		前景色画笔.setStyle(Paint.Style.STROKE);
		前景色画笔.setStrokeWidth(画笔粗细);
		前景色画笔.setColor(0xff977cff);
		BlurMaskFilter mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.INNER);
		前景色画笔.setMaskFilter(mBlur);

		背景色画笔 = new Paint(Paint.ANTI_ALIAS_FLAG);
		背景色画笔.setStyle(Paint.Style.STROKE);
		背景色画笔.setStrokeWidth(画笔粗细 + 10);
		背景色画笔.setColor(0xff333333);
		BlurMaskFilter mBGBlur = new BlurMaskFilter(8,
				BlurMaskFilter.Blur.INNER);
		背景色画笔.setMaskFilter(mBGBlur);

		g.d("ProcessBar Init");
	}


	public Arcs(Context context)
	{
		super(context);
		init();
		M.i().addClass(this);
	}


	public Arcs(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
		M.i().addClass(this);
	}


	public Arcs(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init();
		M.i().addClass(this);
	}


	@Override
	protected void onSizeChanged(int w, int h, int ow, int oh)
	{
		super.onSizeChanged(w, h, ow, oh);
		
		外测边界 = new RectF(画笔粗细, 画笔粗细, w - 画笔粗细, h * 2);
	}


	@Override
	protected void onDraw(Canvas canvas)
	{

		g.d("ProcessBar Draw");

		drawSpeed(canvas);
		calcSpeed();

	}


	private void drawSpeed(Canvas canvas)
	{

		圆弧扫过的角度 = (float) 递增值 / 最大值 * 180;
		g.d("sweep" + 圆弧扫过的角度);
		// if (递增值 > 变色中间刻度值)
		// {
		// 前景色画笔.setColor(0xFFFF0000);
		// }
		// else
		// {
		// 前景色画笔.setColor(0xFF00B0F0);
		// }

		canvas.drawArc(外测边界, 180, 180, false, 背景色画笔);
		canvas.drawArc(外测边界, 180, 圆弧扫过的角度, false, 前景色画笔);
	}


	private void calcSpeed()
	{
		if (递增值 < 当前值)
		{
			递增值 += 速度递增常量;
			if (递增值 > 当前值)
			{
				递增值 = 当前值;
			}
			invalidate();
		}
		else if (递增值 > 当前值)
		{
			递增值 -= 速度递增常量;
			if (递增值 < 当前值)
			{
				递增值 = 当前值;
			}
			invalidate();
		}
	}


	public int get当前值()
	{
		return 当前值;
	}


	public int get最大值()
	{
		return 最大值;
	}


	public void set最大值(int 最大值)
	{
		this.最大值 = 最大值;
	}


	public void set当前值(int 当前值)
	{
		this.当前值 = 当前值;
	}
}
