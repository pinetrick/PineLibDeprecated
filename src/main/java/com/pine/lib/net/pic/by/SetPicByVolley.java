package com.pine.lib.net.pic.by;

import org.apache.http.impl.client.DefaultHttpClient;

import afinal.FinalBitmap;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.pine.lib.R;
import com.pine.lib.net.pic.PicDataBean;
import com.pine.lib.net.pic.SetPicInterface;

/**
 * 如果上下文变化，请重新构建这个类
 */
public class SetPicByVolley extends BasicConfigSettion
{
	private FinalBitmap bitmap;
	private RequestQueue mQueue = null;



	public static SetPicByVolley i(Context context)
	{
		return new SetPicByVolley();
	}


	private SetPicByVolley()
	{

	}





	@Override
	public Boolean setPic(final PicDataBean picDataBean)
	{
		if ((picDataBean.imageView != null) && (picDataBean.url != null) && (!picDataBean.url.equals("")))
		{
			if (mQueue == null)
			{
				DefaultHttpClient httpclient = new DefaultHttpClient();
				// 非持久化存储(内存存储) BasicCookieStore | 持久化存储 PreferencesCookieStore
				httpclient.setCookieStore(picDataBean.cookieStore);
				HttpStack httpStack = new HttpClientStack(httpclient);

				mQueue = Volley.newRequestQueue(picDataBean.imageView.getContext(),
						httpStack);
				
				// bitmap.configLoadingImage(resId);
			}
			
			picDataBean.imageView.setImageResource(loading);
			
			ImageRequest imageRequest = new ImageRequest(picDataBean.url,
					new Response.Listener<Bitmap>() {
					
						
						@Override
						public void onResponse(Bitmap response)
						{
							picDataBean.imageView.setImageBitmap(response);
						}
					}, 500, 500, Config.RGB_565, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error)
						{
							picDataBean.imageView.setImageResource(errPic);
						}
						
					});
			// bitmap.display(imageView, url);
			
			mQueue.add(imageRequest);
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
