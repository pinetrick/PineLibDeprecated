package com.pine.lib.hardware.info.Switch.airplane;

import android.content.Intent;
import android.provider.Settings;

import com.pine.lib.base.activity.A;
import com.pine.lib.hardware.info.Switch.SwitchChangeInterface;

public class AirplaneMode implements SwitchChangeInterface
{

	@Override
	public Boolean isOpen()
	{
		int isAirplaneMode = Settings.System.getInt(
				A.c().getContentResolver(), "airplane_mode_on", 0);
		return isAirplaneMode == 1;

	}

	@Override
	public Boolean openThis()
	{
		Settings.System.putInt(A.c().getContentResolver(),
				"airplane_mode_on", (true) ? 1 : 0);
		Intent intent = new Intent("android.intent.action.AIRPLANE_MODE");
		intent.putExtra("state", true);
		A.c().sendBroadcast(intent);
		return true;
	}

	@Override
	public Boolean closeThis()
	{
		Settings.System.putInt(A.c().getContentResolver(),
				"airplane_mode_on", (false) ? 1 : 0);
		Intent intent = new Intent("android.intent.action.AIRPLANE_MODE");
		intent.putExtra("state", false);
		A.c().sendBroadcast(intent);
		return true;
	}

}
