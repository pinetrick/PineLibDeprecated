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

public class FileDownloader extends AsyncTask<String, Float, Bitmap>
{
	private static G g = new G(FileDownloader.class);
	private DownloadPrecent downloadPrecentListener;
	


	public DownloadPrecent getDownloadPrecentListener()
	{
		return downloadPrecentListener;
	}


	public FileDownloader setDownloadPrecentListener(DownloadPrecent downloadPrecentListener)
	{
		this.downloadPrecentListener = downloadPrecentListener;
		return this;
	}


	private FileDownloader()
	{
	}


	public static FileDownloader i()
	{
		return new FileDownloader();
	}


	/** 执行下载。在背景线程调用 */
	protected Bitmap doInBackground(String... params)
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(params[0]);
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
					publishProgress(count * 1.0f / length);
				}
				byte[] data = baos.toByteArray();
				Bitmap bit = BitmapFactory.decodeByteArray(data, 0, data.length);
				return bit;
			}
		}
		catch (Exception e)
		{
			g.e(e.toString());
			
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
				e.printStackTrace();
			}
		}
		return null;
	}


	/** 更新下载进度。在UI线程调用。onProgressUpdate */
	protected void onProgressUpdate(Float... progress)
	{
		if (downloadPrecentListener != null)
		{
			downloadPrecentListener.onProgressRunning(progress[0]);
		}

	}


	/** 通知下载任务完成。在UI线程调用�? */
	protected void onPostExecute(Bitmap bit)
	{
		 
	        
		if (downloadPrecentListener != null)
		{
			downloadPrecentListener.onDownloadFinish(bit);
		}

	}


	protected void onCancelled()
	{
		g.i("下载取消");
		super.onCancelled();
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
}
