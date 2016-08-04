package com.pine.lib.func.sms;

import java.util.Date;

import com.pine.lib.func.debug.M;

public class OneSms
{
	private Date date;
	private String receiveTime;
	private String number;
	private String body;


	public OneSms()
	{
		M.i().addClass(this);
	}
	
	public Date getDate()
	{
		return date;
	}


	public void setDate(Date date)
	{
		this.date = date;
	}


	public String getReceiveTime()
	{
		return receiveTime;
	}


	public void setReceiveTime(String receiveTime)
	{
		this.receiveTime = receiveTime;
	}


	public String getNumber()
	{
		return number;
	}


	public void setNumber(String number)
	{
		this.number = number;
	}


	public String getBody()
	{
		return body;
	}


	public void setBody(String body)
	{
		this.body = body;
	}


	@Override
	public String toString()
	{
		return "OneSms [date=" + date + ", receiveTime=" + receiveTime
				+ ", number=" + number + ", body=" + body + "]";
	}

}
