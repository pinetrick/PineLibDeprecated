package com.pine.lib.miui.flywindows;

import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Build;

import com.pine.lib.base.activity.A;
import com.pine.lib.func.debug.G;

public class MIUIFlyWindows {
	private static G g = new G(MIUIFlyWindows.class);

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static Boolean isMiuiFloatWindowOpAllowed() {
		final int version = Build.VERSION.SDK_INT;
		Boolean 悬浮窗开启 = false;
		if (version >= 19) {
			悬浮窗开启 = checkOp(A.c(), 24); // 自己写就是24
			// 为什么是24?看AppOpsManager
		} else {
			g.i("MIUI");
			if ((A.c().getApplicationInfo().flags & 1 << 27) == 1) {
				悬浮窗开启 = true;

			} else {
				悬浮窗开启 = false;
			}
		}

		if (悬浮窗开启) {
			g.i("MIUI悬浮窗正常显示");
		} else {
			g.w("MIUI悬浮窗被阻止");
		}

		return 悬浮窗开启;

	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static boolean checkOp(Context context, int op) {
		final int version = Build.VERSION.SDK_INT;

		if (version >= 19) {
			AppOpsManager manager = (AppOpsManager) context
					.getSystemService(Context.APP_OPS_SERVICE);

			try {

				Method md = AppOpsManager.class.getMethod("checkOp",
						int.class, int.class, String.class);
				int r = (Integer) md.invoke(manager, op,
						Binder.getCallingUid(), context.getPackageName());

				if (AppOpsManager.MODE_ALLOWED == r) { // 这儿反射就自己写吧
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				g.e(e.toString());
			}
		} else {
			g.e("Below API 19 cannot invoke!");
		}
		return false;
	}

}
