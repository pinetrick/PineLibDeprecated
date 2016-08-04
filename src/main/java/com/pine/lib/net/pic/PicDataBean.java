package com.pine.lib.net.pic;

import org.apache.http.client.CookieStore;

import android.widget.ImageView;

public class PicDataBean
{
	public ImageView imageView;
	public String url;
	public CookieStore cookieStore;

	
	public PicDataBean(ImageView imageView, String url)
	{
		this.imageView = imageView;
		this.url = url;
	}

	public ImageView getImageView()
	{
		return imageView;
	}


	public void setImageView(ImageView imageView)
	{
		this.imageView = imageView;
	}


	public String getUrl()
	{
		return url;
	}


	public void setUrl(String url)
	{
		this.url = url;
	}


	public CookieStore getCookieStore()
	{
		return cookieStore;
	}


	public void setCookieStore(CookieStore cookieStore)
	{
		this.cookieStore = cookieStore;
	}

}
