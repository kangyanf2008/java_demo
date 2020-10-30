package AES;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {
    public final static String ENCRYPT_PASSWORD = "pensees@20201029";

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String ch = ENCRYPT_PASSWORD + "_" + "bbb";
        AES aes = SecureUtil.aes(ENCRYPT_PASSWORD.getBytes());
        //System.out.println("==========|" + aes.encryptBase64(ch));

        System.out.println(MD5Utils.stringToMD5(aes.encryptBase64(ch)));
        System.out.println("======" + MD5Utils.stringToMD5(aes.encryptBase64(ch)).substring(0, 20));
       //dd(ch);

    }

    public static void dd(String ch) {
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, ENCRYPT_PASSWORD.getBytes());
        //加密
        byte[] encrypt = aes.encrypt(ch);
        System.out.println(new String(Base64.encode(encrypt)));
//解密
        byte[] decrypt = aes.decrypt(encrypt);
//加密为16进制表示
        String encryptHex = aes.encryptHex(ch);
//解密为字符串
        String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println(decryptStr);
    }
}


class MD5Utils {
    public static String stringToMD5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

}
