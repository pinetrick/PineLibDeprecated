//package com.pine.lib.net.pic.by;
//
//import java.io.File;
//
//import afinal.exception.HttpException;
//import android.content.Context;
//import android.widget.ImageView;
//
//import com.lidroid.xutils.BitmapUtils;
//import com.lidroid.xutils.HttpUtils;
//import com.lidroid.xutils.http.ResponseInfo;
//import com.lidroid.xutils.http.callback.RequestCallBack;
//import com.pine.lib.R;
//import com.pine.lib.base.activity.A;
//import com.pine.lib.base.config.PicConfig;
//import com.pine.lib.func.debug.G;
//import com.pine.lib.net.pic.SetPicInterface;
//
///**
// * 如果上下文变化，请重新构建这个类
// */
//public class SetPicByMyFunc2 implements SetPicInterface
//{
//	private static G g = new G(SetPicByMyFunc2.class);
//	private static SetPicByMyFunc2 getPic;
//	/**
//	 * 默认加载图标
//	 */
//	private int loading = R.drawable.loading_static;
//	private int errPic = R.drawable.pic_lost;
//	private int decodErr = R.drawable.error;
//
//
//
//	/**
//	 * 获取本地存储的图片的URL
//	 * 
//	 * <pre>
//	 * 注意 本函数返回的URL不一定存在 需要做校验文件是否已经存在
//	 * </pre>
//	 * 
//	 * @param context
//	 * @param url
//	 *            网络图片的URL http://www.XXX.com/a.jpg
//	 * @return
//	 */
//	public String getLocalUrl(String url)
//	{
//		return PicConfig.getPictureSavingDir(A.c())
//				+ "/"
//				+ url.replace("http", "").replace(":", "").replace("?", "")
//						.replace("&", "").replace("/", "").replace("=", "")
//						.replace("%", "").replace("\\", "").replace(".jpg", "")
//						.replace(".png", "").replace(".gif", "");
//	}
//
//
//	/**
//	 * 设置图片的方法
//	 * 
//	 * <pre>
//	 * 返回true - 来自文件 即时成功
//	 * 返回false - 文件来自网络 稍后设置
//	 * </pre>
//	 * 
//	 * @param v
//	 *            图片控件
//	 * @param url
//	 *            URL
//	 * @return
//	 */
//	public Boolean setPic(final ImageView v, final String url)
//	{
//		if (v == null) return false;
//		final String picUrl = getLocalUrl(url);
//		v.setTag(url);
//		
//		
//		
//		HttpUtils http = new HttpUtils();
//		http.download(url, picUrl, true, true,
//				new RequestCallBack<File>() {
//
//					@Override
//					public void onStart()
//					{
//						g.d("start");
//					}
//
//
//					@Override
//					public void onLoading(long total, long current,
//							boolean isUploading)
//					{
//						
//					}
//
//					@Override
//					public void onSuccess(ResponseInfo<File> responseInfo)
//					{
//						g.d("finish");
//						BitmapUtils bitmapUtils = new BitmapUtils(A.a());
//
//						// 加载本地图片(路径以/开头， 绝对路径)
//						bitmapUtils.display(v, picUrl);
//						
//						
//					}
//
//
//					@Override
//					public void onFailure(
//							com.lidroid.xutils.exception.HttpException arg0,
//							String arg1)
//					{
//						g.d("fail");
//						
//					}
//				});
//
//		return true;
//	}
//
//
//	private SetPicByMyFunc2(Context context)
//	{
//
//	}
//
//
//	/**
//	 * 所有Activity必须继承自PineActivity
//	 * 
//	 * <pre>
//	 * 
//	 * </pre>
//	 * 
//	 * @return
//	 */
//	public static SetPicByMyFunc2 i()
//	{
//		return SetPicByMyFunc2.i(A.c());
//	}
//
//
//	/**
//	 * 单例模式初始化
//	 * 
//	 * <pre>
//	 * 
//	 * </pre>
//	 * 
//	 * @param context
//	 * @return
//	 */
//	public static SetPicByMyFunc2 i(Context context)
//	{
//
//		if (getPic == null)
//		{
//
//			getPic = new SetPicByMyFunc2(context);
//
//		}
//		if (getPic == null)
//		{
//			getPic = new SetPicByMyFunc2(context);
//		}
//		return getPic;
//	}
//
//
//	/**
//	 * 设置加载中的图标资源图标
//	 */
//	@Override
//	public SetPicInterface setLoadingPic(int resId)
//	{
//		this.loading = resId;
//		return this;
//	}
//
//
//	@Override
//	public Boolean setPic(ImageView imageView, int srcId)
//	{
//		if ((imageView != null))
//		{
//			imageView.setImageResource(srcId);
//			imageView.setTag("null");
//			return true;
//		}
//		else
//		{
//			return false;
//		}
//	}
//}
