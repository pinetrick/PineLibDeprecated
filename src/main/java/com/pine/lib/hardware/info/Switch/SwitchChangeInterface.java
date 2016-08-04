package com.pine.lib.hardware.info.Switch;

public interface SwitchChangeInterface
{
	/**
	 * 判断当前开关开闭状态
	 * @return 
	 */
	public Boolean isOpen();
	/**
	 * 打开开关
	 * @return 
	 */
	public Boolean openThis();
	/**
	 * 关闭开关
	 * @return 
	 */
	public Boolean closeThis();
}
