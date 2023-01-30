package com.athletic.api.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

    @Value("${jasypt.encryptor.pool-size}")
    private String poolSize;

    @Value("${jasypt.encryptor.password}")
    private String password;

    @Value("${jasypt.encryptor.algorithm}")
    private String algorithm;

    @Value("${jasypt.encryptor.string-output-type}")
    private String stringOutputType;

    @Value("${jasypt.encryptor.key-obtention-iterations}")
    private String keyObtentionIterations;

    @Value("${jasypt.encryptor.salt-generator-classname}")
    private String saltGeneratorClassName;

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPoolSize(poolSize);
        config.setPassword(password);
        config.setAlgorithm(algorithm);
        config.setStringOutputType(stringOutputType);
        config.setKeyObtentionIterations(keyObtentionIterations);
        config.setSaltGeneratorClassName(saltGeneratorClassName);
        encryptor.setConfig(config);
        return encryptor;
    }
}
