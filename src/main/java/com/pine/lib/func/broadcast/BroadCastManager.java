package com.pine.lib.func.broadcast;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.pine.lib.base.activity.A;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;

/**
 * 应用内部轻量级广播 调用方法
 * 
 * <pre>
 * BroadCastManager.i().reg(thisNumber, &quot;接受何类型广播&quot;, this);
 * </pre>
 */
public class BroadCastManager
{
	private static G g = new G(BroadCastManager.class);
	public static BroadCastManager appBroadCase;
	public static Object locker = new Object();
	public List<BroadCastBean> broad = new ArrayList<BroadCastBean>();



	private BroadCastManager()
	{
		M.i().addClass(this);
	}


	public Boolean remove(int context)
	{
		List<BroadCastBean> willRemoveBeans = new ArrayList<BroadCastBean>();
		for (BroadCastBean broadCaseBean : broad)
		{
			if (broadCaseBean.getContext() == context)
			{
				willRemoveBeans.add(broadCaseBean);
			}
		}
		for (BroadCastBean broadCastBean : willRemoveBeans)
		{
			g.i("移出广播  - " + broadCastBean.getTitle() + ","
					+ broadCastBean.getClassName());
			broad.remove(broadCastBean);
		}

		return false;
	}


	public Boolean remove(BroadCastRecImp broadCaseRecImp)
	{
		List<BroadCastBean> willRemoveBeans = new ArrayList<BroadCastBean>();
		for (BroadCastBean broadCaseBean : broad)
		{
			if (broadCaseBean.getCall() == broadCaseRecImp)
			{
				willRemoveBeans.add(broadCaseBean);
			}
		}
		for (BroadCastBean broadCastBean : willRemoveBeans)
		{
			g.i("移出广播  - " + broadCastBean.getTitle() + ","
					+ broadCastBean.getClassName());
			broad.remove(broadCastBean);
		}

		return false;
	}


	/**
	 * 注册一个广播接收器
	 * 
	 * <pre>
	 * 如果定义了Class 默认申请单例广播模式 
	 * 当前类 无论有多少个实例 只能存在一个广播接收器(最后注册的那个)
	 * </pre>
	 * 
	 * @param title
	 * @return
	 */
	public BroadCastManager reg(String title, Class<?> c,
			BroadCastRecImp broadCaseRecImp)
	{
		return reg(BroadCastNumber.getNumber(), title, c, broadCaseRecImp);
	}


	/**
	 * 注册一个广播接收器
	 * 
	 * <pre>
	 * 如果定义了Class 默认申请单例广播模式 
	 * 当前类 无论有多少个实例 只能存在一个广播接收器(最后注册的那个)
	 * </pre>
	 * 
	 * @param title
	 * @return
	 */
	public BroadCastManager reg(String title, BroadCastRecImp broadCaseRecImp)
	{
		return reg(BroadCastNumber.getNumber(), title, null, broadCaseRecImp);
	}


	/**
	 * 注册一个广播接收器
	 * 
	 * <pre>
	 * 如果定义了Class 默认申请单例广播模式 
	 * 当前类 无论有多少个实例 只能存在一个广播接收器(最后注册的那个)
	 * </pre>
	 * 
	 * @param context
	 * @param title
	 * @return
	 */
	public BroadCastManager reg(int context, String title, Class<?> c,
			BroadCastRecImp broadCaseRecImp)
	{
		if ((title == null) || (broadCaseRecImp == null))
		{
			g.e("注册广播器异常 - 不接受空指针");
			return this;
		}

		for (BroadCastBean broadCastBean : broad)
		{
			if (broadCastBean.getCall() == broadCaseRecImp)
			{
				if (broadCastBean.getTitle().equals(title))
				{
					g.w("广播" + title + "注册失败 - 因为已存在广播");
					return this;
				}
			}
		}

		String classNameString = "未知类";
		if (c != null)
		{
			classNameString = c.getName();
			for (BroadCastBean broadCastBean : broad)
			{
				if (broadCastBean.getClassName().equals(classNameString))
				{
					if (broadCastBean.getTitle().equals(title))
					{
						broad.remove(broadCastBean);
						g.d("广播" + title + "注销成功 - 您申请的是单例广播模式");
						break;
					}
				}
			}
		}

		broad.add(new BroadCastBean(context, title, broadCaseRecImp,
				classNameString));
		g.i("注册了 " + title + " 广播id=" + context + " - 成功 - 类:"
				+ classNameString);
		return this;
	}


	/**
	 * 发送一个广播
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param title
	 *            广播的标题
	 * @param obj
	 *            广播携带的内容
	 * @return 一共发送了多少个广播
	 */

	public int send(String title, Object obj)
	{
		if (title == null)
		{
			g.e("发送广播异常 - 不接受空指针");
			return 0;
		}
		int r = 0;
		for (BroadCastBean broadCaseBean : broad)
		{
			if (broadCaseBean.getTitle().equals(title))
			{
				new BroadCastThread(broadCaseBean, title, obj).start();// 将广播映射到主线程并发送。
				g.d("广播: " + title + " - 接受者：" + broadCaseBean.getClassName());
				r++;
			}
		}
		g.i("发送了 " + title + " 广播, 接受者数量：" + r);
		return r;
	}


	/**
	 * 发送一个广播
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param title
	 *            广播的标题
	 * @return
	 */
	public int send(String title)
	{
		return send(title, null);
	}


	/**
	 * 严谨单例模式
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public static BroadCastManager i()
	{
		if (appBroadCase == null)
		{
			synchronized (locker)
			{
				if (appBroadCase == null)
				{
					appBroadCase = new BroadCastManager();
					g.i("应用内部广播器 - 启动");
				}
			}
		}

		return appBroadCase;
	}


	/**
	 * 检测广播接收器是否被初始化
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public static Boolean isI()
	{
		if (appBroadCase == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
