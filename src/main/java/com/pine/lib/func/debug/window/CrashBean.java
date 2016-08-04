package com.pine.lib.func.debug.window;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.pine.lib.base.activity.A;
import com.pine.lib.func.debug.M;
import com.pine.lib.storage.sqlite.SqlBeanExt;

public class CrashBean extends SqlBeanExt
{
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private int id;
	private String tim;
	private String detail;

	public CrashBean()
	{
		M.i().addClass(this);
	}

	public int getId()
	{
		return id;
	}


	public void setId(int id)
	{
		this.id = id;
	}


	public String getTim()
	{
		return tim;
	}


	public void setTim()
	{
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		tim = sdf.format(curDate);
		update();
	}


	public String getDetail()
	{
		return detail;
	}


	public void setDetail(String detail)
	{
		this.detail = detail;
		update();
	}


	public String getType()
	{
		String info = "";
		try
		{
			String packNameString = "at " + A.app().getPackageName();
			int i = getDetail().indexOf(packNameString);
			int j = getDetail().indexOf("\n", i);
			info = getDetail().substring(i + packNameString.length() + 1, j);
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}

		if (detail.contains("UnsupportedOperationException"))
		{
			return "不支持的操作" + " - " + info;
		}
		else if (detail.contains("IllegalArgumentException"))
		{
			return "非法 - 参数" + " - " + info;
		}
		else if (detail.contains("IndexOutOfBoundsException"))
		{
			return "下标越界" + " - " + info;
		}
		else if (detail.contains("IllegalStateException"))
		{
			return "非法 - 状态" + " - " + info;
		}
		else if (detail.contains("SQLException"))
		{
			return "数据库异常" + " - " + info;
		}
		else if (detail.contains("ClassCastException"))
		{
			return "数据类型转换异常" + " - " + info;
		}
		else if (detail.contains("NumberFormatException"))
		{
			return "字符串 转 数字异常" + " - " + info;
		}
		else if (detail.contains("NullPointerException"))
		{
			return "空指针" + " - " + info;
		}
		else if (detail.contains("ClassNotFoundException"))
		{
			return "指定的类不存在" + " - " + info;
		}
		else if (detail.contains("ArithmeticException"))
		{
			return "数学运算异常" + " - " + info;
		}
		else if (detail.contains("ArrayIndexOutOfBoundsException"))
		{
			return "数组下标越界" + " - " + info;
		}
		else if (detail.contains("IllegalArgumentException"))
		{
			return "方法的参数错误" + " - " + info;
		}
		else if (detail.contains("IllegalAccessException"))
		{
			return "没有访问权限" + " - " + info;
		}

		return "未知异常" + " - " + info;
	}

}
