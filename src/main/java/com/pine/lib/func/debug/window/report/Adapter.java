package com.pine.lib.func.debug.window.report;

import java.util.List;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pine.lib.R;
import com.pine.lib.base.activity.A;
import com.pine.lib.func.debug.window.CrashBean;
import com.pine.lib.storage.sqlite.SqliteManager;

public class Adapter extends BaseAdapter
{
	private List<CrashBean> errorBeans = (List) SqliteManager.i().select(
			"select * from `CrashBean` order by `id` desc", CrashBean.class);



	public List<CrashBean> getErrorBeans()
	{
		return errorBeans;
	}


	@Override
	public int getCount()
	{
		errorBeans = (List) SqliteManager.i()
				.select("select * from `CrashBean` order by `id` desc",
						CrashBean.class);
		return errorBeans.size();
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

		CrashBean cBean = errorBeans.get(arg0);

		TextView textView = new TextView(A.c());
		textView.setText(cBean.getTim() + "\n" + cBean.getType());
		textView.setBackgroundColor(Color.rgb(255, 255, 0));
		textView.setTextColor(Color.rgb(0, 0, 0));
		textView.setMinimumHeight((int) (A.c().getResources()
				.getDimension(R.dimen.h10b)));
//		textView.setTextSize((float) (A.c().getResources()
//				.getDimension(R.dimen.f10k)));
		return textView;
	}
}
