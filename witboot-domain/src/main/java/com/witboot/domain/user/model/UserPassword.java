package com.witboot.domain.user.model;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ValidationException;

/**
 * 用户密码
 *
 * @author sunxiaoizhi
 */
public class UserPassword {
    /**
     * 加密后的密码
     */
    private final EncryptPassword encryptPassword;

    public UserPassword(String password) {
        if (StringUtils.isBlank(password)) {
            throw new ValidationException("密码不能为空");
        }

        // 生成密码盐和加密密码
        this.encryptPassword = new EncryptPassword(generateEncryptPassword(password));
    }

    public UserPassword(EncryptPassword encryptPassword) {
        this.encryptPassword = encryptPassword;
    }

    /**
     * 校验用户输入的密码是否正确
     *
     * @param password 密码
     * @return true-密码正确; false-密码错误
     */
    public boolean isCorrect(String password) {
        return encryptPassword.encryptPassword().equals(generateEncryptPassword(password));
    }

    /**
     * 生成加密密码
     *
     * @return 加密后的密码
     */
    private String generateEncryptPassword(String password) {
        return DigestUtils.sha3_256Hex(password);
    }

    public String getEncryptPassword() {
        return encryptPassword.encryptPassword();
    }

    /**
     * 加密后的密码
     */
    public record EncryptPassword(String encryptPassword) {
    }
}
