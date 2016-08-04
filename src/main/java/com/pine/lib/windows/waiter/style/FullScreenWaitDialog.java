package com.pine.lib.windows.waiter.style;

import android.content.Context;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pine.lib.R;
import com.pine.lib.func.myTimer.MyTimer;
import com.pine.lib.func.myTimer.onTimerListener;

public class FullScreenWaitDialog extends MyAlterDialog implements
		onTimerListener
{
	private TextView mainMassage;
	private TextView otherMassage;
	private RelativeLayout baseLayout;
	private MyTimer myTimer = new MyTimer(500);
	private String docs = "";
	private String message1;
	private String message2;



	public FullScreenWaitDialog(Context context, int theme)
	{
		super(context, theme);
	}


	public FullScreenWaitDialog(Context context)
	{
		super(context);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wait_box_fullscreen);

	}


	public void setText(String message1, String message2)
	{
		this.message1 = message1;
		this.message2 = message2;

		mainMassage = (TextView) findViewById(R.id.mainMassage);
		otherMassage = (TextView) findViewById(R.id.otherMassage);
		baseLayout = (RelativeLayout) findViewById(R.id.baseLayout);

		mainMassage.setText(message1);
		otherMassage.setText(message2);

		myTimer.setOnTimerListener(this).start();
	}
	
	@Override
	public void setBackground(int resId)
	{
		baseLayout.setBackgroundResource(resId);
		super.setBackground(resId);
	}


	@Override
	public void onDetachedFromWindow()
	{
		myTimer.stop();
		super.onDetachedFromWindow();
	}


	public FullScreenWaitDialog getView()
	{
		return this;
	}


	@Override
	public void onTimer()
	{
		docs += ".";
		if (docs.length() > 3)
		{
			docs = "";
		}

		otherMassage.setText(message2 + docs);

	}

}
