package com.pine.lib.func.sms;

import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

import com.pine.lib.base.activity.A;

public class SendSms
{
	/**
	 * 调用系统的短信编辑发送短信
	 * 
	 * <pre>
	 * <uses-permissionandroid:name="android.permission.SEND_SMS"/>
	 * </pre>
	 * 
	 * @param number
	 * @param value
	 */
	public static void bySystem(String number, String content)
	{
		Uri uri = Uri.parse("smsto://" + number);
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra("sms_body", content);
		A.a().startActivity(intent);
	}


	/**
	 * 直接发送短信
	 * 
	 * <pre>
	 * <uses-permissionandroid:name="android.permission.SEND_SMS"/>
	 * </pre>
	 * 
	 * @param number
	 * @param value
	 */
	public static void direct(String number, String content)
	{
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(number, null, content, null, null);
	}
}
