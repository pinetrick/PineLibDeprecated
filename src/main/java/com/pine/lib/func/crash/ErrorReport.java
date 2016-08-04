package com.pine.lib.func.crash;

import android.os.Bundle;

import com.pine.lib.R;
import com.pine.lib.base.activity.PineFragmentActivity;

public class ErrorReport extends PineFragmentActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setContentView(R.layout.err_report_windows);
		super.onCreate(savedInstanceState);
	}

}
