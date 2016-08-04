package com.pine.lib.base.activity;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.baidu.mobstat.StatService;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;
import com.pine.lib.math.pckhash.GetMyPkgHashCode;

/**
 * this class must be extend by ur app
 * 
 * @author Administrator
 * 
 */
public abstract class ConfigApplication extends Application
{
	private static G g = new G(ConfigApplication.class);



	@Override
	public void onCreate()
	{

		A.s(this);
		M.i().addClass(this);

		G.setEnableGlobalDebug(isDebug());
		if (!GetMyPkgHashCode.isRight(getSign()))
		{
			loadingDebug();

			StatService.setDebugOn(true);
			StatService.setSessionTimeOut(1);
		}

		if (EnableBaiduTongji())
		{
			StatService.setAppKey(getBaiduAppKey());
			if (getQuDaoName() != null)
			{
				StatService.setAppChannel(this, getQuDaoName(), true);
			}
			StatService.setOn(this, StatService.EXCEPTION_LOG);

		}

		super.onCreate();
	}


	/**
	 * 渠道名称
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public String getQuDaoName()
	{
		String 渠道名称 = "Test";
		try
		{
			ApplicationInfo appInfo = this.getPackageManager()
					.getApplicationInfo(getPackageName(),
							PackageManager.GET_META_DATA);
			渠道名称 = appInfo.metaData.getString("QuDao");

		}
		catch (Exception e)
		{

		}
		g.i("渠道名称：" + 渠道名称);
		return 渠道名称;
	}


	public abstract Boolean isDebug();


	/**
	 * it ll be run if is debug mode
	 */
	public abstract void loadingDebug();


	/**
	 * 允许使用百度统计？
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public abstract Boolean EnableBaiduTongji();


	/**
	 * 百度统计APPkey
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public abstract String getBaiduAppKey();


	/**
	 * 百度统计Bug提交开关
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public abstract Boolean enableReportBug();


	/**
	 * keep screen on when debug run
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public abstract Boolean KeepScreenOnWhenDbg();


	/**
	 * it is sign of ur app
	 * 
	 * @return
	 */
	public abstract int getSign();

}
