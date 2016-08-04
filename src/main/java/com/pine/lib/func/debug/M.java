package com.pine.lib.func.debug;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.R.integer;

/**
 * 有多少类在内存中加载
 * 
 * <pre>
 * 内存监控类 - debugRuntime
 * </pre>
 */
public class M
{
	private static M m = new M();
	private static List<WeakReference<Object>> ref = new ArrayList<WeakReference<Object>>();



	public Boolean addClass(Object classInstance)
	{
		if (G.getEnableGlobalDebug())
		{
			for (WeakReference<Object> reference : ref)
			{
				if (reference.get() != null)
				{
					if (reference.get() == classInstance)
					{
						return false;
					}
				}

			}

			ref.add(new WeakReference<Object>(classInstance));
		}
		return true;
	}


	public String toString()
	{
		if (G.getEnableGlobalDebug())
		{
			for (int i = 0; i < ref.size(); i++)
			{
				WeakReference<Object> ele = ref.get(i);
				if (ele.get() == null)
				{
					ref.remove(i);
					i--;
				}
			}

			StringBuilder sLib = new StringBuilder();
			StringBuilder sApp = new StringBuilder();
			for (int i = 0; i < ref.size(); i++)
			{
				WeakReference<Object> ele = ref.get(i);
				String nameString = ele.get().getClass().getName();
				if (nameString.startsWith("com.pine.lib"))
				{
					sLib.append(ele.get().getClass().getName() + "\n");
				}
				else
				{
					sApp.append(ele.get().getClass().getName() + "\n");
				}

			}
			return sApp.toString() + "\n" + sLib.toString();
		}
		else
		{
			return "";
		}

	}


	private M()
	{

	}


	public static M i()
	{

		return m;
	}
}
