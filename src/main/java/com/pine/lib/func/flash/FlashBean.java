package com.pine.lib.func.flash;

public class FlashBean<T>
{
	/**
	 * 缓存被命中次数
	 */
	private int useTimes = 0;
	/**
	 * 缓存的名字
	 */
	private String name = "";
	/**
	 * 缓存的Object
	 */
	private T object;



	public int getUseTimes()
	{
		return useTimes;
	}


	public void setUseTimes(int useTimes)
	{
		this.useTimes = useTimes;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public T getObject()
	{
		return object;
	}


	public void setObject(T object)
	{
		this.object = object;
	}


	@Override
	public String toString()
	{
		return "FlashBean [useTimes=" + useTimes + ", name=" + name + ", object=" + object + "]";
	}

}
