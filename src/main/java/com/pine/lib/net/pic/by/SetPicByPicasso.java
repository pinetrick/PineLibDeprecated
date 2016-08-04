package com.pine.lib.net.pic.by;

import android.content.Context;
import android.widget.ImageView;

import com.pine.lib.R;
import com.pine.lib.func.debug.G;
import com.pine.lib.net.pic.PicDataBean;
import com.pine.lib.net.pic.SetPicInterface;
import com.squareup.picasso.Picasso;

/**
 * 如果上下文变化，请重新构建这个类
 */
public class SetPicByPicasso extends BasicConfigSettion
{
	private Picasso picasso;




	public static SetPicByPicasso i(Context context)
	{
		return new SetPicByPicasso();
	}


	private SetPicByPicasso()
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
			if (picasso == null)
			{
				picasso = Picasso.with(picDataBean.imageView.getContext());
				picasso.setLoggingEnabled(G.getEnableGlobalDebug());
				picasso.setIndicatorsEnabled(G.getEnableGlobalDebug());
				picasso.setDebugging(G.getEnableGlobalDebug());

			}

			picasso.load(picDataBean.url).placeholder(loading).error(errPic)
					.into(picDataBean.imageView);

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
			if (picasso == null)
			{
				picasso = Picasso.with(imageView.getContext());
			}

			picasso.load(srcId).into(imageView);

			return true;
		}
		else
		{
			return false;
		}
	}
}
