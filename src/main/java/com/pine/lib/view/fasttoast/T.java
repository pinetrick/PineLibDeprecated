package com.pine.lib.view.fasttoast;

import android.widget.Toast;

import com.pine.lib.base.activity.A;
import com.pine.lib.func.debug.G;

/**
 * 快速吐司辅助类
 * 
 * <pre>
 * 
 * </pre>
 */
public class T
{
	private static G g = new G(T.class);



	/**
	 * 调用吐司函数
	 */
	public static void t(String s)
	{
		t(s, Toast.LENGTH_SHORT);

	}


	/**
	 * 调用吐司函数
	 */
	public static void t(Boolean s)
	{
		t(String.valueOf(s));
	}


	/**
	 * 调用吐司函数
	 */
	public static void t(int s)
	{
		t(String.valueOf(s));
	}


	/**
	 * 调用吐司函数
	 */
	public static void t(int stringRes, int duration)
	{
		t(String.valueOf(stringRes), duration);
	}


	/**
	 * 调用吐司函数
	 */
	public static void t(String stringRes, int duration)
	{
		try
		{
			Toast.makeText(A.a(), stringRes, duration).show();
		}
		catch (Exception e)
		{
			g.e("====================上下文失效！====================");
			e.printStackTrace();
			g.p("Toast：上下文失效");
			try
			{
				Toast.makeText(A.c(), stringRes, duration).show();
			}
			catch (Exception e2)
			{
				g.e("====================上下文失效2！====================");
				e.printStackTrace();
				g.p("Toast：上下文失效2");
			}
		}

	}

}
