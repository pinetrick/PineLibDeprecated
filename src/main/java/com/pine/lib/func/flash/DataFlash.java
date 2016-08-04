package com.pine.lib.func.flash;

import java.util.ArrayList;
import java.util.List;

import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;

/**
 * 数据缓存
 * 
 * <pre>
 * 
 * </pre>
 */
public class DataFlash<T> 
{
	private static G g = new G(DataFlash.class);
	/**
	 * 缓存最大数量
	 */
	private static int max = 20;

	private List<FlashBean<T>> beans = new ArrayList<FlashBean<T>>();

	public DataFlash()
	{
		M.i().addClass(this);
	}
	
	public void onLowMem()
	{
		g.d("低内存，全部释放缓存");
		beans.clear();
	}


	public T get(String name)
	{
		for (FlashBean<T> flashBean : beans)
		{
			if (flashBean.getName().equals(name))
			{
				//g.d("缓存命中");
				flashBean.setUseTimes(flashBean.getUseTimes() + 1);
				return flashBean.getObject();
			}
		}
		return null;
	}


	/**
	 * 添加一个元素到缓存
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param name
	 * @param obj
	 * @return
	 */
	public Boolean add(String name, T obj)
	{
		for (FlashBean<T> flashBean : beans)
		{
			if (flashBean.getName().equals(name))
			{
				g.e("添加缓存失败 - 已存在");
				return false;
			}
		}
		FlashBean<T> minTimesBean = null;
		int minTimes = 9999;
		if (beans.size() > max)
		{
			for (FlashBean<T> flashBean : beans)
			{
				if (minTimes > flashBean.getUseTimes())
				{
					minTimes = flashBean.getUseTimes();
					minTimesBean = flashBean;
				}
			}
			minTimesBean = null;
			beans.remove(minTimesBean);
			g.d("清理缓存");
		}
		FlashBean<T> newBean = new FlashBean<T>();
		newBean.setName(name);
		newBean.setUseTimes(1);
		newBean.setObject(obj);

		beans.add(newBean);
		g.d("添加缓存成功");
		return true;
	}

}
