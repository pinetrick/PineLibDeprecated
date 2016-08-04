package com.pine.lib.func.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.text.DecimalFormat;

import android.content.Context;

import com.pine.lib.base.activity.A;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;

public class BaseFile extends File
{

	private static G g = new G(BaseFile.class);
	private File file;



	/**
	 * 文件大小单位转换
	 * 
	 * @param size
	 * @return
	 */
	public String fileSize(long size)
	{
		DecimalFormat df = new DecimalFormat("###.##");
		float f = ((float) size / (float) (1024 * 1024));

		if (f < 1.0)
		{
			float f2 = ((float) size / (float) (1024));
			return df.format(new Float(f2).doubleValue()) + " KB";
		}
		else
		{
			return df.format(new Float(f).doubleValue()) + " MB";
		}

	}


	/**
	 * 获取文件夹大小
	 * 
	 * @param file
	 *            File实例
	 * @return long 单位为Byte
	 * @throws Exception
	 */
	public long getFolderSize(File file) throws Exception
	{
		long size = 0;
		File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++)
		{
			if (fileList[i].isDirectory())
			{
				size = size + getFolderSize(fileList[i]);
			}
			else
			{
				size = size + fileList[i].length();
			}
		}
		return size;
	}


	/**
	 * 获取缓存目录文件大小 返回格式 1.01 MB 2.54 KB
	 */
	public String getCacheSize(Context context)
	{
		try
		{
			return fileSize(getFolderSize(context.getCacheDir()));
		}
		catch (Exception e)
		{
			return "未知大小";
		}

	}


	/**
	 * 删除缓存目录
	 */
	public void deleteCache(Context context)
	{
		try
		{
			File dir = context.getCacheDir();
			if (dir != null && dir.isDirectory())
			{
				deleteDir(dir);
			}
		}
		catch (Exception e)
		{
		}
	}


	/**
	 * 递归删除目录和目录下所有文件
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param dir
	 * @return
	 */
	public boolean deleteDir(File dir)
	{
		if (dir != null && dir.isDirectory())
		{
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++)
			{
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success)
				{
					return false;
				}
			}
		}
		return dir.delete();
	}


	/**
	 * 检测文件是否存在
	 * 
	 * <pre>
	 * 输入 : "/mnt/sdcard/a.doc"
	 * </pre>
	 * 
	 * @param url
	 * @return
	 */
	public Boolean isFileExist(String url)
	{
		file = new File(url);
		return file.exists();
	}


	/**
	 * 创建目录 - 输入请注意格式
	 * 
	 * <pre>
	 * "/mnt/a/b.jpg" - 创建 - "/mnt/a"
	 * "/mnt/a/b" - 创建 - "/mnt/a"
	 *  "/mnt/a/b/" - 创建 - "/mnt/a/b"
	 *  实现：将删除最后一个反斜杠之后的内容 然后创建
	 * </pre>
	 * 
	 * @param url
	 * @return
	 */
	public Boolean mkDir(String url)
	{
		if (url.contains("/"))
		{
			url = url.substring(0, url.lastIndexOf("/"));
			g.i("创建目录 ： " + url);
			file = new File(url);
			file.mkdirs();
			return true;
		}
		else
		{
			return false;
		}
	}


	


	public static BaseFile i()
	{
		return new BaseFile();
	}


	public BaseFile()
	{
		super("/");
		M.i().addClass(this);
	}


	public BaseFile(File dir, String name)
	{
		super(dir, name);
		M.i().addClass(this);
	}


	public BaseFile(String path)
	{
		super(path);
		M.i().addClass(this);
	}


	public BaseFile(String dirPath, String name)
	{
		super(dirPath, name);
		M.i().addClass(this);
	}


	public BaseFile(URI uri)
	{
		super(uri);
		M.i().addClass(this);
	}

}
