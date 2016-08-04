package com.pine.lib.net.pic;

import android.R.integer;
import android.content.Context;
import android.widget.ImageView;

/**
 * 请通过i(context)构造
 */
public interface SetPicInterface
{

	/**
	 * 设置图片
	 */
	public Boolean setPic(PicDataBean picDataBean);
	/**
	 * 设置图片
	 */
	public Boolean setPic(ImageView imageView, int srcId);

	/**
	 * 设置加载中的图标资源图标
	 */
	public SetPicInterface setLoadingPic(int resId);
}
