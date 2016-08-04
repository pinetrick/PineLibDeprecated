package com.pine.lib.func.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.pine.lib.base.activity.A;

public class RawFileReader
{
	private int rawId = 0;
	private InputStream inputStream;
	private InputStreamReader inputStreamReader = null;

	public RawFileReader(int rawId)
	{
		this.rawId = rawId;
		inputStream = A.c().getResources().openRawResource(rawId);  
		
		try
		{
			inputStreamReader = new InputStreamReader(inputStream, "gbk");
		}
		catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
		
	}
	
	public List<String> getFileContentLines()
	{
		List<String> list = new ArrayList<String>();
		

		BufferedReader reader = new BufferedReader(inputStreamReader);
		String line;
		try
		{
			
			while ((line = reader.readLine()) != null)
			{
				list.add(line);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	public String getFileContent()
	{
		
		BufferedReader reader = new BufferedReader(inputStreamReader);
		StringBuffer sb = new StringBuffer("");
		String line;
		try
		{
			while ((line = reader.readLine()) != null)
			{
				sb.append(line);
				sb.append("\n");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
}
