package com.pine.lib.windows.waiter.style;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.pine.lib.R;
import com.pine.lib.func.myTimer.MyTimer;
import com.pine.lib.func.myTimer.onTimerListener;

public class OnLineWordsWaitDialog extends MyAlterDialog implements
		onTimerListener
{
	private TextView mainMassage;
	private TextView otherMassage;
	private MyTimer myTimer = new MyTimer(500);
	private String docs = "";
	private String message1;
	private String message2;



	public OnLineWordsWaitDialog(Context context, int theme)
	{
		super(context, theme);
	}


	public OnLineWordsWaitDialog(Context context)
	{
		super(context);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wait_box_oneline);

	}


	public void setText(String message1, String message2)
	{
		this.message1 = message1;
		this.message2 = message2;

		mainMassage = (TextView) findViewById(R.id.mainMassage);
		otherMassage = (TextView) findViewById(R.id.otherMassage);

		mainMassage.setText(message1);
		otherMassage.setText(message2);

		myTimer.setOnTimerListener(this).start();
	}


	@Override
	public void onDetachedFromWindow()
	{
		myTimer.stop();
		super.onDetachedFromWindow();
	}


	public OnLineWordsWaitDialog getView()
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

		mainMassage.setText(message1 + docs);

	}

}
