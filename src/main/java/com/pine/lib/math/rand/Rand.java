package com.pine.lib.math.rand;

import java.util.Random;

public class Rand
{
	private static Rand rand = null;
	private Random ran = null;
	
	public Rand()
	{
		ran =new Random(System.currentTimeMillis()); 
	}

	public int getRand(int min, int max)
	{
		int r = min + (int)(ran.nextInt(max - min + 1));  
		return r;
	}
	
	public static Rand i()
	{
		if (rand == null)
		{
			rand = new Rand();
			
		}
		return rand;
	}
}
