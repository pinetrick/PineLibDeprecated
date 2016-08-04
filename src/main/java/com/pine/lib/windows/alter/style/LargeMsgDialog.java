package com.pine.lib.windows.alter.style;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.pine.lib.R;

public class LargeMsgDialog extends AbsAlertDialog
{

	public LargeMsgDialog(Context context, int theme)
	{
		super(context, theme);
	}


	public LargeMsgDialog(Context context)
	{
		super(context);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_box_large);
		
	
	}
	
	public LargeMsgDialog getView()
	{
		return this;
	}
	
}
