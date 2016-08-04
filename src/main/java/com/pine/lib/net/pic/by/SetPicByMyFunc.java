package com.pine.lib.net.pic.by;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.pine.lib.R;
import com.pine.lib.base.activity.A;
import com.pine.lib.base.config.PicConfig;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.file.ImgFile;
import com.pine.lib.func.myTimer.MyTimer;
import com.pine.lib.func.myTimer.onTimerListener;
import com.pine.lib.net.file.DownloadPrecent;
import com.pine.lib.net.file.FileDownloader2;
import com.pine.lib.net.pic.PicDataBean;
import com.pine.lib.net.pic.SetPicInterface;

/**
 * 如果上下文变化，请重新构建这个类
 */
public class SetPicByMyFunc extends BasicConfigSettion implements onTimerListener
{

	private static Context lastContext;
	private static G g = new G(SetPicByMyFunc.class);
	private static SetPicByMyFunc getPic;
	private static Object lock = new Object();
	private static List<String> urlList = new ArrayList<String>();
	private static Map<String, WeakReference<ImageView>> map = new HashMap<String, WeakReference<ImageView>>();
	/**
	 * 默认加载图标
	 */
	private int defLoadingRes = R.drawable.loading_little_image;

	/**
	 * 界面刷新adapter 或者 RootView
	 */
	private Object adapterOrView;

	private MyTimer myTimer;
	// private List<String> needRefresh = new ArrayList<String>();
	private List<String> needToDel = new ArrayList<String>();
	/**
	 * 加载线程池
	 */
	private ExecutorService pool = Executors.newFixedThreadPool(3);



	@Override
	public void onTimer()
	{
		g.i("计时器刷新图片,剩余数量" + urlList.size());
		needToDel.clear();
		for (String string : urlList)
		{
			ImageView iView = map.get(string).get();
			if (iView != null)
			{
				Boolean isAdd = setPic(new PicDataBean(iView, string));
				if (isAdd)
				{
					needToDel.add(string);
				}
			}
		}

		for (String string : needToDel)
		{
			urlList.remove(string);
			map.remove(string);
		}

		if (needToDel.size() == 0)
		{
			g.i("全部刷新完成，刷新图片计时器停止");
			myTimer.stop();
		}
	}


	// /**
	// * 加载图片并且延迟强制刷新
	// *
	// * <pre>
	// *
	// * </pre>
	// */
	// public void setPicAndForceRefresh(final ImageView v, final String url)
	// {
	// Boolean isOk = setPic(v, url);
	//
	//
	//
	// if (!isOk)
	// {
	// if (needRefresh.contains(url))
	// needRefresh.add(url);
	//
	// final MyTimer delayTimes = new MyTimer(1000);
	// delayTimes.setOnTimerListener(new onTimerListener() {
	//
	// @Override
	// public void onTimer()
	// {
	// Boolean isOk = setPic(v, url);
	// if (isOk)
	// {
	// delayTimes.stop();
	// }
	// }
	// }).start();
	// }
	// }

	/**
	 * 获取本地存储的图片的URL
	 * 
	 * <pre>
	 * 注意 本函数返回的URL不一定存在 需要做校验文件是否已经存在
	 * </pre>
	 * 
	 * @param context
	 * @param url
	 *            网络图片的URL http://www.XXX.com/a.jpg
	 * @return
	 */
	public String getLocalUrl(Context context, String url)
	{
		return PicConfig.getPictureSavingDir(context)
				+ "/"
				+ url.replace("http", "").replace(":", "").replace("?", "")
						.replace("&", "").replace("/", "").replace("=", "")
						.replace("%", "").replace("\\", "").replace(".jpg", "")
						.replace(".png", "").replace(".gif", "");
	}


	// /**
	// * 设置图片的方法
	// *
	// * <pre>
	// * 返回true - 来自文件 即时成功
	// * 返回false - 文件来自网络 稍后设置
	// * </pre>
	// *
	// * @param v
	// * 图片控件
	// * @param url
	// * URL
	// * @return
	// */
	// public Boolean setPic(ImageView v, final String url, Object
	// adapterOrView)
	// {
	// this.adapterOrView = adapterOrView;
	// return setPic(v, url);
	// }

	/**
	 * 设置图片的方法
	 * 
	 * <pre>
	 * 返回true - 来自文件 即时成功
	 * 返回false - 文件来自网络 稍后设置
	 * </pre>
	 * 
	 * @param imageView
	 *            图片控件
	 * @param url
	 *            URL
	 * @return
	 */
	public Boolean setPic(final PicDataBean picDataBean)
	{
		if (picDataBean.imageView == null) return false;
		final String picUrl = getLocalUrl(picDataBean.imageView.getContext(), picDataBean.url);
		picDataBean.imageView.setTag(picDataBean.url);

		// 从缓存加载
		Bitmap tmp = ImgFile.i(true).getBitmapFromFlash(picUrl);
		if (tmp != null)
		{
			picDataBean.imageView.setImageBitmap(tmp);
			return true;
		}

		if (ImgFile.i(true).isFileExist(picUrl))// 如果本地存在文件 读本地缓存
		{
			picDataBean.imageView.setImageBitmap(ImgFile.i().getFileFromUrl(picUrl));
			return true;
		}
		else
		{

			// 通过网络加载图片
			picDataBean.imageView.setImageResource(defLoadingRes);
			// boolean flag=CheckNetState.isNetworkAvailable(v.getContext());
			// if (!flag)//网络不可用
			// {
			// g.w("网络不可用 - 图片加载失败");
			// return true;
			// }

			if (urlList.contains(picDataBean.url))
			{
				// SetPicByOther.setByxUtils(v, url);
				return false;// 如果有线程正在从网络加载 则不再加载
			}
			map.put(picDataBean.url, new WeakReference<ImageView>(picDataBean.imageView));
			urlList.add(picDataBean.url);

			// OneDownloadTask tsk = new OneDownloadTask();
			// tsk.setUrl(url);
			// tsk.setSaveDir(picUrl);
			// DownloadAfinalManager.i().startTsk(tsk);
			// myTimer.start();

			// FileDownloader.i()
			// .setDownloadPrecentListener(new DownloadPrecent() {
			Thread td = new FileDownloader2(picDataBean.url)
					.setDownloadPrecentListener(new DownloadPrecent() {
						@Override
						public void onProgressRunning(Float precent)
						{
							// TODO Auto-generated method stub

						}


						@Override
						public void onDownloadFinish(Bitmap bitmap)
						{

							ImgFile.i().saveToFile(bitmap, picUrl);
							// g.d("计时器开始---------");
							myTimer.start();

							if (map.get(picDataBean.url) != null)
							{

								ImageView iView = map.get(picDataBean.url).get();
								if (iView == null)
								{
									g.e("空指针 - 内存数据丢失");
									return;
								}
								if (bitmap == null)
								{
									iView.setImageResource(defLoadingRes);
									return;
								}

							}

						}


						@Override
						public void onDownloadFail(String url)
						{
							urlList.remove(url);
							if (map.get(url) != null)
							{

								ImageView iView = map.get(url).get();
								setPic(new PicDataBean(iView, url));
							}

							g.e("图片加载失败！" + url);
						}
					});
			pool.execute(td);
			return false;
		}

	}


	// if ((iView != null) && (url != null)
	// && (iView.getTag() != null))
	// {
	// if (((String) (iView.getTag())).equals(url))
	// {
	// iView.setImageBitmap(bitmap);
	// }

	// if (adapterOrView != null)
	// {
	// if (adapterOrView instanceof View)
	// {
	// g.d("设置图片");
	// ((View) (adapterOrView))
	// .postInvalidate();
	// }
	// if (adapterOrView instanceof BaseAdapter)
	// {
	// g.d("发送更新UI通知");
	// ((BaseAdapter) (adapterOrView))
	// .notifyDataSetChanged();
	// }
	// }
	// }

	// map.remove(url);
	// urlList.remove(url);

	// private Handler mHandler = new Handler(){
	// @Override
	// public void handleMessage(Message msg) {
	// String url = (String)msg.obj;
	// if (url != null)
	// {
	// if (map.get(url) != null)
	// {
	// ImageView iView = map.get(url).get();
	// setPic(iView, url);
	// }
	// }
	//
	// super.handleMessage(msg);
	// }
	//
	// };

	private SetPicByMyFunc(Context context)
	{

		myTimer = new MyTimer(1000);
		myTimer.setOnTimerListener(this);

	}


	/**
	 * 所有Activity必须继承自PineActivity
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public static SetPicByMyFunc i()
	{
		return SetPicByMyFunc.i(A.c());
	}


	/**
	 * 单例模式初始化
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param context
	 * @return
	 */
	public static SetPicByMyFunc i(Context context)
	{

		if (getPic == null)
		{
			synchronized (lock)
			{
				if (getPic == null)
				{
					lastContext = context;
					getPic = new SetPicByMyFunc(context);
				}
			}
		}
		if (lastContext != context)
		{
			lastContext = context;
			getPic = new SetPicByMyFunc(context);
			urlList.clear();
			map.clear();
		}
		return getPic;
	}


	/**
	 * 设置加载中的图标资源图标
	 */
	@Override
	public SetPicInterface setLoadingPic(int resId)
	{
		this.defLoadingRes = resId;
		return this;
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
