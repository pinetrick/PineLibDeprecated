package com.pine.lib.hardware.info.Switch.gps;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

import com.pine.lib.base.activity.A;
import com.pine.lib.hardware.info.Switch.SwitchChangeInterface;

public class GPS implements SwitchChangeInterface
{

	@Override
	public Boolean isOpen()
	{
		LocationManager a = (LocationManager) A.c().getSystemService(
				Context.LOCATION_SERVICE);

		return a.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
	}


	@Override
	public Boolean openThis()
	{
		Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
		A.a().startActivityForResult(intent, 0); // 此为设置完成后返回到获取界面
		return null;
	}


	@Override
	public Boolean closeThis()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
