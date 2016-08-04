package com.pine.lib.math.rsa;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;

import com.pine.lib.func.debug.G;
/**
 * 功能并未完成
 * <pre>
 * 
 * </pre>
 */
public class Rsa
{
	private static G g = new G(Rsa.class);
	private RSAPublicKey myPublicKey;
	private RSAPrivateKey myPrivateKey;
	private String serverPublicKey_exponent;// 服务器公钥指数
	private String modulus;// 模
	private String public_exponent; // 公钥指数
	private String private_exponent; // 私钥指数








	public String getModulus()
	{
		return modulus;
	}




	public String getPublicExponent()
	{
		return myPublicKey.getPublicExponent().toString();
	}




	public Rsa()
	{
		try
		{
			HashMap<String, Object> map = RSAUtils.getKeys();
			// 生成公钥和私钥
			myPublicKey = (RSAPublicKey) map.get("public");
			myPrivateKey = (RSAPrivateKey) map.get("private");

			
			// 模
			modulus = myPublicKey.getModulus().toString();
			// 公钥指数
			public_exponent = myPublicKey.getPublicExponent().toString();
			// 私钥指数
			private_exponent = myPrivateKey.getPrivateExponent().toString();
			g.d("公钥" + myPublicKey.toString());
			g.d("模" + modulus);
			g.d("公钥指数" + public_exponent);
			g.d("私钥指数" + private_exponent);
			g.d("公私密钥生成成功");
		}
		catch (Exception e)
		{
			g.e("加密函数产生异常" + e.toString());
		}
	}


	/**
	 * 加密函数
	 * 
	 * @param plainText
	 *            明文
	 * @return
	 */
	public String encryption(String plainText)
	{

		// 使用模和指数生成公钥和私钥
		RSAPublicKey pubKey = RSAUtils.getPublicKey(modulus, serverPublicKey_exponent);

		// 加密后的密文
		String mi = "";
		try
		{
			mi = RSAUtils.encryptByPublicKey(plainText, pubKey);
			g.d("加密结束，密文" + mi);
		}
		catch (Exception e)
		{
			g.e("加密异常" + e.toString());

		}

		return mi;

	}


	/**
	 * 解密函数
	 * 
	 * @param cipherText
	 *            密文
	 * @return
	 */
	public String deciphering(String cipherText)
	{

		RSAPrivateKey priKey = RSAUtils
				.getPrivateKey(modulus, private_exponent);
		// 解密后的明文
		String ming = "";
		try
		{
			ming = RSAUtils.decryptByPrivateKey(cipherText, priKey);
			g.d("解密结束，明文" + ming);
		}
		catch (Exception e)
		{
			g.e("解密异常" + e.toString());
		}

		return ming;
	}

}
