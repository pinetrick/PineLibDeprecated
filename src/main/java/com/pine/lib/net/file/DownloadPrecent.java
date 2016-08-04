package com.pine.lib.net.file;

import android.graphics.Bitmap;

public interface DownloadPrecent
{
	public void onProgressRunning(Float precent);


	public void onDownloadFinish(Bitmap bitmap);


	public void onDownloadFail(String url);
}
