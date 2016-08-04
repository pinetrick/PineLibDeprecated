package com.pine.lib.func.sms;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.pine.lib.func.broadcast.BroadCastManager;
import com.pine.lib.func.debug.M;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver
{
	public SmsReceiver()
	{
		M.i().addClass(this);

	}

	/**
	 * 别引用错了 
	 * 应该引用： android.telephony.SmsMessage;
	 * 
	 */
	@Override
	public void onReceive(Context context, Intent intent)
	{
		Bundle bundle = intent.getExtras();
		SmsMessage msg = null;
		if (null != bundle)
		{
			try
			{
				Object[] smsObj = (Object[]) bundle.get("pdus");
				for (Object object : smsObj)
				{
					msg = SmsMessage.createFromPdu((byte[]) object);
					Date date = new Date(msg.getTimestampMillis());// 时间
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String receiveTime = format.format(date);

					OneSms oneSms = new OneSms();
					oneSms.setReceiveTime(receiveTime);
					oneSms.setBody(msg.getDisplayMessageBody());
					oneSms.setDate(date);
					oneSms.setNumber(msg.getOriginatingAddress());

					BroadCastManager.i().send("收到新短信", oneSms);

				}
			}
			catch (Exception e)
			{
				// TODO: handle exception
			}

		}
	}
}
