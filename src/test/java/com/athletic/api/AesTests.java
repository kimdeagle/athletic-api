package com.athletic.api;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AesTests {
    @Test
    void decrypt() {
        String encText = "4WUMNJgckBI78Vuz3UC7zsSD1XbPMbnt1fpoluKcYoI/Lwp6y0SZ61oM2cUK4Q4Q";
        System.out.println("decrypt text : " + decrypt(encText));
    }

    private String decrypt(String text) {
        try {
            String key = "1029a3847s5665d7483f9201";
            String algorithm = "AES/CBC/PKCS5Padding";
            if (StringUtils.isBlank(text)) {
                return text;
            }
            Cipher cipher = Cipher.getInstance(algorithm);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
            String iv = key.substring(0, 16);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decodedBytes = Base64.getDecoder().decode(text);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("error CryptUtil.decryptAES256()");
        }
    }
}
