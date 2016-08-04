package com.pine.lib.func.debug.window.key;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.pine.lib.R;


public class KeyDebugDialog extends AlertDialog
{

	


	public KeyDebugDialog(Context context, int theme)
	{
		super(context, theme);
	}


	public KeyDebugDialog(Context context)
	{
		super(context);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.debug_runtime_key_dialog);
		

	}


	public KeyDebugDialog getView()
	{
		return this;
	}

}
