package com.pine.lib.hardware.info.Pid;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;

import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;


public class Pid {
	private static G g = new G(Pid.class);
	private Activity context = null;
	
	public Pid(Activity context)
	{
		M.i().addClass(this);
		this.context = context;
	}
	
	public int getPid()
	{
		return android.os.Process.myPid(); 

	}
	
	/*
	 * 返回"开发者工具箱”
	 */
	public String getAppName()
	{
	    String processName = "";
	    ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
	    List l = am.getRunningAppProcesses();
	    Iterator i = l.iterator();
	    PackageManager pm = context.getPackageManager();
	    int pID = getPid();
	    while(i.hasNext()) 
	    {
	          ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo)(i.next());
	          try 
	          { 
	              if(info.pid == pID)
	              {
	                  CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
	                  g.d(  "Id: "+ info.pid +" ProcessName: "+ info.processName +"  Label: "+c.toString());
	                  //processName = c.toString();
	                  processName = info.processName;
	              }
	          }
	          catch(Exception e) 
	          {
	                //Log.d("Process", "Error>> :"+ e.toString());
	          }
	   }

	   return processName;
	}
}
