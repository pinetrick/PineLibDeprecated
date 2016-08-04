package com.pine.lib.func.intentcall;

import android.content.ActivityNotFoundException;
import android.content.Intent;

import com.pine.lib.R;
import com.pine.lib.base.activity.A;
import com.pine.lib.view.debugerrbox.E;

/**
 * 快速intent I.i(Main.class, R.anim.in_from_up, R.anim.out_to_down);
 * 
 * <pre>
 * I.i(sss.class);
 * </pre>
 */
public class I
{

	/**
	 * 快速启动Activity方法
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param class1
	 */
	public static void i(int in, int out)
	{
		A.a().overridePendingTransition(in, out);
	}
	

	/**
	 * 快速启动Activity方法
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param class1
	 */
	public static void i(Class<?> class1)
	{
		try
		{

			Intent intent = new Intent();
			intent.setClass(A.a(), class1);
			A.a().startActivity(intent);

		}
		catch (ActivityNotFoundException e)
		{
			E.e("异常：请在Manifest中定义" + class1.toString() + "类", e);
		}
	}
}
