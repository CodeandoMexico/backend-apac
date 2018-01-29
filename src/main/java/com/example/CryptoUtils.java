/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author Gad
 */
public class CryptoUtils {

    public byte[] deriveKey(String password, byte[] salt, int keyLen)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory kf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec specs = new PBEKeySpec(password.toCharArray(), salt, 1024, keyLen);
        SecretKey key = kf.generateSecret(specs);
        return key.getEncoded();

    }

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

    }
}
