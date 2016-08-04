package com.pine.lib.view.debugerrbox;

import com.pine.lib.func.debug.G;
import com.pine.lib.view.fasttoast.T;
import com.pine.lib.windows.alter.MessageBox;

/**
 * 这个是调试的错误显示类，如果调试总开关打开将会显示，否则不显示
 * 
 * <pre>
 * 
 * </pre>
 */
public class E
{
	/**
	 * 
	 * <pre>
	 * 这个是调试的错误显示类，如果调试总开关打开将会显示，否则不显示
	 * 如果正常显示错误 返回true
	 * 以吐司形式出现
	 * </pre>
	 * 
	 * @param message
	 * @return
	 */
	public static Boolean w(String message)
	{
		return w(message, null);
	}


	/**
	 * 
	 * <pre>
	 * 这个是调试的错误显示类，如果调试总开关打开将会显示，否则不显示
	 * 如果正常显示错误 返回true
	 * 以调试框形式出现
	 * </pre>
	 * 
	 * @param message
	 * @return
	 */
	public static Boolean w(String message, Exception e)
	{
		if (G.getEnableGlobalDebug())
		{
			message += "(调试时显示)";
			T.t(message);
			if (e != null)
			{
				e.printStackTrace();
			}
			return true;
		}
		else
		{
			return false;
		}
	}


	/**
	 * 
	 * <pre>
	 * 这个是调试的错误显示类，如果调试总开关打开将会显示，否则不显示
	 * 如果正常显示错误 返回true
	 * 以调试框形式出现
	 * </pre>
	 * 
	 * @param message
	 * @return
	 */
	public static Boolean e(String message)
	{
		return e(message, null);
	}


	/**
	 * 
	 * <pre>
	 * 这个是调试的错误显示类，如果调试总开关打开将会显示，否则不显示
	 * 如果正常显示错误 返回true
	 * 以调试框形式出现
	 * </pre>
	 * 
	 * @param message
	 * @return
	 */
	public static Boolean e(String message, Exception e)
	{
		if (G.getEnableGlobalDebug())
		{
			message += "(调试时显示)";
			MessageBox.i().show(message);
			if (e != null)
			{
				e.printStackTrace();
			}
			return true;
		}
		else
		{
			return false;
		}
	}
}
