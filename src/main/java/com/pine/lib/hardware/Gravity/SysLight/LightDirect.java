package com.pine.lib.hardware.Gravity.SysLight;

public enum LightDirect {
	/**
	 * 横屏
	 */
	HENG,
	/**
	 * 竖屏
	 */
	SHU,
	/**
	 * 跟随系统
	 */
	SYS,
	/**
	 * 未指定，此为默认值，由Android系统自己选择适当的方向，不懂
	 */
	UNKNOW,
	/**
	 * 锁定当前屏幕不变 不受重力感应影响
	 */
	LOCK,
	/**
	 * 用户当前的首选方向 不懂
	 */
	USER
}
