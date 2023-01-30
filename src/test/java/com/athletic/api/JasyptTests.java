package com.athletic.api;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

public class JasyptTests {

    @Test
    void jasypt() {
        String input = "1029a3847s5665d7483f9201";
        String input2 = "AES/CBC/PKCS5Padding";
        System.out.println("encryptText : " + jasyptEncrypt(input));
        System.out.println("encryptText2 : " + jasyptEncrypt(input2));
    }

    private String jasyptEncrypt(String input) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword("athletic");
        return encryptor.encrypt(input);
    }
}
