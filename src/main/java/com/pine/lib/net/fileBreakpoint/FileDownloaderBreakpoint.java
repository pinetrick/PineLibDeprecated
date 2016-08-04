package com.pine.lib.net.fileBreakpoint;

import java.io.File;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.pine.lib.base.activity.A;
import com.pine.lib.func.debug.G;
import com.pine.lib.net.fileBreakpoint.network.DownloadProgressListener;
import com.pine.lib.net.fileBreakpoint.network.FileDownloader;

/**
 * 文件下载类
 * 
 * <pre>
 * FileDownloaderBreakpoint
 * 		.i()
 * 		.setListener(new OnPercentChangeListener() {
 * 
 * 			&#064;Override
 * 			public void percentChange(int downloadSize)
 * 			{
 * 				g.d(&quot;文件&quot; + downloadSize);
 * 
 * 			}
 * 
 * 
 * 			&#064;Override
 * 			public void onDownloadErr()
 * 			{
 * 				// TODO Auto-generated method stub
 * 
 * 			}
 * 
 * 
 * 			&#064;Override
 * 			public void fileSize(int size)
 * 			{
 * 				g.d(&quot;文件大小&quot; + size);
 * 
 * 			}
 * 		})
 * 		.download(
 * 				&quot;http://dlsw.baidu.com/sw-search-sp/soft/3a/12350/QQ6.3.1410836688.exe&quot;,
 * 				&quot;/mnt/sdcard/1.exe&quot;);
 * </pre>
 */
public class FileDownloaderBreakpoint
{

	private static Object locker = new Object();
	private static FileDownloaderBreakpoint fileDownloaderBreakpoint = null;
	private static G g = new G(FileDownloaderBreakpoint.class);
	private OnPercentChangeListener listener;
	private Context context;
	private FileDownloader loader;



	/**
	 * 暂停当前下载
	 * 
	 * <pre>
	 * 
	 * </pre>
	 */
	public void pause()
	{
		loader.pause();
	}


	/**
	 * 主线程(UI线程) 对于显示控件的界面更新只是由UI线程负责，如果是在非UI线程更新控件的属性值，更新后的显示界面不会反映到屏幕上
	 * 如果想让更新后的显示界面反映到屏幕上，需要用Handler设置。
	 * 
	 * @param path
	 * @param savedir
	 */
	public void download(final String path, final String savePath)
	{
		final File savedir = new File(savePath);
		g.d("下载从" + path + ",到" + savedir);
		new Thread(new Runnable() {
			@Override
			public void run()
			{
				// 开启3个线程进行下载
				loader = new FileDownloader(context, path, savedir, 3);
				if (listener != null)
				{
					listener.fileSize(loader.getFileSize());// 设置进度条的最大刻度为文件的长度
				}

				try
				{
					loader.download(new DownloadProgressListener() {
						@Override
						public void onDownloadSize(int size)
						{// 实时获知文件已经下载的数据长度
							Message msg = new Message();
							msg.what = 1;
							msg.getData().putInt("size", size);
							handler.sendMessage(msg);// 发送消息
						}
					});
				}
				catch (Exception e)
				{
					handler.obtainMessage(-1).sendToTarget();
				}

			}
		}).start();
	}



	/**
	 * 当Handler被创建会关联到创建它的当前线程的消息队列，该类用于往消息队列发送消息 消息队列中的消息由当前线程内部进行处理
	 * 使用Handler更新UI界面信息。
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case 1:

					if (listener != null)
					{
						listener.percentChange(msg.getData().getInt("size"));// 设置进度条的最大刻度为文件的长度
					}

					break;
				case -1:
					g.d("下载有错误！");
					if (listener != null)
					{
						listener.onDownloadErr();// 显示下载错误信息
					}

					break;
			}
		}
	};



	public FileDownloaderBreakpoint setListener(OnPercentChangeListener listener)
	{
		this.listener = listener;
		return this;
	}


	private FileDownloaderBreakpoint(Context context)
	{
		this.context = context;
	}


	public static FileDownloaderBreakpoint i(Context context)
	{
		if (fileDownloaderBreakpoint == null)
		{
			synchronized (locker)
			{
				if (fileDownloaderBreakpoint == null)
				{
					fileDownloaderBreakpoint = new FileDownloaderBreakpoint(
							context);
				}
			}
		}
		return fileDownloaderBreakpoint;
	}


	/**
	 * 本构造需要应用集成QFActivity
	 * 
	 * <pre>
	 * 
	 * </pre>
	 */
	public static FileDownloaderBreakpoint i()
	{

		return i(A.c());
	}

}
