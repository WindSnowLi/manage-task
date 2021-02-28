package com.windsnowli.workserver.utils;

import com.aliyun.oss.common.utils.BinaryUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

/**
 * @author windSnowLi
 */
public class CodeUtils {
    final static int RANDOM_PASSWORD_LENGTH = 8;

    /**
     * 验证RSA
     *
     * @param content   内容
     * @param sign      签名
     * @param publicKey 公开密钥
     * @return 验证状态
     */
    public static boolean rsaCheck(String content, byte[] sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes());
            return signature.verify(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取随机字符串
     *
     * @param length 长度
     * @return 随机字符串
     */
    public static String getRandomString(final int length) {
        StringBuilder rs = new StringBuilder();
        Random rd = new Random();
        final String table = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890.+";
        for (int i = 0; i < length; i++) {
            rs.append(table.charAt(rd.nextInt(64) % table.length()));
        }
        return rs.toString();
    }

    /**
     * 获取随机密码
     *
     * @return 随机密码
     */
    public static String getRandomPassword() {
        return getRandomString(RANDOM_PASSWORD_LENGTH);
    }

    /**
     * 获取字符串SHA521
     *
     * @param content 内容
     * @return SHA512
     */
    public static String strWithSha512(final String content) {
        try {
            //创建SHA512类型的加密对象
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(content.getBytes());
            byte[] bytes = messageDigest.digest();
            StringBuilder strHexString = new StringBuilder();
            for (byte aByte : bytes) {
                String hex = Integer.toHexString(0xff & aByte);
                if (hex.length() == 1) {
                    strHexString.append('0');
                }
                strHexString.append(hex);
            }
            return strHexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取字符串SHA521
     *
     * @param content 内容
     * @return SHA512
     */
    public static String strWithSha1(final String content) {
        try {
            if (content == null) {
                return null;
            }

            final MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            final byte[] digests = messageDigest.digest(content.getBytes());

            final StringBuilder stringBuilder = new StringBuilder();
            for (byte digest : digests) {
                int halfbyte = (digest >>> 4) & 0x0F;
                for (int j = 0; j <= 1; j++) {
                    stringBuilder.append(
                            halfbyte <= 9
                                    ? (char) ('0' + halfbyte)
                                    : (char) ('a' + halfbyte - 10));
                    halfbyte = digest & 0x0F;
                }
            }

            return stringBuilder.toString();
        } catch (final Throwable throwable) {
            return null;
        }
    }

    public static String strWithMd5(final String content) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(content.getBytes(StandardCharsets.UTF_8));
            byte[] hexBuff = md5.digest();
            return hexToStr(hexBuff);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getFileMd5(File file) {
        FileInputStream fileInputStream = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            return hexToStr(md5.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 十六进制转字符串
     *
     * @param hexStr 十六进制数组
     * @return 字符串
     */
    private static String hexToStr(byte[] hexStr) {
        StringBuilder result = new StringBuilder();
        for (byte b : hexStr) {
            result.append(Integer.toHexString((0x000000FF & b) | 0xFFFFFF00).substring(6));
        }
        return result.toString();
    }


    /**
     * 密码加密方式
     *
     * @param content 内容
     * @param salt    盐
     * @return 加密结果
     */
    public static String getSha512ToMd5(String content, String salt) {
        return strWithMd5(strWithSha512(content + salt));
    }
}
