package com.pine.lib.view.UIInject.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.view.View;

import com.pine.lib.windows.alter.MessageBox;

/**
 * 
 * <pre>
 * public void onClick(View v)
 * {
 * 
 * }
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface InjectClick
{
	int value();
}
