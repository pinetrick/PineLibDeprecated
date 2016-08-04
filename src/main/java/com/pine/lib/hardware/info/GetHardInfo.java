package com.pine.lib.hardware.info;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.SystemClock;
import android.telephony.TelephonyManager;

import com.pine.lib.base.activity.A;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;

/**
 * 获取硬件信息
 * 
 * @author 黑色林海 需要权限 <uses-permission
 *         android:name="android.permission.READ_PHONE_STATE"/>
 */
public class GetHardInfo
{
	private static G g = new G(GetHardInfo.class);
	public String device_id, device_software_version, line1_number,
			network_country_iso, network_operator, network_operator_name,
			sim_country_iso, sim_operator, sim_operator_name,
			sim_serial_number, subscriber_id, voice_mail_number;
	public int network_type, phone_type, sim_state;

	public TelephonyManager tm;



	public GetHardInfo(Context context)
	{
		M.i().addClass(this);
		tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		device_id = tm.getDeviceId();//IMEI
		device_software_version = tm.getDeviceSoftwareVersion();
		line1_number = tm.getLine1Number();
		network_country_iso = tm.getNetworkCountryIso();
		network_operator = tm.getNetworkOperator();
		network_operator_name = tm.getNetworkOperatorName();
		network_type = tm.getNetworkType();
		phone_type = tm.getPhoneType();
		sim_country_iso = tm.getSimCountryIso();
		sim_operator = tm.getSimOperator();
		sim_operator_name = tm.getSimOperatorName();
		sim_serial_number = tm.getSimSerialNumber();
		sim_state = tm.getSimState();
		subscriber_id = tm.getSubscriberId();//IMSI
		voice_mail_number = tm.getVoiceMailNumber();

		toString();
		// device_id_tv.setText("设备ID：" + device_id);
		// device_software_version_tv.setText("设备软件版本号" +
		// device_software_version);
		// line1_number_tv.setText("设备电话卡号码：" + line1_number);
		// network_country_iso_tv.setText("设备的Line1Number" +
		// network_country_iso);
		// network_operator_tv.setText("当前网络提供商的数字名字（MCC+MNC的形式）："
		// + network_operator);
		// network_operator_name_tv.setText("当前网络提供商的名字（字母形式）："
		// + network_operator_name);
		// network_type_tv.setText("用于传输数据的网络的无线类型：" + network_type);
		// phone_type_tv.setText("设备用于传输语言的无线类型：" + phone_type);
		// sim_country_iso_tv.setText("设备SIM卡提供商的国家代码" + sim_country_iso);
		// sim_operator_tv.setText("设备SIM卡的提供商代码：" + sim_operator);
		// sim_operator_name_tv.setText("设备SIM卡服务商的名字：" + sim_operator_name);
		// sim_serial_number_tv.setText("设备SIM卡的编码" + sim_serial_number);
		// sim_state_tv.setText("设备SIM卡的状态：" + sim_state);
		// subscriber_id_tv.setText("国际移动用户识别码：" + subscriber_id);
		// voice_mail_number_tv.setText("设备的语音邮箱码：" + voice_mail_number);

	}


	/**
	 * 获取设备系统版本号 可以使用
	 * 
	 * @return
	 */
	public String getSystemVersionCode()
	{
		return Build.VERSION.SDK;
	}


	/**
	 * 获取设备系统版本名 可以使用
	 * 
	 * @return
	 */
	public String getSystemVersionName()
	{
		return Build.VERSION.RELEASE;
	}


	/**
	 * 获取设备名称 可以使用
	 * 
	 * @return
	 */
	public String getDeviceName()
	{
		return Build.MODEL;
	}


	/**
	 * 检测手机上的存在的所有传感器 可以使用
	 * 
	 * @return
	 */
	public String getSensor()
	{
		StringBuilder sb = new StringBuilder();

		SensorManager sm = (SensorManager) A.c()
				.getSystemService("sensor");

		List<Sensor> allSensors = sm.getSensorList(-1);

		sb.append("经检测该手机有" + allSensors.size() + "个传感器，他们分别是：\n");
		for (Sensor s : allSensors)
		{
			String tempString = "\n 设备名称：" + s.getName() + "\n" + " 设备版本："
					+ s.getVersion() + "\n" + " 供应商：" + s.getVendor() + "\n";

			switch (s.getType())
			{
				case 1:
					sb.append("传感器类型ID：" + s.getType() + " 加速度传感器accelerometer"
							+ tempString);
					break;
				case 4:
					sb.append("传感器类型ID：" + s.getType() + " 陀螺仪传感器gyroscope"
							+ tempString);
					break;
				case 5:
					sb.append("传感器类型ID：" + s.getType() + " 环境光线传感器light"
							+ tempString);
					break;
				case 2:
					sb.append("传感器类型ID：" + s.getType()
							+ " 电磁场传感器magnetic field" + tempString);
					break;
				case 3:
					sb.append("传感器类型ID：" + s.getType() + " 方向传感器orientation"
							+ tempString);
					break;
				case 6:
					sb.append("传感器类型ID：" + s.getType() + " 压力传感器pressure"
							+ tempString);
					break;
				case 8:
					sb.append("传感器类型ID：" + s.getType() + " 距离传感器proximity"
							+ tempString);
					break;
				case 7:
					sb.append("传感器类型ID：" + s.getType() + " 温度传感器temperature"
							+ tempString);
					break;
				default:
					sb.append("传感器类型ID：" + s.getType() + " 未知传感器" + tempString);
			}
		}

		return sb.toString();
	}


	/**
	 * 获取设备开机后运行时间 可以使用
	 * 
	 * @return
	 */
	public String getBootTime()
	{
		long ut = SystemClock.elapsedRealtime() / 1000L;
		if (ut == 0L)
		{
			ut = 1L;
		}
		int m = (int) (ut / 60L % 60L);
		int h = (int) (ut / 3600L);
		return h + " Hours " + m + " Minutes";
	}


	/**
	 * 获取设备当前语言 可以使用
	 * 
	 * @return
	 */
	public String getDeviceLanguage()
	{
		Locale locale = A.c().getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		return language;
	}


	public String toString()
	{

		StringBuilder sb = new StringBuilder();
		sb.append("\nDeviceId(IMEI) = " + tm.getDeviceId());
		sb.append("\nDeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion());
		sb.append("\nLine1Number = " + tm.getLine1Number());
		sb.append("\nNetworkCountryIso = " + tm.getNetworkCountryIso());
		sb.append("\nNetworkOperator = " + tm.getNetworkOperator());
		sb.append("\nNetworkOperatorName = " + tm.getNetworkOperatorName());
		sb.append("\nNetworkType = " + tm.getNetworkType());
		sb.append("\nPhoneType = " + tm.getPhoneType());
		sb.append("\nSimCountryIso = " + tm.getSimCountryIso());
		sb.append("\nSimOperator = " + tm.getSimOperator());
		sb.append("\nSimOperatorName = " + tm.getSimOperatorName());
		sb.append("\nSimSerialNumber = " + tm.getSimSerialNumber());
		sb.append("\nSimState = " + tm.getSimState());
		sb.append("\nSubscriberId(IMSI) = " + tm.getSubscriberId());
		sb.append("\nVoiceMailNumber = " + tm.getVoiceMailNumber());
		g.d(sb.toString());
		return sb.toString();
	}
}
