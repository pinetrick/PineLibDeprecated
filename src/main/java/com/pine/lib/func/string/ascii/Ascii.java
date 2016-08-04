package com.pine.lib.func.string.ascii;

public class Ascii
{
	 /** * 汉字转成ASCII码 * * @param chs * @return */  
    public static int getChsAscii(String chs) {  
        int asc = 0;  
        try {  
            byte[] bytes = chs.getBytes("gb2312");  
            if (bytes == null || bytes.length > 2 || bytes.length <= 0) {  
                throw new RuntimeException("illegal resource string");  
            }  
            if (bytes.length == 1) {  
                asc = bytes[0];  
            }  
            if (bytes.length == 2) {  
                int hightByte = 256 + bytes[0];  
                int lowByte = 256 + bytes[1];  
                asc = (256 * hightByte + lowByte) - 256 * 256;  
            }  
        } catch (Exception e) {  
            System.out.println("ERROR:ChineseSpelling.class-getChsAscii(String chs)" + e);  
            return Integer.MAX_VALUE;  
        }  
        return asc;  
    }  
}
