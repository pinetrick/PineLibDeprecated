package com.pine.lib.net.fileAfinal;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import afinal.http.HttpHandler;

import com.pine.lib.func.broadcast.BroadCastManager;
import com.pine.lib.func.debug.G;
import com.pine.lib.storage.sqlite.SqliteBeanInterface;
import com.pine.lib.storage.sqlite.SqliteManager;

public class OneDownloadTask implements SqliteBeanInterface
{

	private static G g = new G(OneDownloadTask.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private int id = -1;
	private String url = "";
	private String saveDir = "";
	private long fileSize = -1;
	private long nowDownload = -1;
	private String fileName = "";
	/**
	 * <pre>
	 * 0	已添加
	 * 10	下载中
	 * 20	下载暂停
	 * 30	下载失败
	 * 40	文件存在 
	 * 100	下载完成
	 * </pre>
	 */
	private int state = 0;
	private String downloadFinishTime = "";
	/**
	 * 下载速度 单位kb每秒
	 */
	private long spend = 10;
	/**
	 * 附加的信息，比如下载失败附加的失败原因
	 */
	private String tag = "";
	private HttpHandler<File> handler;



	public void update()
	{
		SqliteManager.i().update(this);
		// g.d("更新一条数据" + toString());
	}


	public void insert()
	{
		SqliteManager.i().insert(this, OneDownloadTask.class);
		BroadCastManager.i().send("下载数量及分类改变");
	}


	public void del()
	{
		SqliteManager.i().del(this);
		BroadCastManager.i().send("下载数量及分类改变");
	}


	public String getFileName()
	{
		return fileName;
	}


	public String getTag()
	{
		return tag;
	}


	public void setTag(String tag)
	{
		this.tag = tag;
	}


	public String getDownloadFinishTime()
	{
		return downloadFinishTime;
	}


	public void setDownloadFinishTime()
	{
		Date today = new Date(System.currentTimeMillis());// 获取当前时间
		String today1 = sdf.format(today);
		this.downloadFinishTime = today1;
		g.d("------------" + today1);
		update();
	}


	public void pause()
	{
		setState(20);
		g.i("下载暂停(20)");
		if (handler != null)
		{
			handler.stop();
			g.i("暂停成功");
		}
		else
		{
			g.e("暂停失败，handle空了");
		}
		BroadCastManager.i().send("下载进度改变");
	}


	public void resume()
	{
		g.i("下载继续");
		DownloadAfinalManager.i().startTsk(this);
		BroadCastManager.i().send("下载进度改变");
	}


	public HttpHandler<File> getHandler()
	{
		return handler;
	}


	public void setHandler(HttpHandler<File> handler)
	{
		this.handler = handler;
	}


	public int getState()
	{
		// if ((handler == null))
		// {
		// state = 20;
		// g.i("重启后状态改成暂停");
		// }
		return state;
	}


	public void setState(int state)
	{
		this.state = state;
		update();
	}


	public String getUrl()
	{
		return url;
	}


	public void setUrl(String url)
	{
		this.url = url;
		update();
	}


	public String getSaveDir()
	{
		saveDir = saveDir.replace("?", "");
		saveDir = saveDir.replace(";", "");
		saveDir = saveDir.replace("=", "");
		saveDir = saveDir.replace(",", "");
		saveDir = saveDir.replace("*", "");
		return saveDir;
	}


	public void setSaveDir(String saveDir)
	{
		this.saveDir = saveDir;
		fileName = saveDir.substring(saveDir.lastIndexOf("/") + 1);

		update();
	}


	public long getFileSize()
	{
		return fileSize;
	}


	public String getFileSizeString()
	{
		long size = fileSize / 1024;
		if (size < 1024)
		{
			return size + "KB";
		}
		else
		{

			double s = size / 1024f;
			DecimalFormat df2 = new DecimalFormat("####.00");
			return df2.format(s) + "MB";
		}

	}


	public String getSpend()
	{
		long size = spend / 1024;
		if (size < 1024)
		{
			return size + "KB/s";
		}
		else
		{

			double s = size / 1024f;
			DecimalFormat df2 = new DecimalFormat("####.00");
			return df2.format(s) + "MB/s";
		}
	}


	public void setFileSize(long fileSize)
	{
		this.fileSize = fileSize;
		update();
	}


	public long getNowDownload()
	{
		return nowDownload;
	}



	private long lastTime = 0;



	public void setNowDownload(long nowDownload)
	{
		setState(10);
		// g.d("下载状态10");
		this.nowDownload = nowDownload;
		spend = nowDownload - lastTime;
		lastTime = nowDownload;
		update();
	}


	public int getId()
	{
		return id;
	}


	public void setId(int id)
	{
		this.id = id;
	}


	@Override
	public String toString()
	{
		return "OneDownloadTask [sdf=" + sdf + ", id=" + id + ", url=" + url
				+ ", saveDir=" + saveDir + ", fileSize=" + fileSize
				+ ", nowDownload=" + nowDownload + ", fileName=" + fileName
				+ ", state=" + state + ", downloadFinishTime="
				+ downloadFinishTime + ", handler=" + handler + "]";
	}

}
