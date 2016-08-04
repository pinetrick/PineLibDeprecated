package com.pine.lib.view.UIInject;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pine.lib.func.debug.G;
import com.pine.lib.view.UIInject.instance.OnClickInf;
import com.pine.lib.view.UIInject.instance.OnItemClickInf;
import com.pine.lib.view.UIInject.interfaces.InjectClick;
import com.pine.lib.view.UIInject.interfaces.InjectItemClick;
import com.pine.lib.view.UIInject.interfaces.InjectView;

/**
 * 界面注入类
 * 
 * <pre>
 * 
 * </pre>
 */
public class UiInject
{
	private static G g = new G(UiInject.class);



	/**
	 * 对Activity进行控件注入
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param activity
	 */
	public static void inject(Activity activity)
	{
		UiInject.inject(activity, ((ViewGroup) activity
				.findViewById(android.R.id.content)).getChildAt(0));

	}


	/**
	 * 对Dialog进行控件注入
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param activity
	 */
	public static void inject(Dialog dialog)
	{
		UiInject.inject(dialog, ((ViewGroup) dialog
				.findViewById(android.R.id.content)).getChildAt(0));

	}


	/**
	 * 对View进行控件注入
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param activity
	 */
	public static void inject(View view)
	{
		UiInject.inject(view, view);

	}


	// @InjectView(R.id.addToDictionary)
	public static void inject(Object activity, View baseView)
	{

		Field[] fields = activity.getClass().getDeclaredFields();
		// Annotation[] annotations;
		for (Field field : fields)
		{
			InjectView injectView = field.getAnnotation(InjectView.class);

			if (injectView != null)
			{
				View view;
				try
				{
					Method m = injectView.getClass().getDeclaredMethod("value",
							null);
					int id = (Integer) m.invoke(injectView, null);
					view = baseView.findViewById(id);

					// field.setAccessible(true);
					// m.set(field, view);

					field.setAccessible(true);
					field.set(activity, view);

					g.i("界面加载 - " + field.getName());

				}
				catch (Exception e)
				{
					g.e(field.toGenericString() + "赋值失败 - " + e.toString());
				}

			}

		}

		Method[] methods = activity.getClass().getDeclaredMethods();
		for (Method method : methods)
		{
			InjectClick injectClick = method.getAnnotation(InjectClick.class);
			if (injectClick != null)
			{
				View view;
				try
				{
					Method m = injectClick.getClass().getDeclaredMethod(
							"value", null);
					int id = (Integer) m.invoke(injectClick, null);
					view = baseView.findViewById(id);

					OnClickInf onClickInf = new OnClickInf();
					onClickInf.activity = activity;
					onClickInf.method = method;

					view.setOnClickListener(onClickInf);

					g.i("界面加载 - " + method.getName()
							+ " = setOnClickListener()");
				}
				catch (Exception e)
				{
					g.e(method.toGenericString() + "设置点击监听器失败 - "
							+ e.toString());
				}

			}
			InjectItemClick injectItemClick = method
					.getAnnotation(InjectItemClick.class);
			if (injectItemClick != null)
			{
				ListView listView;
				try
				{
					Method m = injectItemClick.getClass().getDeclaredMethod(
							"value", null);
					int id1 = (Integer) m.invoke(injectItemClick, null);
					listView = (ListView) baseView.findViewById(id1);

					OnItemClickInf onItemClickInf = new OnItemClickInf();
					onItemClickInf.activity = activity;
					onItemClickInf.method = method;

					listView.setOnItemClickListener(onItemClickInf);

					// listView.setBackgroundColor(Color.RED);

					g.i("界面加载 - " + method.getName()
							+ " = setOnItemClickListener()");

				}
				catch (Exception e)
				{
					g.e(method.toGenericString() + "设置点击监听器失败 - "
							+ e.toString());
				}

			}

		}
	}
}
