package com.pine.lib.view.textview;

import java.text.DecimalFormat;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.pine.lib.func.myTimer.MyTimer;
import com.pine.lib.func.myTimer.onTimerListener;

public class MyTextView extends TextView implements onTimerListener
{
	private static int step = 100;
	private static int time = 1000;

	private String format = "#.##"; // #.00保留两位小数
	private Context context;
	private Double endNumber = 0.0;
	private Double oldNumber = 0.0;
	private int nowStep = 0;
	private Double oneStep = 0.0;
	private MyTimer myTimer;



	private void init(Context context)
	{
		this.context = context;

	}


	private void setTextF(Double d)
	{
		DecimalFormat formatter = new DecimalFormat(format);
		setText(formatter.format(d));
	}


	public void setText(Double text)
	{
		oldNumber = Double.valueOf(getText().toString());
		endNumber = text;
		nowStep = 0;
		oneStep = (text - oldNumber) / step;
		myTimer = new MyTimer();
		myTimer.setInterval(time / step);
		myTimer.setOnTimerListener(this).start();
	}


	public void setText(Double text, int timer)
	{
		if (timer > 0)
		{
			time = timer;
			setText(text);
		}
		else
		{
			setTextF(text);
		}
	}


	public MyTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}


	public MyTextView(Context context)
	{
		super(context);
		init(context);
	}


	@Override
	public void onTimer()
	{
		oldNumber += oneStep;
		nowStep++;
		if (nowStep >= step)
		{
			setTextF(endNumber);
			myTimer.stop();
		}
		else
		{
			setTextF(oldNumber);
		}
	}


	public String getFormat()
	{
		return format;
	}


	public void setFormat(String format)
	{
		this.format = format;
	}
}
