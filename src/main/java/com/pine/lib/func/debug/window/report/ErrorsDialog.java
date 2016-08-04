package com.pine.lib.func.debug.window.report;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.pine.lib.R;


public class ErrorsDialog extends AlertDialog
{

	


	public ErrorsDialog(Context context, int theme)
	{
		super(context, theme);
	}


	public ErrorsDialog(Context context)
	{
		super(context);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.debug_runtime_err_dialog);
		

	}


	public ErrorsDialog getView()
	{
		return this;
	}

}
