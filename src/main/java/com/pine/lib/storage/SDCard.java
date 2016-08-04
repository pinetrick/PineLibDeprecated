package com.pine.lib.storage;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;

public class SDCard
{
	/**
	 * 检测SD卡是否可用
	 * 
	 * @return
	 */
	public static boolean sdCardIsAvailable()
	{
		String status = Environment.getExternalStorageState();

		return status.equals("mounted");
	}


	/**
	 * 手机SD卡是否还有指定大小的空间
	 * 
	 * @param updateSize
	 * @return
	 */
	public static boolean enoughSpaceOnSdCard(long updateSize)
	{
		String status = Environment.getExternalStorageState();
		if (!status.equals("mounted")) return false;
		return updateSize < getRealSizeOnSdcard();
	}


	/**
	 * 获取手机SD卡存储可用空间
	 * 
	 * @return
	 */
	public static long getRealSizeOnSdcard()
	{
		File path = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath());
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}


	/**
	 * / 手机系统存储是否还有指定大小的空间
	 * 
	 * @param updateSize
	 * @return
	 */
	public static boolean enoughSpaceOnPhone(long updateSize)
	{
		return getRealSizeOnPhone() > updateSize;
	}


	/**
	 * 获取手机系统存储可用空间
	 * 
	 * @return
	 */
	public static long getRealSizeOnPhone()
	{
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		long realSize = blockSize * availableBlocks;
		return realSize;
	}


	/**
	 * 获取SD卡总体大小(单位：M) 可以使用 没插入sd卡是没有的
	 * 
	 * @return
	 */
	public String getSDCardTotalStorage()
	{
		long[] sdCardInfo = new long[2];
		String state = Environment.getExternalStorageState();
		if ("mounted".equals(state))
		{
			File sdcardDir = Environment.getExternalStorageDirectory();
			StatFs sf = new StatFs(sdcardDir.getPath());
			long bSize = sf.getBlockSize();
			long bCount = sf.getBlockCount();
			long availBlocks = sf.getAvailableBlocks();

			sdCardInfo[0] = (bSize * bCount);
		}
		if (sdCardInfo[0] / 1048576L == 0)
		{
			return "没有sd卡";
		}

		return sdCardInfo[0] / 1048576L + "MB";
	}


	/**
	 * 获取SD卡可用存储空间(单位：M) 可以使用
	 * 
	 * @return
	 */
	public String getSDCardAvailableStorage()
	{
		long[] sdCardInfo = new long[2];
		String state = Environment.getExternalStorageState();
		if ("mounted".equals(state))
		{
			File sdcardDir = Environment.getExternalStorageDirectory();
			StatFs sf = new StatFs(sdcardDir.getPath());
			long bSize = sf.getBlockSize();
			long bCount = sf.getBlockCount();
			long availBlocks = sf.getAvailableBlocks();

			sdCardInfo[1] = (bSize * availBlocks);
		}
		return sdCardInfo[1] / 1048576L + "MB";
	}

}
