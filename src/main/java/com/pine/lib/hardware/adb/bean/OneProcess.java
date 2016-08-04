package com.pine.lib.hardware.adb.bean;

import java.util.List;

import com.pine.lib.func.debug.G;

public class OneProcess
{
	private static G g = new G(OneProcess.class);

	private int pid = 0;
	private String packageName = "";
	private String user = "";// 所属用户
	private OneProcess ppid = null;// 父进程ID



	public Boolean isSystemApp()
	{
		if (getPackageName().startsWith("com.android"))
		{
			return true;
		}
		if (!getPackageName().contains("."))
		{
			return true;
		}
		return false;
	}


	public int getPid()
	{
		return pid;
	}


	public void setPid(int pid)
	{
		this.pid = pid;
	}


	public void setPid(String pid) throws Exception
	{
		try
		{
			setPid(Integer.valueOf(pid));
		}
		catch (Exception e)
		{
			g.e("错误" + e.toString());
			throw e;
		}

	}


	public String getPackageName() 
	{
		if (packageName.contains(":"))
		{
			return packageName.substring(0, packageName.indexOf(":"));
		}
		return packageName;
	}


	public void setPackageName(String packageName)
	{
		this.packageName = packageName;
	}


	public String getUser()
	{
		return user;
	}


	public void setUser(String user)
	{
		this.user = user;
	}


	public OneProcess getPpid()
	{
		return ppid;
	}


	public void setPpid(String ppid, List<OneProcess> processes) throws Exception
	{
		try
		{
			int ppid1 = Integer.valueOf(ppid);
			for (OneProcess oneProcess : processes)
			{
				if (oneProcess.getPid() == ppid1)
				{
					this.ppid = oneProcess;
					break;
				}
			}
		}
		catch (Exception e)
		{
			g.e("错误" + e.toString());
			throw e;
		}

	}


	@Override
	public String toString()
	{
		return "OneProcess [pid=" + pid + ", packageName=" + packageName
				+ ", user=" + user + ", ppid=" + ppid + "]";
	}

}
