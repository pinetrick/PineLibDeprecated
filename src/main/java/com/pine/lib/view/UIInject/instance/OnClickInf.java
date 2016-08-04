package com.pine.lib.view.UIInject.instance;

import java.lang.reflect.Method;

import android.view.View;
import android.view.View.OnClickListener;

import com.pine.lib.func.debug.G;
import com.pine.lib.view.debugerrbox.E;

public class OnClickInf implements OnClickListener
{
	private static G g = new G(OnClickInf.class);
	public Method method;
	public Object activity;



	@Override
	public void onClick(View v)
	{
		try
		{
			method.invoke(activity, v);
		}
		catch (Exception e)
		{
			E.w("OnClick不存在或内部有异常，请查看LOG堆栈", e);

		}

	}

}
