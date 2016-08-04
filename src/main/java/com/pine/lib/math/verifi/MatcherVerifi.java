package com.pine.lib.math.verifi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import android.text.TextUtils;

/**
 * 匹配验证
 * 
 * @author cyp
 * 
 */
public class MatcherVerifi {
	/**
	 * 验证手机号格式是否正确
	 * 
	 * @param mobiles
	 *            确认使用
	 * @return
	 */
	public static int isMobileNO(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		String str = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		return patternint(str, mobiles);

	}

	/**
	 * 验证邮箱是否格式正确
	 * 
	 * @param strEmail
	 *            确认使用
	 * @return
	 */
	public static int isEmail(String strEmail) {
		// String str =
		// "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		return patternint(str, strEmail);
	}

	/**
	 * 过滤特殊字符
	 * 
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String StringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 验证身份证号码
	 * 
	 * @param idCard
	 *            居民身份证号码15位或18位，最后一位可能是数字或字母
	 *               确认使用
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static int checkIdCard(String idCard) {
		String str = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
		return patternint(str, idCard);
	}


	/**
	 * 验证固定电话号码
	 * 
	 * @param phone
	 *            电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
	 *            <p>
	 *            <b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9
	 *            的一位或多位数字， 数字之后是空格分隔的国家（地区）代码。
	 *            </p>
	 *            <p>
	 *            <b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
	 *            对不使用地区或城市代码的国家（地区），则省略该组件。
	 *            </p>
	 *            <p>
	 *            <b>电话号码：</b>这包含从 0 到 9 的一个或多个数字
	 *            </p>
	 * @return 验证成功返回true，验证失败返回false
	 *   确认使用
	 */
	public static int checkPhone(String phone) {
		String str = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
		return patternint(str, phone);
	}



	/**
	 * 验证中文
	 * 确认使用
	 * @param chinese
	 *            中文字符
	 *            确认使用
	 * @return 验证成功返回true 验证失败返回false
	 */
	public static int checkChinese(String chinese) {
		String str = "^[\u4E00-\u9FA5]+$";
		return patternint(str, chinese);
	}


//	/**
//	 * 验证URL地址
//	 * 确认使用
//	 * @param url
//	 *            格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或
//	 *            http://www.csdn.net:80
//	 * @return 验证成功返回true，验证失败返回false
//	 */
//	public static boolean checkURL(String url) {
//		String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
//		return Pattern.matches(regex, url);
//	}
//
//	/**
//	 * 匹配中国邮政编码
//	 * 
//	 * @param postcode
//	 *            邮政编码
//	 * @return 验证成功返回true，验证失败返回false
//	 */
//	public static boolean checkPostcode(String postcode) {
//		String regex = "[1-9]\\d{5}";
//		return Pattern.matches(regex, postcode);
//	}
//
//	/**
//	 * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
//	 * 
//	 * @param ipAddress
//	 *            IPv4标准地址
//	 * @return 验证成功返回true，验证失败返回false
//	 */
//	public static boolean checkIpAddress(String ipAddress) {
//		String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
//		return Pattern.matches(regex, ipAddress);
//	}

	/**
	 * 抽象出的方法 用来得到验证结果 参数 0 是空 -1不匹配 1是匹配
	 * 
	 * @param pattern_str
	 *            正则
	 * @param inputcontent
	 *            输入的内容
	 * @return
	 */
	private static int patternint(String pattern_str, String inputcontent) {
		Pattern p = Pattern.compile(pattern_str);
		Matcher m = p.matcher(inputcontent);

		if (TextUtils.isEmpty(inputcontent))
			return 0;
		else if (!m.matches()) {
			return -1;
		}
		return 1;
	}

	/**
	 * 非空判断
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

}
