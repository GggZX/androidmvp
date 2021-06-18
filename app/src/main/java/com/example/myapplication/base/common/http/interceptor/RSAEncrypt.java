package com.example.myapplication.base.common.http.interceptor;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAEncrypt {

    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCs0EZw9DH06IgqBgSDnjdG6piinJ8moa2EbmacLnCECYAnAzM+LupTNXVtA1cN44yQSuLRMnt8+BZ7YTRO8hZV1XZ3I/Ih1HRfxeKzU7BPwEytz3UpQmtIwqGB63G9YwfBwXO85cvKgkFAfirFXIbE3fuqZvSiV7QELT4O0nIqsQIDAQAB";


    public void initKey() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);

        long time = System.currentTimeMillis();

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

//        System.out.println("publicKey:" + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
//        System.out.println("privateKey:" + Base64.getEncoder().encodeToString(privateKey.getEncoded()));

//        System.out.println("execute time:" + (System.currentTimeMillis() - time));
    }

    public String encryptByPrivateKey(String privateKey, String text) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);
        byte[] bytes = cipher.doFinal(text.getBytes("utf-8"));
        return Base64.getEncoder().encodeToString(bytes);
    }

    public String encryptByPublicKey(String publicKey, String text) {
        int isEncrypt =1;
        //早期登陆注册3个借口加密方式（北京上海都打开）
        if (false) {
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
                RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);

                Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
                cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
                byte[] bytes = cipher.doFinal(text.getBytes("utf-8"));
                return Base64.getEncoder().encodeToString(bytes);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        } else {
            return text;
        }
    }

    public String decryptByPrivateKey(String privateKey, String text) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(text));
        return new String(bytes);
    }

    public String decryptByPublicKey(String publicKey, String text) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
        byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(text));
        return new String(bytes);
    }


    public static void main(String[] args) throws Exception {
        RSAEncrypt rsaTest = new RSAEncrypt();
        rsaTest.initKey();

        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKzQRnD0MfToiCoGBIOeN0bqmKKcnyahrYRuZpwucIQJgCcDMz4u6lM1dW0DVw3jjJBK4tEye3z4FnthNE7yFlXVdncj8iHUdF/F4rNTsE/ATK3PdSlCa0jCoYHrcb1jB8HBc7zly8qCQUB+KsVchsTd+6pm9KJXtAQtPg7SciqxAgMBAAECgYAfAScCMO2dkPKNB5NbwdENCh3kXtE27x7geaYzMynApqu1VB+ncmdcEmVE76p4tXCMmsLi4XnCTAhMjn4CA/D5nbbqtlC+ihhnRMnuSHXGKFxenZhqwRglr18mtitQm2hNJHZyptjrGeWbviG1957J/qJLdJ22XhDK9m4f7ESM2QJBAOBsCECyxJSRxSVbJJGCUVtt+tP4rXb261PvWKgLxrW41WLT8HirzXBRSg8AqcKHxiKiamQ83hij37EPI3Ni/+sCQQDFIT3JLQ6LuD+t9XECRsHdIRVwHs+hENkcNC8oGFmNMiv400mtMFGo73KsW1rX21cYyh3DugJu5L/es5fBuLTTAkB6Xo3VcWS9OgrMhrkW2n8wInTtliBHyPWia9TktJ8iQWmhHL13nv5DKx/9tDfciZohEnx+sa3Ms7ZqXj3PUgEHAkEArKFfqj1674B6w6ydOpEcVLoUPAu/aB2JA9nvMf2g7rVxLIZbjv8xg++tTKLz+vRqYZseVbkfSF2qaSx9SXinYQJACEbamOTKNUEwZLEqNkZ7UQ9MyktazZeRnvGOjNiTPXaKaXCTfPeZXty85g5zjg8QRBj6rEibncJgQZoqSP33BA==";

        String text = "This is a test words, include some CN words, ex:中文&&*中";

//        System.out.println("私钥加密，公钥解密");

//        String mb = rsaTest.encryptByPrivateKey(privateKey, text);
//        String mv = rsaTest.decryptByPublicKey(publicKey, mb);

//        System.out.println(mb);
//        System.out.println(mv);

//        System.out.println("======================================================");

//        System.out.println("公钥加密，私钥解密");
//        String mb1 = rsaTest.encryptByPublicKey(publicKey, "13699126865");
//        String mv1 = rsaTest.decryptByPrivateKey(privateKey, "ep4dziPPcGqJ28YCC0mnoxhiD3UepGBLU9oZB1/f6TY/36ETl/MQ12PWMK4GxWA4SQ3G1wQp7btEaD35SlWPkd8hSAdtqBydCBngIVobMUcwmg53PBWXQLSmCu0QOJnAhgOCiHNkcGvFq0WK1w70tpQoQT4UQ1dYmOXtWDTrfwQ=");
//        System.out.println(mb1);
//        System.out.println(mv1);
    }
}
