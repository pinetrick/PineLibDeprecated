package com.pine.lib.func.debug.window.key;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pine.lib.R;
import com.pine.lib.base.activity.A;

public class Adapter extends BaseAdapter
{

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Object getItem(int arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public long getItemId(int arg0)
	{
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2)
	{
		TextView textView = new TextView(A.c());
		textView.setText("");
		textView.setBackgroundColor(Color.rgb(255, 255, 0));
		textView.setMinimumHeight((int) (A.c().getResources()
				.getDimension(R.dimen.h10b)));
		textView.setTextColor(Color.rgb(0, 0, 0));
		return textView;
	}
}
