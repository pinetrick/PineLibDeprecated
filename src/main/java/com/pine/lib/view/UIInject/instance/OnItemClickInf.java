package com.pine.lib.view.UIInject.instance;

import java.lang.reflect.Method;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.pine.lib.func.debug.G;
import com.pine.lib.view.debugerrbox.E;

public class OnItemClickInf implements OnItemClickListener
{
	private static G g = new G(OnItemClickInf.class);
	public Method method;
	public Object activity;



	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		try
		{
			method.invoke(activity, new Object[] { arg0, arg1, arg2, arg3 });
		}
		catch (Exception e)
		{
			E.w("onItemClick不存在或内部有异常，请查看LOG堆栈", e);

		}

	}
}
