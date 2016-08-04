package com.pine.lib.view.widget.icon;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;

import com.pine.lib.base.activity.A;

public class AddToMyDesktop
{
	/**
	 * 快速创建桌面图标
	 * 
	 * <pre>
	 * AddToMyDesktop.addShortcutToDesktop(&quot;杀进程&quot;, R.drawable.warning,
	 * 		KillProcessActivity.class);
	 * </pre>
	 * 
	 * @param iconName  图标名称
	 * @param iconRes  图标icon
	 * @param startActivity 图标点击后打开的Activity
	 */
	public static void addShortcutToDesktop(String iconName, int iconRes,
			Class<? extends Activity> startActivity)
	{

		Intent intent = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, iconName);
		// 是否可以有多个快捷方式的副本，参数如果是true就可以生成多个快捷方式，如果是false就不会重复添加
		intent.putExtra("duplicate", false);

		Intent intent2 = new Intent(Intent.ACTION_MAIN);
		intent2.addCategory(Intent.CATEGORY_LAUNCHER);
		// 删除的应用程序的ComponentName，即应用程序包名+activity的名字
		intent2.setComponent(new ComponentName(A.c().getPackageName(),
				startActivity.getName()));

		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent2);
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
				Intent.ShortcutIconResource.fromContext(A.c(), iconRes));
		A.c().sendBroadcast(intent);

	}
}