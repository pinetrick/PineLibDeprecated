package com.pine.lib.base.appinfo;

import java.util.List;

import com.pine.lib.func.debug.M;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

public class AppInfo {
	private Context c;

	public AppInfo(Context context) {
		this.c = context;
		M.i().addClass(this);
	}

	/**
	 * 获取APP包名
	 * 确认使用
	 * @return
	 */
	public String getPackageName() {
		return this.c.getPackageName();
	}

	/**
	 * 获取App版本号  
	 * 确认使用
	 * 
	 * @param context
	 * @return
	 */
	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
		}
		return verCode;
	}

	/**
	 * 获取App版本名
	 * 确认使用
	 * @param context
	 * @return
	 */
	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
		}
		return verName;
	}

	/**
	 * 获取APP名字
	 * 确认使用
	 * @param context
	 * @return
	 */
	public static String getAppName(Context context) {
		String verName = context
				.getResources()
				.getText(
						context.getResources().getIdentifier("app_name",
								"string", context.getPackageName())).toString();
		return verName;
	}

	/**
	 * 获取APP的Icon
	 * 确认使用
	 * @param context
	 * @return
	 */
	public static Drawable getAppIcon(Context context) {
		Drawable icon = null;
		try {
			icon = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).applicationInfo
					.loadIcon(context.getPackageManager());
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
		}
		return icon;
	}

//	 public static long getFirstInstallTime(Context context)
//	 {
//	 long fit = 0L;
//	 try {
//	 fit = context.getPackageManager().getPackageInfo(
//	 context.getPackageName(), 0).firstInstallTime;
//	 }
//	 catch (PackageManager.NameNotFoundException localNameNotFoundException) {
//	 }
//	 return fit;
//	 }
	 
	
	//
	// public static long getLastUpdateTime(Context context)
	// {
	// long fit = 0L;
	// try {
	// fit = context.getPackageManager().getPackageInfo(
	// context.getPackageName(), 0).lastUpdateTime;
	// }
	// catch (PackageManager.NameNotFoundException localNameNotFoundException) {
	// }
	// return fit;
	// }

	// public ArrayList<MyPermission> getUsesPermission(String packageName)
	// {
	// ArrayList myPerms = new ArrayList();
	// try {
	// PackageManager packageManager = this.c.getPackageManager();
	// PackageInfo packageInfo = packageManager.getPackageInfo(
	// packageName, 4096);
	// String[] usesPermissionsArray = packageInfo.requestedPermissions;
	//
	// for (int i = 0; i < usesPermissionsArray.length; ++i) {
	// MyPermission permi = new MyPermission();
	//
	// String usesPermissionName = usesPermissionsArray[i];
	// permi.setPermissionName(usesPermissionName);
	// System.out.println("usesPermissionName=" + usesPermissionName);
	//
	// PermissionInfo permissionInfo = packageManager
	// .getPermissionInfo(usesPermissionName, 0);
	//
	// PermissionGroupInfo permissionGroupInfo = packageManager
	// .getPermissionGroupInfo(permissionInfo.group, 0);
	// permi.setPermissionGroup(permissionGroupInfo.loadLabel(
	// packageManager).toString());
	// System.out.println("permissionGroup=" +
	// permissionGroupInfo.loadLabel(packageManager)
	// .toString());
	//
	// String permissionLabel = permissionInfo.loadLabel(
	// packageManager).toString();
	// permi.setPermissionLabel(permissionLabel);
	// System.out.println("permissionLabel=" + permissionLabel);
	//
	// String permissionDescription = permissionInfo.loadDescription(
	// packageManager).toString();
	// permi.setPermissionDescription(permissionDescription);
	// System.out.println("permissionDescription=" +
	// permissionDescription);
	// System.out
	// .println("===========================================");
	// myPerms.add(permi);
	// }
	//
	// return myPerms;
	// } catch (Exception localException) {
	// }
	// return myPerms;
	// }
	/**
	 * 获取手机上所有第三方应用:格式(微信,QQ)
	 * 确认可用
	 * @return
	 */
	public String getAllThirdApp() {
		String result = "";
		List<PackageInfo> packages = this.c.getPackageManager()
				.getInstalledPackages(0);
		for (PackageInfo i : packages) {
			if ((i.applicationInfo.flags & 0x1) == 0) {
				result = result
						+ i.applicationInfo.loadLabel(
								this.c.getPackageManager()).toString() + ",";
			}
		}
		return result.substring(0, result.length() - 1);
	}
}