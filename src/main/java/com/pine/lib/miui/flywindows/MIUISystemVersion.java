package com.pine.lib.miui.flywindows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.nostra13.universalimageloader.utils.IoUtils;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.window.DebugRuntimeWindow;

public class MIUISystemVersion {
	private static G g = new G(MIUISystemVersion.class);

	/**
	 * 根据返回的是V5还是V6判断
	 * 
	 * @return
	 */
	public static int getSystemVersion() {
		String v = getSystemProperty();
		if (v.equals("UNKNOWN"))
			return -1;
		else if (v.equals("V5"))
			return 5;
		else if (v.equals("V6"))
			return 6;
		else
			return -1;
	}

	/**
	 * 根据返回的是V5还是V6判断
	 * 
	 * @return
	 */
	public static String getSystemProperty() {
		String line = null;
		BufferedReader reader = null;
		try {
			Process p = Runtime.getRuntime().exec(
					"getprop ro.miui.ui.version.name");
			reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()), 1024);
			line = reader.readLine();
			return line;
		} catch (IOException e) {
			g.e(e.toString());
		} finally {
			IoUtils.closeSilently(reader);
		}
		return "UNKNOWN";
	}

}
