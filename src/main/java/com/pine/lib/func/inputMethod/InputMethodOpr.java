package com.pine.lib.func.inputMethod;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.pine.lib.func.debug.M;

public class InputMethodOpr
{

	private InputMethodOpr()
	{
		M.i().addClass(this);
	}


	public static InputMethodOpr i()
	{
		return new InputMethodOpr();
	}


	/**
	 * 打开输入法窗口: View v 传入一个根布局 android:windowSoftInputMode="stateVisible" 不可使用
	 * 
	 * @return
	 */
	public InputMethodOpr open(View v)
	{
		v.setFocusable(true);
		v.requestFocus();
		onFocusChange(v, true);
		return this;
	}


	/**
	 * 关闭输入法窗口: View v 传入一个根布局
	 * 
	 * @return
	 */
	public InputMethodOpr close(View v)
	{
		v.setFocusable(true);
		v.requestFocus();
		onFocusChange(v, false);
		return this;
	}


	/**
	 * 切换状态:打开则关闭 关闭则打开
	 * 
	 * @return
	 */
	public InputMethodOpr change(View v)
	{

		v.setFocusable(true);
		v.requestFocus();
		onFocusChange(v, v.isFocused());
		return this;
	}


	/**
	 * 如果给定EditText 有输入法打开 isOpen返回true
	 * 
	 * @return
	 */
	public Boolean isOpen(View view)
	{
		return view.isFocused();
		// return inputMethodManager.isActive();
	}


	private void onFocusChange(final View titleInput, boolean hasFocus)
	{
		final boolean isFocus = hasFocus;
		(new Handler()).postDelayed(new Runnable() {
			public void run()
			{
				InputMethodManager imm = (InputMethodManager) titleInput
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				if (isFocus)
				{
					imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				}
				else
				{
					imm.hideSoftInputFromWindow(titleInput.getWindowToken(), 0);
				}
			}
		}, 100);
	}
}
