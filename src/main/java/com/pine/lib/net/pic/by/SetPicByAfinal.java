package com.pine.lib.net.pic.by;

import afinal.FinalBitmap;
import android.content.Context;
import android.widget.ImageView;

import com.pine.lib.R;
import com.pine.lib.net.pic.PicDataBean;
import com.pine.lib.net.pic.SetPicInterface;

/**
 * 如果上下文变化，请重新构建这个类
 */
public class SetPicByAfinal extends BasicConfigSettion
{
	private FinalBitmap bitmap;
	private Context context;



	public static SetPicByAfinal i(Context context)
	{
		return new SetPicByAfinal();
	}


	private SetPicByAfinal()
	{
		
	}





	@Override
	public Boolean setPic(PicDataBean picDataBean)
	{
		if ((picDataBean.imageView != null) && (picDataBean.url != null) && (!picDataBean.url.equals("")))
		{
			if (bitmap == null)
			{
				bitmap = FinalBitmap.create(picDataBean.imageView.getContext());
				bitmap.configLoadingImage(loading);
				bitmap.configLoadfailImage(errPic);
			}

			bitmap.display(picDataBean.imageView, picDataBean.url);
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
