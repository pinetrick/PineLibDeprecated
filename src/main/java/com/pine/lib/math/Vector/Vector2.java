package com.pine.lib.math.Vector;

import com.pine.lib.func.debug.G;

public class Vector2<T> {
	private static G g = new G(Vector2.class);
	public T x;
	public T y;
	
	public Vector2(T x, T y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2()
	{
		x = (T)(new Object());
		y = (T)(new Object());
	}
	
	public String toString()
	{
		return "(" + String.valueOf(x) + ", " +String.valueOf(y) + ")";
	}

	public String toString(int i)
	{
		i = i + 1;
		
		if (i < 1)
		{
			g.e("错误的输入");
			return "";
		}
		String xx = String.valueOf(x);
		String yy = String.valueOf(y);
		
		int index = xx.indexOf(".");
		if (index + i < xx.length()) xx = xx.substring(0, index + i);
		
		index = yy.indexOf(".");
		if (index + i < yy.length()) yy = yy.substring(0, index + i);

		
		return "(" + xx+ ", " +yy + ")";
	}
	
}
