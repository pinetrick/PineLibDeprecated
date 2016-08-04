package com.pine.lib.math.pckhash;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import com.pine.lib.base.activity.A;
import com.pine.lib.func.debug.G;

public class GetMyPkgHashCode
{
	private static G g = new G(GetMyPkgHashCode.class);



	public static int getHashCode()
	{
		try
		{
			final String packname = A.c().getPackageName();
			PackageInfo packageInfo = A.c().getPackageManager()
					.getPackageInfo(packname, PackageManager.GET_SIGNATURES);
			Signature[] signs = packageInfo.signatures;
			Signature sign = signs[0];
			boolean checkright = false;
			int code = sign.hashCode();
			return code;
		}
		catch (Exception e)
		{
			return 0;
		}

	}


	/**
	 * 如果应用哈希值为发布版本的，则关闭调试功能
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param hashCode
	 *            发布版本的哈希值
	 * @return 如果为发布版本 返回 TRUE
	 */
	public static Boolean isRight(int hashCode)
	{
		int myHash = getHashCode();
		if (hashCode == myHash)
		{
			g.e("紧急错误：调试模式被强制关闭，这个应该是发布版本！");
			G.setEnableGlobalDebug(false);

			return true;

		}
		else if (!G.getEnableGlobalDebug())
		{
			return true;
		}
		else
		{
			g.w("当前版本的哈希值:" + myHash);
			return false;
		}
	}

}
