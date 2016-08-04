package com.pine.lib.func.debug.window.notice;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.pine.lib.R;
import com.pine.lib.base.activity.A;

public class DebugDialog extends AlertDialog
{
	private TextView mainMassage;



	public DebugDialog(Context context, int theme)
	{
		super(context, theme);
	}


	public DebugDialog(Context context)
	{
		super(context);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.debug_runtime_notice_dialog);

		mainMassage = (TextView) findViewById(R.id.mainMassage);
		String activityName = "";
		if (A.a() != null)
		{
			activityName = A.a().getClass().getSimpleName();
		}
		mainMassage.setText("Rain调试器附在:" + activityName);
	}


	public DebugDialog getView()
	{
		return this;
	}

}
