package com.pine.lib.base.activity;

import com.baidu.mobstat.StatService;

public class BaiduTongjiResumeCall
{
	public static void resume()
	{
		StatService.onResume(A.a());
	}


	public static void pause()
	{
		StatService.onPause(A.a());
	}
}
