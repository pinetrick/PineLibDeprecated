package com.pine.lib.hardware.adb;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.os.Handler;
import android.os.Message;

import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;

public class AdbShell
{

	private static G g = new G(AdbShell.class);

	private String[] cmd = null;
	private String workdirectory = "/";

	private OnShellFinishListener onShellFinishListener = null;

	private StringBuffer result = null;
	private Handler mHandler = null;



	public AdbShell()
	{
		M.i().addClass(this);
	}


	public AdbShell setOnShellFinishLisener(
			OnShellFinishListener onShellFinishListener)
	{
		this.onShellFinishListener = onShellFinishListener;
		return this;
	}


	private void beginRunInThread()
	{
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg)
			{
				if (msg.what == 1424711)
				{
					g.d("命令执行结束！");
					if (onShellFinishListener != null)
					{

						onShellFinishListener.onShellFinish(result.toString());
					}
				}
				super.handleMessage(msg);
			}
		};
		g.d("将要启动子线程执行命令");
		new MyThread().start();
	}



	private class MyThread extends Thread
	{
		public void run()
		{
			runNow(cmd, workdirectory);

			Message msg = new Message();
			msg.what = 1424711;
			mHandler.sendMessage(msg);

		}
	}



	public String runBusybox(String cmd)
	{

		try
		{
			// Executes the command.
			File file = new File(workdirectory);
			Process process = Runtime.getRuntime().exec(cmd, null, file);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			int read;
			char[] buffer = new char[4096];
			StringBuffer output = new StringBuffer();
			while ((read = reader.read(buffer)) > 0)
			{
				output.append(buffer, 0, read);
			}
			reader.close();
			// Waits for the command to finish.
			process.waitFor();
			return output.toString(); // 获得输出
		}
		catch (IOException e)
		{
			return "手机端触发IO异常";
			
		}
		catch (InterruptedException e)
		{
			return "命令被安全软件阻止";
		}

	}


	public String getWorkdirectory()
	{
		return workdirectory;
	}


	public void setWorkdirectory(String workdirectory)
	{
		this.workdirectory = workdirectory;
	}


	/**
	 * 同步运行并返回数据
	 * 
	 * <pre>
	 * 
	 * </pre>
	 */
	public synchronized String runNow(String[] cmd, String workdirectory)
	{
		while (!Thread.currentThread().isInterrupted())
		{

			result = new StringBuffer();
			try
			{
				// 创建操作系统进程（也可以由Runtime.exec()启动）
				// Runtime runtime = Runtime.getRuntime();
				// Process proc = runtime.exec(cmd);
				// InputStream inputstream = proc.getInputStream();
				ProcessBuilder builder = new ProcessBuilder(cmd);

				InputStream in = null;
				g.d("设置一个路径（绝对路径了就不一定需要）");
				if (workdirectory != null)
				{
					g.d("设置工作目录（同上）");
					builder.directory(new File(workdirectory));
					g.d("合并标准错误和标准输出");
					builder.redirectErrorStream(true);
					g.d("启动一个新进程");
					Process process = builder.start();

					in = process.getInputStream();
					byte[] re = new byte[1024];
					while (in.read(re) != -1)
					{

						String string = new String(re);
						string = string.trim();
						result = result.append(string);
					}

				}
				g.d("关闭输入流");
				if (in != null)
				{
					in.close();
				}
				return result.toString();
			}
			catch (Exception ex)
			{
				g.e(ex.toString());
				return "";
			}

		}
		return "";
	}


	/**
	 * 执行一个shell命令，并返回字符串值
	 * 
	 * @param cmd
	 *            命令名称&参数组成的数组（例如：{"/system/bin/cat", "/proc/version"}）
	 * @param workdirectory
	 *            命令执行路径（例如："system/bin/"）
	 * @return 执行结果组成的字符串
	 * @throws IOException
	 */
	public void run(String[] cmd, String workdirectory)
	{
		g.d("批量执行Shell命令");

		this.cmd = cmd;
		this.workdirectory = workdirectory;

		beginRunInThread();

	}


	public String runNow(List<String> list, String workdirectory)
	{
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			s[i] = list.get(i);
		}
		return runNow(s, workdirectory);
	}


	public void run(List<String> list, String workdirectory)
	{
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			s[i] = list.get(i);
		}
		run(s, workdirectory);
	}


	/**
	 * 执行一个shell命令，并返回字符串值
	 * 
	 * @param cmd
	 *            命令名称&参数组成的数组（例如：{"/system/bin/cat", "/proc/version"}）
	 * @param workdirectory
	 *            命令执行路径（例如："system/bin/"）
	 * @return 执行结果组成的字符串
	 * @throws IOException
	 */
	public void run(String cmd, String workdirectory)
	{
		g.d("执行单条Shell命令");
		String r = "";

		String[] s = new String[2];
		s[0] = cmd;
		s[1] = "exit";
		run(s, workdirectory);

	}

}
