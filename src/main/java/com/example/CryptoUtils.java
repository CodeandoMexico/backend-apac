/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;



/**
 *
 * @author Gad
 */
public class CryptoUtils {

	public class Message {
		public byte[] Salt = null;
		public byte[] IV = null;
		public byte[] CipherText = null;
		public Message() {}
	}
	
	private static String Pwd = "esta otra es la contrasenia";
	private static int SALT_LENGTH = 32;
    private static int IV_LENGTH = 16;
    
    public byte[] deriveKey(String password, byte[] salt, int keyLen)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory kf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec specs = new PBEKeySpec(password.toCharArray(), salt, 1024, keyLen);
        SecretKey key = kf.generateSecret(specs);
        return key.getEncoded();

    }
    /*
    public byte[] encrypt(String password, byte[] plaintext)
            throws NoSuchAlgorithmException, InvalidKeySpecException,
            InvalidKeyException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException {

        byte[] salt = new byte[64];
        Random rnd = new Random();
        rnd.nextBytes(salt);
        byte[] data = deriveKey(password, salt, 192);
        SecretKey desKey = SecretKeyFactory.getInstance("DESede").generateSecret(new DESedeKeySpec(data));
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, desKey);
        return cipher.doFinal(plaintext);

    }*/
    
    public static String GenerarDigestion(String value) {
    	String digestion= null;
        try {
        	Random random = new Random(1000);
			
			byte[] salt = new byte[SALT_LENGTH];
			random.nextBytes(salt);
			
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            //md.update(salt);
            byte[] hashValue = md.digest(value.getBytes(Charset.forName("UTF-8")));
            digestion=  Base64.encodeBase64String(hashValue);
        }
        catch (NoSuchAlgorithmException e)
        {
            
        }
        
        return digestion;
    }
    public static String Encrypt (String value) {
		try
    	{
			Random random = new Random(1000);
			Message msg = new CryptoUtils().new Message();
			
			byte[] salt = new byte[SALT_LENGTH];
			byte[] IV = new byte[IV_LENGTH];
			random.nextBytes(salt);
			random.nextBytes(IV);
    		msg.Salt= salt;
    		msg.IV = IV;
    		msg.CipherText = value.getBytes(Charset.forName("UTF-8"));
    		
            SecretKeySpec key = CryptoUtils.getSecretKey(msg.Salt);
            Cipher cipher = CryptoUtils.getCipher(Cipher.ENCRYPT_MODE, key, msg.IV);
            byte[] decValue= cipher.doFinal(msg.CipherText);
            
            byte[] cifrado = new byte[ decValue.length + SALT_LENGTH + IV_LENGTH];
            System.arraycopy(msg.Salt, 0, cifrado, 0, msg.Salt.length);
            System.arraycopy(msg.IV, 0, cifrado, msg.Salt.length, msg.IV.length);
            System.arraycopy(decValue, 0, cifrado, (msg.Salt.length + msg.IV.length), decValue.length);
            return Base64.encodeBase64String(cifrado);
            
    	}
    	catch(Exception ex) {
    		return null;
    	}
	}
    
    public static String Decrypt(String value) {
    	
    	try
    	{
    		Message msg = CryptoUtils.parseValueBase64(value);
            SecretKeySpec key = CryptoUtils.getSecretKey(msg.Salt);
            Cipher cipher = CryptoUtils.getCipher(Cipher.DECRYPT_MODE, key, msg.IV);
            byte[] decValue= cipher.doFinal(msg.CipherText);
            return new String(decValue);
    	}
    	catch(Exception ex) {
    		return null;
    	}
    	
    	

    }
    private static Message parseValueBase64(String value)
    {
    	byte[] cifrado = Base64.decodeBase64(value);
		int CIPHERTEXT_LENGTH= cifrado.length - SALT_LENGTH - IV_LENGTH;
		Message m = new CryptoUtils().new Message();
		m.Salt = Arrays.copyOfRange(cifrado, 0, SALT_LENGTH);
        m.IV = Arrays.copyOfRange(cifrado, m.Salt.length, SALT_LENGTH + IV_LENGTH);
        m.CipherText= Arrays.copyOfRange(cifrado, SALT_LENGTH + IV_LENGTH, cifrado.length);
        return m;
    }
    
    private static SecretKeySpec getSecretKey(byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
    	SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(Pwd.toCharArray(), salt, 1000, 256);        
        SecretKey tmp = factory.generateSecret(spec);
        
        byte[] encoded = tmp.getEncoded();
        SecretKeySpec key = new SecretKeySpec(encoded, "AES");
        return key;
    }
    private static Cipher getCipher(int mode, SecretKeySpec key, byte[] IV ) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {
    	Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(mode ,key, new IvParameterSpec(IV));
        return cipher;
    }
}
