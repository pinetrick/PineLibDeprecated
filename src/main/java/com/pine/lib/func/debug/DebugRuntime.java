package com.pine.lib.func.debug;

import android.content.Context;

import com.pine.lib.func.myTimer.MyTimer;
import com.pine.lib.func.myTimer.onTimerListener;
import com.pine.lib.hardware.info.Cpu.CpuInfo;

/**
 * <br> 这个类用于后台服务
 * <br> 进行数据收集 以及信息统计
 * <br> 当CPU占用过高时会通过LOG报警
 * <br> 功能总开关受限于全局debug开关
 * <br> 使用方法:DebugRuntime.i(this);
 */
public class DebugRuntime {
	private static G g = new G(DebugRuntime.class);
	private static DebugRuntime debugRuntime = null;
	
	private Context context = null;
	
	private MyTimer memAlert;
	
	private DebugRuntime(Context context)
	{
		
		this.context = context;
		
		//如果全局DEBUG模式关闭 则不开启本功能
		if (!G.getEnableGlobalDebug()) return;
		
		setMemAlert();
		memAlert.start();
		M.i().addClass(this);
		
	}

	/**
	 * 通过这个函数构造实例或者初始化
	 * @param context 上下文 
	 * @return
	 */
	public static DebugRuntime i(Context context)
	{
		if (debugRuntime == null)
		{
			debugRuntime = new DebugRuntime(context);
		}
		return debugRuntime;
	}
	
	//=========================  用于CPU占用模块  ===================
	/**
	 * 从系统启动结束到现在 系统用了多少CPU
	 */
	private long startCpuUsed = 0;
	/**
	 * 到现在为止 本应用 使用了多少CPU
	 */
	private long nowAppCpuUsed = 0;
	/**
	 * 到现在为止 总共CPU利用时间是多少
	 */
	private long nowAllCpuUsed = 0;
	/**
	 * 平均CPU利用率
	 */
	private float averageTime = 0;
	/**
	 * 1秒内消耗的CPU周期
	 */
	private long cpuPreSec = 0;
	
	private void setMemAlert()
	{
		startCpuUsed = CpuInfo.getTotalCpuTime();
		memAlert = new MyTimer();
		memAlert.setInterval(1000);
		memAlert.setOnTimerListener(new onTimerListener() {
			
//			@Override
//			public void onTimerInThead() {
//				long nowAppCpuUsed1 = CpuInfo.getAppCpuTime();
//				long nowAllCpuUsed1 = CpuInfo.getTotalCpuTime();
//				averageTime = nowAppCpuUsed1 * 100 / (nowAllCpuUsed1 - startCpuUsed);
//				cpuPreSec = nowAppCpuUsed1 - nowAppCpuUsed;
//				
//				g.d("1秒内CPU消耗:" + cpuPreSec);
//				g.d("平均CPU消耗:" + averageTime);
//				
//				if (cpuPreSec >= 60)
//				{
//					g.e("超高CPU利用率:" + cpuPreSec + "/秒");
//				}
//				else if (cpuPreSec >= 40)
//				{
//					g.w("高CPU利用率:" + cpuPreSec + "/秒");
//				}
//				nowAppCpuUsed = nowAppCpuUsed1;
//				nowAllCpuUsed = nowAllCpuUsed1;
//				
//			}
			
			@Override
			public void onTimer() {

				
			}
		});
	}
}
