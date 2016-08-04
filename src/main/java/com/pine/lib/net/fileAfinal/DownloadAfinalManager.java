package com.pine.lib.net.fileAfinal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import afinal.FinalHttp;
import afinal.http.AjaxCallBack;
import afinal.http.HttpHandler;
import android.annotation.SuppressLint;
import android.widget.Toast;

import com.pine.lib.base.activity.A;
import com.pine.lib.func.broadcast.BroadCastManager;
import com.pine.lib.func.broadcast.BroadCastRecImp;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.file.BaseFile;
import com.pine.lib.storage.sqlite.SqliteManager;
import com.pine.lib.windows.alter.MessageBox;

public class DownloadAfinalManager
{
	private static Object lockObject = new Object();
	private static G g = new G(DownloadAfinalManager.class);
	private static DownloadAfinalManager manager = null;
	private static List<OneDownloadTask> tasks = getItems();



	public static List<OneDownloadTask> getItems()
	{
		// g.i("从数据库刷新下载项");
		List<OneDownloadTask> r = (List) SqliteManager.i().select(
				"select * from `OneDownloadTask`", OneDownloadTask.class);
		return r;
	}


	/**
	 * 获取文件现在的下载状态
	 * 
	 * @param tsk
	 * @return
	 */
	public static StateEnum getFileState(OneDownloadTask tsk)
	{
		List<OneDownloadTask> tasks1 = getTasks();

		for (OneDownloadTask oneDownloadTask : tasks1)
		{

			if (tsk.getSaveDir().equals(oneDownloadTask.getSaveDir()))
			{// 下载已存在
				if (oneDownloadTask.getState() == 100)// 下载完成
				{
					return StateEnum.FileDownloaded;
				}
				else
				{
					return StateEnum.FileDownloading;
				}
			}
		}

		// 检查文件存在性
		if (new BaseFile().isFileExist(tsk.getSaveDir()))
		{
			return StateEnum.FileExist;
		}

		return StateEnum.EnableDownload;
	}


	/**
	 * 
	 * @param tsk
	 */
	public void startTsk(OneDownloadTask tsk)
	{
		startTsk(tsk, false);
	}


	/**
	 * 开始下载 不做验证 请提前调用getFileState()验证文件存在性
	 * 
	 * @param tsk
	 */
	public void startTsk(OneDownloadTask tsk, Boolean delIfExist)
	{
		if (delIfExist)
		{
			BaseFile.i().deleteDir(new File(tsk.getSaveDir()));
			List<OneDownloadTask> tasks1 = getTasks();
			for (OneDownloadTask oneDownloadTask2 : tasks1)
			{
				if (oneDownloadTask2.getSaveDir().equals(tsk.getSaveDir()))
				{
					oneDownloadTask2.del();
				}
			}

		}

		if (tasks == null)// 防止空指针
		{
			MessageBox.i().show("数据库字段有变，请更新数据库！");
			return;
		}

		if (tsk.getId() <= 0)
		{
			tsk.insert();
		}
		if (!tasks.contains(tsk))
		{
			tasks.add(tsk);
		}

		final int id = tasks.indexOf(tsk);

		g.d("下载开始 - " + tsk.getSaveDir());
		FinalHttp fh = new FinalHttp();

		HttpHandler<File> hd = fh.download(tsk.getUrl(), tsk.getSaveDir(),
				true, new AjaxCallBack<File>() {

					@Override
					public void onStart()
					{
						super.onStart();
						tasks.get(id).setState(10);
						g.i("下载开始 = " + 10);
						BroadCastManager.i().send("下载开始");
						BroadCastManager.i().send("下载数量及分类改变");
					}


					@Override
					public void onLoading(long count, long current)
					{
						if (tasks.size() > id)
						{
							tasks.get(id).setFileSize(count);
							tasks.get(id).setNowDownload(current);
							BroadCastManager.i().send("下载进度改变");
						}
						super.onLoading(count, current);

					}


					@Override
					public void onSuccess(File t)
					{
						tasks.get(id).setState(100);
						tasks.get(id).setDownloadFinishTime();

						g.i("下载完成 - " + t.getName());
						BroadCastManager.i().send("下载进度改变");
						BroadCastManager.i().send("下载完成");
						BroadCastManager.i().send("下载数量及分类改变");
						super.onSuccess(t);
					}


					@Override
					public void onFailure(Throwable t, String strMsg)
					{
						if (strMsg == null)
						{
							strMsg = "";
						}

						if (strMsg.contains("user stop download thread"))
						{
							tasks.get(id).setState(20);
							g.d("用户主动暂停 = 20");
							BroadCastManager.i().send("用户暂停下载", strMsg);
							return;
						}

						tasks.get(id).setState(30);
						tasks.get(id).setTag(strMsg);
						super.onFailure(t, strMsg);
						g.e("下载失败(30) - " + strMsg);
						BroadCastManager.i().send("下载进度改变");
						BroadCastManager.i().send("下载失败", strMsg);
						BroadCastManager.i().send("下载数量及分类改变");

						File file = new File(tasks.get(id).getSaveDir());
						if (file.exists())
						{
							g.e("文件存在(40) - " + strMsg);
							tasks.get(id).setState(40);// 文件已存在
							BroadCastManager.i().send("下载失败，文件存在", strMsg);
							BroadCastManager.i().send("下载数量及分类改变");
						}

					}
				});
		tsk.setHandler(hd);

	}


	private DownloadAfinalManager()
	{

		SqliteManager.i().createTable(OneDownloadTask.class);
		// BroadCastManager.i()
		// .reg("下载数量及分类改变", DownloadAfinalManager.class, this);
	}


	public static List<OneDownloadTask> getTasks()
	{
		List<OneDownloadTask> tasks1 = getItems();

		for (OneDownloadTask oneDownloadTask : tasks)
		{
			if (oneDownloadTask.getHandler() != null)
			{
				for (OneDownloadTask oneDownloadTask1 : tasks1)
				{
					if (oneDownloadTask1.getUrl().equals(
							oneDownloadTask.getUrl()))
					{
						oneDownloadTask1.setHandler(oneDownloadTask
								.getHandler());
					}
				}
			}
		}
		tasks.clear();
		tasks.addAll(tasks1);
		return tasks;
	}


	public static DownloadAfinalManager i()
	{
		if (manager == null)
		{
			synchronized (lockObject)
			{
				if (manager == null)
				{
					manager = new DownloadAfinalManager();
				}
			}
		}
		return manager;
	}

}
