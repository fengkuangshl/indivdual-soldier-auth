package com.key.win.rsa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

public class RSAEncryptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) throws Exception {

        String privateKeyPath = "/Users/Love/Desktop/RSA_KEYS/rsa_public_key.pem";        // replace your public key path here
        String publicKeyPath = "/Users/Love/Desktop/RSA_KEYS/pkcs8_private_key.pem";     // replace your private path here
        RSAEncryptor rsaEncryptor = new RSAEncryptor(privateKeyPath, publicKeyPath);

        try {

            String test = "JAVA";
            String testRSAEnWith64 = rsaEncryptor.encryptWithBase64(test);
            String testRSADeWith64 = rsaEncryptor.decryptWithBase64(testRSAEnWith64);
            System.out.println("\nEncrypt: \n" + testRSAEnWith64);
            System.out.println("\nDecrypt: \n" + testRSADeWith64);

            // NSLog the encrypt string from Xcode , and paste it here.
            // 请粘贴来自IOS端加密后的字符串
//            String rsaBase46StringFromIOS =
//                    "nIIV7fVsHe8QquUbciMYbbumoMtbBuLsCr2yMB/WAhm+S/kGRPlf+k2GH8imZIYQ" + "\r" +
//                    "QBDssVLQmS392QlxS87hnwMRJIzWw6vdRv/k79TgTfu6tI/9QTqIOvNlQIqtIcVm" + "\r" +
//                    "R/suvydoymKgdlB+ce5/tHSxfqEOLLrL1Zl2PqJSP4A=";
//
//            String decryptStringFromIOS = rsaEncryptor.decryptWithBase64(rsaBase46StringFromIOS);
//            System.out.println("Decrypt result from ios client: \n" + decryptStringFromIOS);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * @param publicKeyFilePath
     * @param privateKeyFilePath
     */
    public RSAEncryptor(String publicKeyFilePath, String privateKeyFilePath) throws Exception {
        String public_key = getKeyFromFile(publicKeyFilePath);
        String private_key = getKeyFromFile(privateKeyFilePath);
        loadPrivateKey(private_key);
        loadPublicKey(public_key);
    }

    public RSAEncryptor() {
        // load the PublicKey and PrivateKey manually
    }


    public String getKeyFromFile(String filePath) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

        String line = null;
        List<String> list = new ArrayList<String>();
        while ((line = bufferedReader.readLine()) != null) {
            list.add(line);
        }

        // remove the firt line and last line
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < list.size() - 1; i++) {
            stringBuilder.append(list.get(i)).append("\r");
        }

        String key = stringBuilder.toString();
        return key;
    }

    public String decryptWithBase64(String base64String) throws Exception {
        //  http://commons.apache.org/proper/commons-codec/ : org.apache.commons.codec.binary.Base64
        // sun.misc.BASE64Decoder
        byte[] binaryData = decrypt(getPrivateKey(), new BASE64Decoder().decodeBuffer(base64String) /*org.apache.commons.codec.binary.Base64.decodeBase64(base46String.getBytes())*/);
        String string = new String(binaryData);
        return string;
    }

    public String encryptWithBase64(String string) throws Exception {
        //  http://commons.apache.org/proper/commons-codec/ : org.apache.commons.codec.binary.Base64
        // sun.misc.BASE64Encoder
        byte[] binaryData = encrypt(getPublicKey(), string.getBytes());
        String base64String = new BASE64Encoder().encodeBuffer(binaryData) /* org.apache.commons.codec.binary.Base64.encodeBase64(binaryData) */;
        return base64String;
    }


    // convenient properties
    public static RSAEncryptor sharedInstance = null;

    public static void setSharedInstance(RSAEncryptor rsaEncryptor) {
        sharedInstance = rsaEncryptor;
    }


    // From: http://blog.csdn.net/chaijunkun/article/details/7275632

    /**
     * 私钥
     */
    private RSAPrivateKey privateKey;

    /**
     * 公钥
     */
    private RSAPublicKey publicKey;

    /**
     * 获取私钥
     *
     * @return 当前的私钥对象
     */
    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * 获取公钥
     *
     * @return 当前的公钥对象
     */
    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * 随机生成密钥对
     */
    public void genKeyPair() {
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException:" + e.getMessage(), e);
            //e.printStackTrace();
        }
        keyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param in 公钥输入流
     * @throws Exception 加载公钥时产生的异常
     */
    public void loadPublicKey(InputStream in) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            loadPublicKey(sb.toString());
        } catch (IOException e) {
            logger.error("公钥数据流读取错误:" + e.getMessage(), e);
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            logger.error("公钥输入流为空:" + e.getMessage(), e);
            throw new Exception("公钥输入流为空");
        }
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public void loadPublicKey(String publicKeyStr) throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            this.publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            logger.error("无此算法:" + e.getMessage(), e);
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            logger.error("公钥非法:" + e.getMessage(), e);
            throw new Exception("公钥非法");
        } catch (IOException e) {
            logger.error("公钥数据内容读取错误:" + e.getMessage(), e);
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            logger.error("公钥数据为空:" + e.getMessage(), e);
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 从文件中加载私钥
     *
     * @param in 私钥文件名
     * @return 是否成功
     * @throws Exception
     */
    public void loadPrivateKey(InputStream in) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            loadPrivateKey(sb.toString());
        } catch (IOException e) {
            logger.error("私钥数据读取错误:" + e.getMessage(), e);
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            logger.error("私钥输入流为空:" + e.getMessage(), e);
            throw new Exception("私钥输入流为空");
        }
    }

    public void loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            logger.error("无此算法:" + e.getMessage(), e);
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            logger.error("私钥非法:" + e.getMessage(), e);
            throw new Exception("私钥非法");
        } catch (IOException e) {
            logger.error("私钥数据内容读取错误:" + e.getMessage(), e);
            throw new Exception("私钥数据内容读取错误");
        } catch (NullPointerException e) {
            logger.error("私钥数据为空:" + e.getMessage(), e);
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 加密过程
     *
     * @param publicKey     公钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
        if (publicKey == null) {
            logger.error("加密公钥为空, 请设置");
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA");//, new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            logger.error("无此加密算法:" + e.getMessage(), e);
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            logger.error("加密公钥非法,请检查:" + e.getMessage(), e);
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            logger.error("明文长度非法:" + e.getMessage(), e);
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            logger.error("明文数据已损坏:" + e.getMessage(), e);
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 解密过程
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
        if (privateKey == null) {
            logger.error("解密私钥为空, 请设置");
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA");//, new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            logger.error("无此解密算法:" + e.getMessage(), e);
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            logger.error("NoSuchPaddingException:" + e.getMessage(), e);
            return null;
        } catch (InvalidKeyException e) {
            logger.error("解密私钥非法,请检查:" + e.getMessage(), e);
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            logger.error("密文长度非法:" + e.getMessage(), e);
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            logger.error("密文数据已损坏:" + e.getMessage(), e);
            throw new Exception("密文数据已损坏");
        }
    }


    /**
     * 字节数据转字符串专用集合
     */
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 字节数据转十六进制字符串
     *
     * @param data 输入数据
     * @return 十六进制内容
     */
    public static String byteArrayToString(byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            //取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移 
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
            //取出字节的低四位 作为索引得到相应的十六进制标识符 
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
            if (i < data.length - 1) {
                stringBuilder.append(' ');
            }
        }
        return stringBuilder.toString();
    }
}