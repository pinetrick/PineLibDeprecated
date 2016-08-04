package com.pine.lib.func.img;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * 处理bitmap
 * 
 * @author cyp
 *
 */
public class BitmapInfo {
	private Context c;

	public BitmapInfo(Context context) {
		this.c = context;
	}

	/**
	 * 获取SD卡上指定的图片原图
	 * 
	 * @param path
	 * @return
	 */
	public Bitmap getLocalBitmap(String path) {
		Bitmap bitmap = null;
		try {
			File file = new File(path);
			if (file.exists())
				bitmap = BitmapFactory.decodeFile(path);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	/**
	 * 对Bitmap图片对象进行指定宽高缩放处理
	 * 
	 * @param bm
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public Bitmap getBitmapZoom(Bitmap bm, int newWidth, int newHeight) {
		int width = bm.getWidth();
		int height = bm.getHeight();

		float scaleWidth = newWidth / width;
		float scaleHeight = newHeight / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);

		return newBm;
	}

	/**
	 * 返回当前Bitmap图片对象宽度和高度,返回格式为：当前图片宽度和高度分别为@700@500
	 * 
	 * @param bm
	 * @return
	 */
	public String getBitmapSize(Bitmap bm) {
		int width = bm.getWidth();
		int height = bm.getHeight();

		String s = "当前图片宽度和高度分别为@" + width + "@" + height;

		return s;
	}

	/**
	 * 将Bitmap保存到指定路径里，默认命名为当前时间，格式为PNG。注意路径后要加/
	 * 例如最终保存结果为：/sdcard/cacheImage/2013-08-06_14-57-52.png
	 * 
	 * @param bm
	 * @param savePath
	 */
	public void saveBitmap(Bitmap bm, String savePath) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss",
				Locale.US);
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdir();
		}
		String fname = savePath + sdf.format(new Date()) + ".png";
		try {
			FileOutputStream out = new FileOutputStream(fname);
			bm.compress(Bitmap.CompressFormat.PNG, 100, out);
			System.out.println("file " + fname + "output done.");
			bm = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存Bitmap到指定位置，指定文件名
	 * 
	 * @param bm
	 * @param savePath
	 * @param saveName
	 */
	public void saveBitmap(Bitmap bm, String savePath, String saveName) {
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdir();
		}
		String fname = savePath + saveName + ".png";
		try {
			FileOutputStream out = new FileOutputStream(fname);
			bm.compress(Bitmap.CompressFormat.PNG, 100, out);
			System.out.println("file " + fname + "output done.");
			bm = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保存Bitmap，可指定保存类型格式。要保存为的图片格式，只可输入：png或jpg或jpeg （不区分大小写）
	 * @param bm
	 * @param savePath
	 * @param saveName
	 * @param imageType
	 */
	public void saveBitmap(Bitmap bm, String savePath, String saveName,
			String imageType) {
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdir();
		}
		String fname = savePath + saveName + "." + imageType;
		try {
			FileOutputStream out = new FileOutputStream(fname);
			if (imageType.trim().toLowerCase().equalsIgnoreCase("png"))
				bm.compress(Bitmap.CompressFormat.PNG, 100, out);
			else if ((imageType.trim().toLowerCase().equalsIgnoreCase("jpeg"))
					|| (imageType.trim().toLowerCase().equalsIgnoreCase("jpg"))) {
				bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
			}
			System.out.println("file " + fname + "output done.");
			bm = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 等比例缩放图片方法，第二个参数(0.1-1.0)为缩小;(1.1- )为放大
	 * 
	 * @param bm
	 * @param scale
	 * @return
	 */
	public Bitmap getBitmapZoom(Bitmap bm, double scale) {
		int width = bm.getWidth();
		int height = bm.getHeight();

		Matrix matrix = new Matrix();
		matrix.postScale((float) scale, (float) scale);
		Bitmap newBm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);
		System.out.println("新图片的尺寸：" + newBm.getWidth() + ","
				+ newBm.getHeight());
		return newBm;
	}

	/**
	 * 指定图片路径，对图片进行等比例缩小，指定要缩小到的图片宽度，图片高度等比例自动缩小
	 * 
	 * @param picPath
	 * @param height
	 * @return
	 */
	public Bitmap getBitmapZoom(String picPath, double height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(picPath, options);
		options.inJustDecodeBounds = false;

		int be = (int) (options.outHeight / (float) height);
		if (be <= 0)
			be = 1;
		options.inSampleSize = be;

		bitmap = BitmapFactory.decodeFile(picPath, options);
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		return bitmap;
	}

	/**
	 * 保存Bitmap，可指定压缩率，默认为当前时间命名。 要保存的图片质量（0-100）值越低压缩率越高，不压缩则设为100
	 * 
	 * @param bm
	 * @param quality
	 * @param savePath
	 */
	public void saveBitmapQuality(Bitmap bm, int quality, String savePath) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss",
				Locale.US);
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdir();
		}
		String fname = savePath + sdf.format(new Date()) + ".jpg";
		try {
			FileOutputStream out = new FileOutputStream(fname);
			bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
			System.out.println("file " + fname + "output done.");
			bm = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将Bitmap对象转换为Drawable对象
	 * 
	 * @param bitmap
	 * @return
	 */
	public Drawable bitmapToDrawable(Bitmap bitmap) {
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}

	/**
	 * 
	 * byte[]数组转为 Bitmap
	 * 
	 * @param b
	 * @return
	 */
	public Bitmap bytesToBimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		}
		return null;
	}

	/**
	 * 
	 * // Bitmap 转 byte[]
	 * 
	 * @param bm
	 * @return
	 */
	public byte[] bitmapToBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 70, baos);
		return baos.toByteArray();
	}

	/**
	 * 对bitmap进行伸缩 ，根据传过来的图片文件路径对图片进行水平或者垂直伸缩
	 * 
	 * @param windowsWidth
	 *            屏幕宽度
	 * @param windowHeight
	 *            屏幕高度
	 * @param path
	 *            图片路径
	 * @return
	 * 
	 */
	public Bitmap ExtandBitmap(int windowsWidth, int windowHeight, String path) {

		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		// 返回为空
		BitmapFactory.decodeFile(path, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;

		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (height > windowHeight || width > windowsWidth) {
			int scaleX = width / windowsWidth;
			int scaleY = height / windowHeight;
			if (scaleX > scaleY) {// 按照水平方向的比例缩放
				opts.inSampleSize = scaleX;
			} else {// 按照竖直方向的比例缩放
				opts.inSampleSize = scaleY;
			}

		} else {// 如果图片比手机屏幕小 不去缩放了.
			opts.inSampleSize = 1;
		}
		opts.inJustDecodeBounds = false;
		float scale = Math.max(scaleWidth, scaleHeight);
		opts.inSampleSize = (int) scale;
		return BitmapFactory.decodeFile(path, opts);
	}

}