package com.pine.lib.view.viewpager;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;

/**
 * 这个作为Viewpage的那条线使用，作用是动画效果
 * 
 * <pre>
 * 这个类暂时不推荐使用 请使用UnderlinePageIndicator
 * </pre>
 */
public class ViewPageLine extends View
{
	// ====================允许用户赋值部分 ====================
	/**
	 * 一共含有多少个选项卡
	 */
	private int sumNumber = 4;
	/**
	 * 移动的目标位置
	 * 
	 * <pre>
	 * 3.3含义 = 第三个选项卡和第四个选项卡之间
	 * </pre>
	 */
	private float subNumber = 3.3f;
	/**
	 * 线的颜色
	 */
	private int color = Color.BLUE;
	/**
	 * 线的颜色
	 */
	private int bgColor = Color.WHITE;
	// ==================== 下方数据由系统动态运算 ====================
	/**
	 * 控件的宽度 动态运算
	 */
	private int width = -1;
	/**
	 * 控件的高度 动态运算
	 */
	private int height = -1;
	/**
	 * 每个选项卡的宽度 动态运算
	 */
	private int preWidth = -1;
	/**
	 * 当前线的起始位置 动态运算
	 */
	private float lineBegin = 0;
	/**
	 * 画笔
	 */
	private Paint paint = null;



	@Override
	protected void onDraw(Canvas canvas)
	{
		if (width <= 0)
		{
			width = canvas.getWidth();
			height = canvas.getHeight();
			preWidth = width / sumNumber;
			paint = new Paint();
			paint.setColor(color);
		}
		canvas.drawColor(bgColor);
		if (lineBegin != subNumber)
		{
			// 向目标位置移动
			if (Math.abs(lineBegin - subNumber) < 0.05)
			{
				lineBegin = subNumber;
			}
			else
			{
				lineBegin += getFlag(subNumber - lineBegin)
						* Math.abs(lineBegin - subNumber) / 5;
			}
		}

		float startX = preWidth * lineBegin;
		float stopX = preWidth * (lineBegin + 1);

		canvas.drawRect(startX, 0, stopX, height, paint);

		if (lineBegin != subNumber)
		{
			postInvalidate();
		}

		super.onDraw(canvas);
	}


	private int getFlag(float f)
	{
		if (f >= 0) return 1;
		else return -1;
	}


	public ViewPageLine(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}


	public int getSumNumber()
	{
		return sumNumber;
	}


	/**
	 * 设置选项卡总个数
	 * 
	 * <pre>
	 * 下标从1开始
	 * </pre>
	 * 
	 * @param sumNumber
	 */
	public void setSumNumber(int sumNumber)
	{
		this.sumNumber = sumNumber;
	}


	/**
	 * 移动的目标位置
	 * 
	 * <pre>
	 * 3.3含义 = 第2个选项卡和第3个选项卡之间
	 * 0 含义 = 最开始位置
	 * 4 含义 = 第5个选项卡
	 * </pre>
	 * 
	 * @param sumNumber
	 */
	public void setSubNumber(float subNumber)
	{
		this.subNumber = subNumber;
		postInvalidate();
	}


	/**
	 * 设置线的颜色
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param color
	 */
	public void setColor(int color)
	{
		this.color = color;
	}


	public int getBgColor()
	{
		return bgColor;
	}


	/**
	 * 设置背景色
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param bgColor
	 */
	public void setBgColor(int bgColor)
	{
		this.bgColor = bgColor;
	}

}
