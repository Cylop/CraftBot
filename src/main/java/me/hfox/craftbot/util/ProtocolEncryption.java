package me.hfox.craftbot.util;

import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

public class ProtocolEncryption {

    public static SecretKey generateSecretKey() {
        CipherKeyGenerator generator = new CipherKeyGenerator();
        generator.init(new KeyGenerationParameters(new SecureRandom(), 128));
        return new SecretKeySpec(generator.generateKey(), "AES");
    }

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keypairgenerator = KeyPairGenerator.getInstance("RSA");

            keypairgenerator.initialize(1024);
            return keypairgenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static PublicKey getPublicKey(byte[] bytes) {
        try {
            X509EncodedKeySpec x509encodedkeyspec = new X509EncodedKeySpec(bytes);
            KeyFactory keyfactory = KeyFactory.getInstance("RSA");

            return keyfactory.generatePublic(x509encodedkeyspec);
        } catch (Exception ex) {
            // ignore?
        }

        return null;
    }

    public static Cipher generateCipher(int i, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");

            cipher.init(i, key, new IvParameterSpec(key.getEncoded()));
            return cipher;
        } catch (GeneralSecurityException generalsecurityexception) {
            throw new RuntimeException(generalsecurityexception);
        }
    }

    public static SecretKey getSecretKey(PrivateKey privatekey, byte[] bytes) {
        return new SecretKeySpec(toBytes(privatekey, bytes), "AES");
    }

    public static byte[] toBytes(Key key, byte[] bytes) {
        return toBytes(2, key, bytes);
    }

    public static byte[] toBytes(int method, Key key, byte[] bytes) {
        try {
            return toCypher(method, key.getAlgorithm(), key).doFinal(bytes);
        } catch (IllegalBlockSizeException | BadPaddingException illegalblocksizeexception) {
            illegalblocksizeexception.printStackTrace();
        }

        return null;
    }

    public static Cipher toCypher(int method, String s, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(s);

            cipher.init(method, key);
            return cipher;
        } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static byte[] encrypt(String string, PublicKey publicKey, SecretKey secretKey) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return hash("SHA-1", string.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded());
    }

    public static byte[] hash(String algorithm, byte[]... data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        for(byte[] section : data)
            digest.update(section);
        return digest.digest();
    }

}
