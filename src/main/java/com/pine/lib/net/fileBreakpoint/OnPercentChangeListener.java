package com.pine.lib.net.fileBreakpoint;

public interface OnPercentChangeListener
{
	public void fileSize(int size);


	public void percentChange(int downloadSize);


	public void onDownloadErr();
}
