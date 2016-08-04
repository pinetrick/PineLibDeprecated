package com.pine.lib.net.pic.by;

import java.io.OutputStream;

import afinal.bitmap.download.Downloader;
import android.content.Context;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.pine.lib.R;
import com.pine.lib.net.pic.PicDataBean;
import com.pine.lib.net.pic.SetPicInterface;

/**
 * 如果上下文变化，请重新构建这个类
 */
public class SetPicByXUtils extends BasicConfigSettion
{
	private BitmapUtils bitmapUtils;



	public static SetPicByXUtils i(Context context)
	{
		return new SetPicByXUtils();
	}


	private SetPicByXUtils()
	{

	}



	/**
	 * 默认一律返回true
	 */
	@Override
	public Boolean setPic(PicDataBean picDataBean)
	{
		if ((picDataBean.imageView != null) && (picDataBean.url != null)
				&& (!picDataBean.url.equals("")))
		{
			if (bitmapUtils == null)
			{
				bitmapUtils = new BitmapUtils(
						picDataBean.imageView.getContext());
				bitmapUtils.configDefaultLoadingImage(loading);
				bitmapUtils.configDefaultLoadFailedImage(errPic);
				
			}
			bitmapUtils.display(picDataBean.imageView, picDataBean.url);
			return true;
		}
		else
		{
			return false;
		}
	}


	@Override
	public Boolean setPic(ImageView imageView, int srcId)
	{
		if ((imageView != null))
		{
			imageView.setImageResource(srcId);
			imageView.setTag("null");
			return true;
		}
		else
		{
			return false;
		}
	}
}
