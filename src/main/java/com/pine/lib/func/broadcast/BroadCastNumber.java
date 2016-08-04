package com.pine.lib.func.broadcast;

import com.pine.lib.func.debug.M;

public class BroadCastNumber
{
	private static int number;



	public static int getNumber()
	{
		return number;
	}


	public static void setNumber(int number)
	{
		BroadCastNumber.number = number;
	}


	public BroadCastNumber()
	{
		M.i().addClass(this);
	}

}
