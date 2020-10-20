package pers.zdl1004.SchoolLeaveSystem.util;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pers.zdl1004.SchoolLeaveSystem.Messages;

public class RSAUtil {
	private static Logger logger = LogManager.getLogger(RSAUtil.class);
	private final static int keyLength = 1024;
	private static KeyPairGenerator generator;//KeyPairGenerator 类用于生成公钥和私钥对。
	private static Cipher cipher;//Cipher为密码进行加密、解密
	
	static {
		try {
			generator = KeyPairGenerator.getInstance("RSA"); //$NON-NLS-1$
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); //$NON-NLS-1$
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			logger.error(Messages.getString("RSAInitFailed"), e); //$NON-NLS-1$
			throw new Error(Messages.getString("RSAInitFailed"), e); //$NON-NLS-1$
		}
		generator.initialize(keyLength);
	}
	
	public static KeyPair genKeyPair() {
		return generator.generateKeyPair();
	}
	
	/**
	 * 
	 * @param content 需要加密的内容
	 * @param publicKey 加密用的公钥
	 * @return 加密后的密文
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	public static byte[] encrypt(byte[] content, PublicKey publicKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(content);
	}
	
	public static byte[] decrypt(byte[] encryptedContent, PrivateKey privateKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(encryptedContent);
	}
}
