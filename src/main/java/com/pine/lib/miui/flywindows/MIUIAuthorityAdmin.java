package com.pine.lib.miui.flywindows;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.pine.lib.func.debug.G;

public class MIUIAuthorityAdmin {
	private static G g = new G(MIUIAuthorityAdmin.class);
	

	/**
	 * 打开MIUI权限管理界面(MIUI v5, v6)
	 * 输入必须为Activity
	 * 
	 * @param context
	 */
	public static void openMiuiPermissionActivity(Activity context) {
		Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");


		if (MIUISystemVersion.getSystemVersion() == 5) {
			PackageInfo pInfo = null;
			try {
				pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
						0);
			} catch (NameNotFoundException e) {
				g.e(e.toString());
			}
			intent.setClassName("SETTINGS_PACKAGE_NAME",
					"com.miui.securitycenter.permission.AppPermissionsEditor");
			intent.putExtra("extra_package_uid", pInfo.applicationInfo.uid);

		} else if (MIUISystemVersion.getSystemVersion() == 6) {
			intent.setClassName("com.miui.securitycenter",
					"com.miui.permcenter.permissions.AppPermissionsEditorActivity");
			intent.putExtra("extra_pkgname", context.getPackageName());
		}

		if (isIntentAvailable(context, intent)) {
			if (context instanceof Activity) {
				Activity a = (Activity) context;
				a.startActivityForResult(intent, 2);
			}
		} else {
			g.e("Intent is not available!");
		}
	}

	@TargetApi(9)
	public static void openAppDetailActivity(Context context, String packageName) {
		Intent intent = null;
		if (Build.VERSION.SDK_INT >= 9) {
			intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
			Uri uri = Uri.fromParts("SCHEME_PACKAGE", packageName, null);
			intent.setData(uri);
		} else {
			final String className = Build.VERSION.SDK_INT == 8 ? "SETTINGS_APPDETAILS_CLASS_NAME_22"
					: "SETTINGS_APPDETAILS_CLASS_NAME_B21";
			intent = new Intent(Intent.ACTION_VIEW);
			intent.setClassName("SETTINGS_PACKAGE_NAME",
					"SETTINGS_APPDETAILS_CLASS_NAME");
			intent.putExtra(className, packageName);
		}
		if (isIntentAvailable(context, intent)) {
			context.startActivity(intent);
		} else {
			g.e("intent is not available!");
		}
	}

	/**
	 * 判断是否有可以接受的Activity
	 * 
	 * @param context
	 * @param action
	 * @return
	 */
	public static boolean isIntentAvailable(Context context, Intent intent) {
		if (intent == null)
			return false;
		return context.getPackageManager()
				.queryIntentActivities(intent, PackageManager.GET_ACTIVITIES)
				.size() > 0;
	}
}
