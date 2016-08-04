package com.pine.lib.func.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.res.AssetManager;

import com.pine.lib.base.activity.A;
import com.pine.lib.func.debug.G;
import com.pine.lib.storage.SharePreferenceTool;

public class AssistFile
{
	private static G g = new G(AssistFile.class);
	/**
	 * 初始化本地数据库
	 */
	public void initDatabase(String ASSETS_DB_NAME)
	{
		String shareName = "初始化数据库" + ASSETS_DB_NAME;
		Boolean 数据库是否初始化 = SharePreferenceTool.i().getValue(shareName, false);
		if (!数据库是否初始化)
		{
			if (!ASSETS_DB_NAME.endsWith(".db"))
			{
				ASSETS_DB_NAME = ASSETS_DB_NAME + ".db";
			}
			
			copy(ASSETS_DB_NAME, "/data/data/" + A.c().getPackageName() + "/databases/", ASSETS_DB_NAME);
			SharePreferenceTool.i().setValue(shareName, true);
			g.i("数据库初始化成功！");
		}
	}
	
	/**
	 * // 将assets目录的文件复制到SD卡指定位置，指定文件名
	 * 
	 * @param ASSETS_NAME
	 * @param savePath
	 * @param saveName
	 */
	public void copy(String ASSETS_NAME, String savePath, String saveName)
	{
		String filename = savePath + "/" + saveName;

		File dir = new File(savePath);

		if (!dir.exists()) dir.mkdir();
		try
		{
			if (!new File(filename).exists())
			{
				InputStream is = A.c().getResources().getAssets()
						.open(ASSETS_NAME);
				FileOutputStream fos = new FileOutputStream(filename);
				byte[] buffer = new byte[7168];
				int count = 0;
				while ((count = is.read(buffer)) > 0)
				{
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * 从资源文件中（asset）读取文本文档(如果出现乱码，可以调换输入的格式参数charsetName)
	 * 
	 * @param assetsUri
	 * @param charsetName
	 * @return
	 */
	public String readTxtFromAssets(String assetsUri, String charsetName)
	{
		String text = null;
		try
		{
			InputStream in = A.c().getAssets().open(assetsUri);
			int size = in.available();

			byte[] buffer = new byte[size];
			in.read(buffer);
			in.close();
			text = new String(buffer, charsetName);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return text;
	}


	/**
	 * 获取assets目录下指定文件的InputStream
	 * 
	 * @param name
	 * @return
	 */
	public InputStream getISAssets(String name)
	{
		AssetManager am = A.c().getAssets();
		InputStream is = null;
		try
		{
			is = am.open(name);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return is;
	}


	/**
	 * 读取assets目录下文本类型文件内容（html或txt等）
	 * 
	 * @param fileName
	 * @return
	 */
	public String getStringFromAssets(String fileName)
	{
		String Result = "";
		try
		{
			InputStreamReader inputReader = new InputStreamReader(A.c()
					.getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";

			while ((line = bufReader.readLine()) != null)
				Result = Result + line;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return Result;
	}
}
