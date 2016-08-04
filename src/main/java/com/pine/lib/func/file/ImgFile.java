package com.pine.lib.func.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.pine.lib.func.debug.G;
import com.pine.lib.func.flash.DataFlash;

/**
 * 从文件读取图片的相关方法
 * 
 * <pre>
 * 
 * </pre>
 */
public class ImgFile extends BaseFile
{
	private static G g = new G(ImgFile.class);
	private static ImgFile imgFile;
	/**
	 * 是否缓存图片BitMap
	 */
	private Boolean isFlash = false;
	/**
	 * 系统图片缓存
	 */
	private DataFlash<Bitmap> flash;



	public ImgFile(Boolean isFlash)
	{
		this.isFlash = isFlash;
		if (isFlash)
		{
			flash = new DataFlash<Bitmap>();
		}
	}


	/**
	 * 非严谨单例模式
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public static ImgFile i(Boolean isFlash)
	{
		if (imgFile == null)
		{
			imgFile = new ImgFile(isFlash);
		}
		return imgFile;
	}


	/**
	 * 非严谨单例模式
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public static ImgFile i()
	{
		return i(false);
	}
	
	/**
	 * 从文件缓存读取Bitmap
	 * <pre>
	 * 
	 * </pre>
	 * @param localUrl
	 * @return
	 */
	public Bitmap getBitmapFromFlash(String localUrl)
	{
		if (isFlash)
		{
			Bitmap bm = flash.get(localUrl);
			if (bm != null)
			{
				return bm;
			}
		}
		return null;
	}

	public Bitmap getFileFromUrl(String localUrl)
	{
		Bitmap r = getBitmapFromFlash(localUrl);
		if (r != null) return r;

		try
		{
			if (isFileExist(localUrl))
			{
				BitmapFactory.Options opt = new BitmapFactory.Options();  
				opt.inPreferredConfig = Bitmap.Config.RGB_565;   
				opt.inPurgeable = true;  
				opt.inInputShareable = true;  
				
				FileInputStream fis = new FileInputStream(localUrl);
				Bitmap bm = BitmapFactory.decodeStream(fis,null,opt);
				if (isFlash)
				{
					flash.add(localUrl, bm);
				}
				g.d("从文件加载");
				return bm;
			}
			else
			{
				g.e("图片加载异常 - 文件不存在");
				return null;
			}
		}
		catch (Exception e)
		{
			g.e("图片加载异常 - " + e.toString());
			return null;
		}

	}


	public Boolean saveToFile(Bitmap bitmap, String url)
	{
		g.d("保存图片文件 - " + url);
		mkDir(url);
		File f = new File(url);
		if (f.exists())
		{
			f.delete();
		}

		try
		{
			if (bitmap != null)
			{
				FileOutputStream out = new FileOutputStream(f);
				
				bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
				out.flush();
				out.close();
				return true;
			}
			return false;
		}
		catch (FileNotFoundException e)
		{
			return false;
		}
		catch (IOException e)
		{
			return false;
		}
	}

}
