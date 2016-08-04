package com.pine.lib.net.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.pine.lib.func.debug.G;
import com.pine.lib.func.thread.AbsThread;

public class FileDownloader2 extends AbsThread
{
	private static G g = new G(FileDownloader2.class);
	private String url = "";
	private DownloadPrecent downloadPrecentListener;
	


	public DownloadPrecent getDownloadPrecentListener()
	{
		return downloadPrecentListener;
	}


	public FileDownloader2 setDownloadPrecentListener(DownloadPrecent downloadPrecentListener)
	{
		this.downloadPrecentListener = downloadPrecentListener;
		return this;
	}
	
	

	public FileDownloader2(String url)
	{
		this.url = url;
	}




	private void printHttpResponse(HttpResponse httpResponse)
	{
		Header[] headerArr = httpResponse.getAllHeaders();
		for (int i = 0; i < headerArr.length; i++)
		{
			Header header = headerArr[i];
		}
		HttpParams params = httpResponse.getParams();
	}

	@Override
	public void run()
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try
		{
			HttpResponse httpResponse = httpClient.execute(httpGet);
			printHttpResponse(httpResponse);
			HttpEntity httpEntity = httpResponse.getEntity();
			long length = httpEntity.getContentLength();
			is = httpEntity.getContent();
			if (is != null)
			{
				baos = new ByteArrayOutputStream();
				byte[] buf = new byte[128];
				int read = -1;
				int count = 0;
				while ((read = is.read(buf)) != -1)
				{
					baos.write(buf, 0, read);
					count += read;
				}
				byte[] data = baos.toByteArray();
				Bitmap bit = BitmapFactory.decodeByteArray(data, 0, data.length);
				callToMainThread(bit);
			}
		}
		catch (Exception e)
		{
			g.e(e.toString());
			callToMainThread(url);
		}
		
		finally
		{
			try
			{
				if (baos != null)
				{
					baos.close();
				}
				if (is != null)
				{
					is.close();
				}
			}
			catch (IOException e)
			{
				callToMainThread(url);
			}
		}
		
	}


	@Override
	public void runInMainThread(Object callMethod)
	{
		if (callMethod instanceof Bitmap)
		{
			if (downloadPrecentListener != null)
			{
				downloadPrecentListener.onDownloadFinish((Bitmap)callMethod);
			}
		}
		else if  (callMethod instanceof String)
		{
			if (downloadPrecentListener != null)
			{
				downloadPrecentListener.onDownloadFail((String)url);
			}
		}
		
	}
}
