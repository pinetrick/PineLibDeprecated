package com.pine.lib.hardware.root;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.pine.lib.base.activity.A;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;
import com.pine.lib.hardware.adb.AdbFunc;
import com.pine.lib.hardware.adb.bean.OneProcess;
import com.pine.lib.view.fasttoast.T;

public class Root
{
	private static G g = new G(Root.class);
	
	public Root()
	{
		M.i().addClass(this);
	}
	/**
	 * 如果此设备已Root，则申请Root授权，否则无提示 可以使用 - 只是申请
	 */
	public void ApplyRootAuthorize()
	{
		try
		{
			Runtime.getRuntime().exec("su").getOutputStream();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * 执行root命令 可以使用
	 */
	public String shellRootCommand(List<String> command)
	{
		StringBuilder outputString = new StringBuilder();
		Process process = null;
		DataOutputStream os = null;
		try
		{
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			for (String string : command)
			{
				os.writeBytes(string + "\n");
			}
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();

			InputStream in = process.getInputStream();
			byte[] re = new byte[1024];
			while (in.read(re) != -1)
			{
				String string = new String(re);
				//string = string.trim();
				outputString = outputString.append(string);
			}

		}
		catch (Exception e)
		{
			Log.d("*** DEBUG ***", "ROOT REE" + e.getMessage());
			return "Run command error";
		}
		finally
		{
			try
			{
				if (os != null)
				{
					os.close();
				}
				process.destroy();
			}
			catch (Exception e)
			{
			}
		}
		// Log.d("*** DEBUG ***", "Root SUC ");
		return outputString.toString();
	}


	/**
	 * 检测设备是否有Root 可以使用
	 * 
	 * @return
	 */
	public boolean isRoot()
	{
		boolean bool = false;
		try
		{
			if ((!new File("/system/bin/su").exists())
					&& (!new File("/system/xbin/su").exists())) bool = false;
			else bool = true;
		}
		catch (Exception localException)
		{
		}
		return bool;
	}


	public String shellRootCommand(String string)
	{
		List<String> list = new ArrayList<String>();
		list.add(string);
		return shellRootCommand(list);
	}
	/**
	 * 杀进程的 需要ROOT权限
	 * <pre>
	 * 返回被干掉的进程数量
	 * </pre>
	 */
	public int killAllProcess()
	{
		List<String> cmd = new ArrayList<String>();
		List<OneProcess> processes = AdbFunc.i().getAllProcesses();
		for (OneProcess oneProcess : processes)
		{
			if (!oneProcess.isSystemApp())
			{
				if (!oneProcess.getPackageName().equals(
						A.c().getPackageName()))
				{
					g.d("杀掉进程(" + oneProcess.getPid() + ")"
							+ oneProcess.getPackageName());
					cmd.add("kill " + oneProcess.getPid());

				}
			}
		}
		Root.i().shellRootCommand(cmd);
		
		return cmd.size();
	}


	public static Root i()
	{
		return new Root();
	}

}
