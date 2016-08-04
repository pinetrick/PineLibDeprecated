package com.pine.lib.net.pic.by;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.pine.lib.R;
import com.pine.lib.base.activity.A;
import com.pine.lib.net.pic.PicDataBean;
import com.pine.lib.net.pic.SetPicInterface;

/**
 * 如果上下文变化，请重新构建这个类
 */
public class SetPicByUniversal extends BasicConfigSettion
{

	private DisplayImageOptions options;
	private File cacheDir;
	private ImageLoaderConfiguration config;



	public static SetPicByUniversal i(Context context)
	{
		return new SetPicByUniversal();
	}


	private SetPicByUniversal()
	{
		cacheDir = StorageUtils.getOwnCacheDirectory(A.c()
				.getApplicationContext(), "imageloader/Cache");

		config = new ImageLoaderConfiguration.Builder(A.c())
				.memoryCacheExtraOptions(480, 800)
				// max width, max height，即保存的每个缓存文件的最大长宽
				.threadPoolSize(3)
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				// You can pass your own memory cache
				// implementation/你可以通过自己的内存缓存实现
				.memoryCacheSize(2 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(100)
				// 缓存的文件数量
				.discCache(new UnlimitedDiscCache(cacheDir))
				// 自定义缓存路径
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(
						new BaseImageDownloader(A.c(), 5 * 1000, 30 * 1000)) // connectTimeout
																				// (5
																				// s),
																				// readTimeout
																				// (30
																				// s)超时时间
				.writeDebugLogs() // Remove for release app
				.build();// 开始构建
		// Initialize ImageLoader with configuration.

		ImageLoader.getInstance().init(config);

	}


	/**
	 * 设置加载中的图标资源图标
	 */
	@Override
	public SetPicInterface setLoadingPic(int resId)
	{
		this.loading = resId;
		return this;
	}


	@Override
	public Boolean setPic(PicDataBean picDataBean)
	{
		ImageLoader imageLoader = ImageLoader.getInstance();

		DisplayImageOptions options;
		options = new DisplayImageOptions.Builder().showImageOnLoading(loading) // 设置图片在下载期间显示的图片
				.showImageForEmptyUri(errPic)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(decodErr) // 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
				.imageScaleType(ImageScaleType.NONE)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
				// .decodingOptions(new
				// android.graphics.BitmapFactory.Options())//设置图片的解码配置
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的下载前的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// .preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				// .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
				// .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
				.build();// 构建完成

		if ((picDataBean.imageView != null) && (picDataBean.url != null)
				&& (!picDataBean.url.equals("")))
		{
			ImageLoader.getInstance().displayImage(picDataBean.url,
					picDataBean.imageView, options);
			return true;
		}
		else
		{
			return false;
		}

		// if (options == null)
		// {
		// options = new DisplayImageOptions.Builder()
		// .showImageOnLoading(resId) // 设置图片在下载期间显示的图片
		// // .showImageForEmptyUri(R.drawable.ic_empty)//
		// // 设置图片Uri为空或是错误的时候显示的图片
		// // .showImageOnFail(R.drawable.ic_error) //
		// // 设置图片加载/解码过程中错误时候显示的图片
		// .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
		// .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
		// .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
		// .imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
		// .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
		// // .decodingOptions(BitmapFactory.Options
		// // decodingOptions)//设置图片的解码配置
		// // .delayBeforeLoading(int delayInMillis)//int
		// // delayInMillis为你设置的下载前的延迟时间
		// // 设置图片加入缓存前，对bitmap进行设置
		// // .preProcessor(BitmapProcessor preProcessor)
		// .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
		// // .displayer(new RoundedBitmapDisplayer(200))//
		// // 是否设置为圆角，弧度为多少 2选1
		// .displayer(new FadeInBitmapDisplayer(1000))// 是否图片加载好后渐入的动画时间
		// .build();// 构建完成
		// }
		//
		// if ((imageView != null) && (url != null) && (!url.equals("")))
		// {
		// ImageLoader.getInstance().displayImage(url, imageView, options);
		// return true;
		// }
		// else
		// {
		// return false;
		// }
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
