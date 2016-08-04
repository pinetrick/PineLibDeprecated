package com.pine.lib.hardware.adb;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;

import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;
import com.pine.lib.hardware.adb.bean.OneProcess;
import com.pine.lib.hardware.root.Root;
import com.pine.lib.math.Vector.Vector2;

public class AdbFunc
{
	private static G g = new G(AdbFunc.class);
	private static List<OneProcess> processes = new ArrayList<OneProcess>();

	public AdbFunc()
	{
		M.i().addClass(this);
	}

	/**
	 * 获取所有正在运行的进程的信息
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public List<OneProcess> getAllProcesses()
	{
		processes.clear();

		String string = Root.i().shellRootCommand("ps").trim();
		g.d(string);
		
		// 删除第一行数据
		int pos = string.indexOf("\n") + 1;
		string = string.substring(pos);
		// 遍历每一行
		while (string.indexOf("\n") > 0)
		{
			// g.d("读取第一行数据");
			pos = string.indexOf("\n") + 1;
			String line1 = string.substring(0, pos - 1);
			string = string.substring(pos);
			g.d(line1);
			OneProcess process = getOneProcess(line1);
			processes.add(process);

		}

		

		return processes;
	}


	/**
	 * 处理一行数据
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param line1
	 * @return
	 */
	private OneProcess getOneProcess(String line1)
	{
		OneProcess oneProcess = new OneProcess();
		try
		{
			// User
			Vector2<Integer> pos0 = getFirstSpace(line1);
			oneProcess.setUser(line1.substring(0, pos0.x));
			line1 = line1.substring(pos0.y + 1);

			// PID
			pos0 = getFirstSpace(line1);
			oneProcess.setPid(line1.substring(0, pos0.x));
			line1 = line1.substring(pos0.y + 1);

			// PPID
			pos0 = getFirstSpace(line1);
			oneProcess.setPpid(line1.substring(0, pos0.x), processes);
			line1 = line1.substring(pos0.y + 1);

			// 过几个
			for (int i = 1; i <= 5; i++)
			{
				pos0 = getFirstSpace(line1);
				line1 = line1.substring(pos0.y + 1);
			}

			// PkgName
			oneProcess.setPackageName(line1);
		}
		catch (Exception e)
		{
			g.e("错误" + e.toString());
		}

		//g.d(oneProcess.toString());
		return oneProcess;
	}


	/**
	 * 获取第一个空格的位置 和 空格的长度
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param s
	 * @return 开始位置 和结束位置
	 */
	private Vector2<Integer> getFirstSpace(String s)
	{
		Vector2<Integer> vector2 = new Vector2<Integer>();

		int t = s.indexOf(" ");
		if (t != -1)
		{
			vector2.x = t;
			for (int i = t; i < s.length(); i++)
			{
				if (s.charAt(i) == ' ')
				{
					vector2.y = i;
				}
				else
				{
					break;
				}
			}
		}
		else
		{
			vector2.x = 0;
			vector2.y = 0;
		}
		return vector2;
	}


	public static AdbFunc i()
	{
		return new AdbFunc();
	}

}
