package com.pine.lib.func.string.filter;

import java.util.regex.Pattern;

import android.text.InputFilter;

import com.pine.lib.func.debug.M;
import com.pine.lib.func.string.ascii.Ascii;

public class StringFilter
{
	private String s;
	private int length = -1;
	/**
	 * 允许数字
	 */
	private Boolean enableNumber = true;
	/**
	 * 允许大写字母
	 */
	private Boolean enableBigLetter = true;
	/**
	 * 允许小写字母
	 */
	private Boolean enableLittleLetter = true;
	/**
	 * 允许汉字
	 */
	private Boolean enableChinese = true;
	/**
	 * 允许特殊符号 - 仅限ASCII可接受
	 */
	private Boolean enableFlag = true;
	/**
	 * 允许笑脸
	 */
	private Boolean enableSmail = true;
	/**
	 * 允许其他
	 */
	private Boolean enableOther = true;



	public StringFilter(String s)
	{
		M.i().addClass(this);
		this.s = s;
	}


	/**
	 * 过滤数字
	 */
	private Boolean isNumber(String str)
	{
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}


	private Boolean isNumber(char chr)
	{
		if (chr < 48 || chr > 57) return false;
		return true;
	}


	private Boolean isBigLetter(char chr)
	{
		if (chr >= 65 && chr <= 90) return true;
		return false;
	}


	private Boolean isLittleLetter(char chr)
	{
		if (chr >= 97 && chr <= 122) return true;
		return false;
	}


	private Boolean isChinese(char chr)
	{
		int ascii = Ascii.getChsAscii(String.valueOf(chr));
		if (-20319 <= ascii && ascii <= -10254) return true;
		if (ascii <= -1) return true;
		return false;
	}


	private Boolean isFlag(char chr)
	{
		if (32 <= chr && chr <= 47) return true;
		if (58 <= chr && chr <= 64) return true;
		if (91 <= chr && chr <= 96) return true;
		if (123 <= chr && chr <= 126) return true;
		return false;
	}


	private Boolean isSmail(char chr)
	{
		if (Ascii.getChsAscii(String.valueOf(chr)) == 63) return true;
		// Pattern emoji =
		// Pattern.compile("[\u3f00-\u3fff]",Pattern.UNICODE_CASE |
		// Pattern.CASE_INSENSITIVE);
		// if (emoji.matcher(String.valueOf(chr)).find()) return true;
		return false;
	}


	private Boolean isOther(char chr)
	{
		if (isNumber(chr)) return false;
		if (isBigLetter(chr)) return false;
		if (isLittleLetter(chr)) return false;
		if (isChinese(chr)) return false;
		if (isFlag(chr)) return false;
		if (isSmail(chr)) return false;

		return true;
	}


	/**
	 * 切割掉第i个字符 下标从0开始
	 * 
	 * @param i
	 * @return
	 */
	private String stringCut(int i)
	{
		StringBuilder sb = new StringBuilder();

		sb.append(s.substring(0, i));
		sb.append(s.substring(i + 1, s.length()));

		return sb.toString();
	}


	public String doFilter()
	{
		for (int i = 0; i < s.length(); i++)
		{
			if ((!enableNumber) && (isNumber(s.charAt(i))))
			{
				s = stringCut(i);
				i--;
				continue;
			}
			if ((!enableBigLetter) && (isBigLetter(s.charAt(i))))
			{
				s = stringCut(i);
				i--;
				continue;
			}
			if ((!enableLittleLetter) && (isLittleLetter(s.charAt(i))))
			{
				s = stringCut(i);
				i--;
				continue;
			}
			if ((!enableChinese) && (isChinese(s.charAt(i))))
			{
				s = stringCut(i);
				i--;
				continue;
			}
			if ((!enableFlag) && (isFlag(s.charAt(i))))
			{
				s = stringCut(i);
				i--;
				continue;
			}
			if ((!enableSmail) && (s.length() > i + 1)
					&& (isSmail(s.charAt(i)) && (isSmail(s.charAt(i + 1)))))
			{
				s = stringCut(i);
				s = stringCut(i);
				i -= 1;
				continue;
			}
			if ((!enableOther) && (isOther(s.charAt(i))))
			{
				s = stringCut(i);
				i--;
				continue;
			}
		}

		return s;
	}


	public int getLength()
	{
		return length;
	}


	public StringFilter setLength(int length)
	{
		this.length = length;
		return this;
	}


	public Boolean getEnableNumber()
	{
		return enableNumber;
	}


	public StringFilter setEnableNumber(Boolean enableNumber)
	{
		this.enableNumber = enableNumber;
		return this;
	}


	public Boolean getEnableBigLetter()
	{
		return enableBigLetter;
	}


	public StringFilter setEnableBigLetter(Boolean enableBigLetter)
	{
		this.enableBigLetter = enableBigLetter;
		return this;
	}


	public Boolean getEnableLittleLetter()
	{
		return enableLittleLetter;
	}


	public StringFilter setEnableLittleLetter(Boolean enableLittleLetter)
	{
		this.enableLittleLetter = enableLittleLetter;
		return this;
	}


	public Boolean getEnableSmail()
	{
		return enableSmail;
	}


	public StringFilter setEnableSmail(Boolean enableSmail)
	{

		this.enableSmail = enableSmail;
		return this;
	}


	public Boolean getEnableChinese()
	{
		return enableChinese;
	}


	public StringFilter setEnableChinese(Boolean enableChinese)
	{
		this.enableChinese = enableChinese;
		return this;
	}


	public Boolean getEnableFlag()
	{
		return enableFlag;
	}


	public StringFilter setEnableFlag(Boolean enableFlag)
	{
		this.enableFlag = enableFlag;
		return this;
	}


	public Boolean getEnableOther()
	{
		return enableOther;
	}


	public StringFilter setEnableOther(Boolean enableOther)
	{
		this.enableOther = enableOther;
		return this;
	}

}
