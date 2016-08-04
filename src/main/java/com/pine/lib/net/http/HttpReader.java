package com.pine.lib.net.http;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

import afinal.FinalHttp;
import afinal.http.AjaxCallBack;
import afinal.http.AjaxParams;

public class HttpReader
{
	private static HttpReader httpReader = null;
	private CookieStore cookieStore = new BasicCookieStore();
	FinalHttp finalHttp = new FinalHttp();
	String cookie = "";

	

	public HttpReader()
	{
		finalHttp.addHeader("Accept-Charset", "UTF-8");// 配置http请求头
		finalHttp.configCharset("UTF-8");
		finalHttp.configCookieStore(cookieStore);
		finalHttp.configRequestExecutionRetryCount(3);// 请求错误重试次数
		finalHttp.configTimeout(5000);// 超时时间
		finalHttp
				.configUserAgent("JUC (Linux; U; 2.3.7; zh-cn; MB200; 320*480) UCWEB7.9.3.103/139/999");// 配置客户端信息
	}

	public CookieStore getCookieStore()
	{
		return cookieStore;
	}
	
	public void getCode(String url, AjaxCallBack<String> callback)
	{

		finalHttp.get(url, callback);
	}


	public void postCode(String url, AjaxParams params,
			AjaxCallBack<String> callback)
	{

		finalHttp.post(url, params, callback);
		
	}


	public static HttpReader i()
	{
		if (httpReader == null)
		{
			httpReader = new HttpReader();
		}
		return httpReader;
	}
}
