package com.pine.lib.base.config;

import com.pine.lib.base.activity.A;

import android.content.Context;

/**
 * 图片缓存的相关配置
 * 
 * <pre>
 * 
 * </pre>
 */
public class PicConfig
{
	private static String PictureSavingDir = "/picture";

	/**
	 * 获取图片缓存目录
	 * 
	 * <pre>
	 * eg:/data/data/com.hslh.aa/picture
	 * </pre>
	 * 
	 * @param context
	 * @return
	 */
	public static String getPictureSavingDir()
	{
		return getPictureSavingDir(A.c());
	} 

	/**
	 * 获取图片缓存目录
	 * 
	 * <pre>
	 * eg:/data/data/com.hslh.aa/cache/picture
	 * </pre>
	 * 
	 * @param context
	 * @return
	 */
	public static String getPictureSavingDir(Context context)
	{
		//String pkName = context.getPackageName();
		String pkName = context.getCacheDir() + PictureSavingDir;
		return pkName;
	}


	/**
	 * 设置图片缓存目录
	 * 
	 * <pre>
	 * eg:"/picture"
	 * </pre>
	 * 
	 * @param pictureSavingDir
	 */
	public static void setPictureSavingDir(String pictureSavingDir)
	{
		PictureSavingDir = pictureSavingDir;
	}

}
