package com.pine.lib.math.Vector;

import com.pine.lib.func.debug.G;

public class Vector3<T> {
	private static G g = new G(Vector3.class);
	public T x;
	public T y;
	public T z;
	
	public Vector3(T x, T y, T z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3()
	{
		x = (T)(new Object());
		y = (T)(new Object());
		z = (T)(new Object());
	}
	
	public String toString()
	{
		return "(" + String.valueOf(x) + ", " +String.valueOf(y) + ", " + String.valueOf(z) + ")";
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
		String zz = String.valueOf(z);
		
		int index = xx.indexOf(".");
		if (index + i < xx.length()) xx = xx.substring(0, index + i);
		
		index = yy.indexOf(".");
		if (index + i < yy.length()) yy = yy.substring(0, index + i);
	
		index = zz.indexOf(".");
		if (index + i < zz.length()) zz = zz.substring(0, index + i);
		
		return "(" + xx+ ", " +yy + ", " + zz + ")";
	}
	
}
