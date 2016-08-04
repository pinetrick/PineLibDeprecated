package com.pine.lib.view.UIInject.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.view.View;
import android.widget.AdapterView;

import com.pine.lib.windows.alter.MessageBox;

/**
 * 
 * <pre>
 * public void onItemClick(AdapterView&lt;?&gt; parent, View view, int position, long id)
 * {
 * 
 * }
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface InjectItemClick
{
	int value();
}
