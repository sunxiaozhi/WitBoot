package com.witboot.infrastructure.common.litenser;

import cn.hutool.core.date.DateUtil;
import com.witboot.domain.loginlog.gateway.LoginLogGateway;
import com.witboot.domain.loginlog.model.LoginLogEntity;
import com.witboot.infrastructure.common.aspectj.WitLogAspect;
import com.witboot.infrastructure.common.event.JwtLoginSuccessEvent;
import com.witboot.infrastructure.common.utils.BeanUtils;
import com.witboot.infrastructure.common.utils.JakartaServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtLoginSuccessListener {

    @Async("loginLogExecutor")
    @EventListener
    public void handle(JwtLoginSuccessEvent jwtLoginSuccessEvent) {
        try {
            saveLoginLog(jwtLoginSuccessEvent);
        } catch (Exception e) {
            log.error("JWT 登录日志记录失败", e);
        }
    }

    /**
     * 初始化登录日志实体的公共属性
     */
    private LoginLogEntity initLoginLogEntity(JwtLoginSuccessEvent jwtLoginSuccessEvent) {
        LoginLogEntity loginLogEntity = new LoginLogEntity();

        loginLogEntity.setIp(jwtLoginSuccessEvent.ip());
        loginLogEntity.setUserId(String.valueOf(jwtLoginSuccessEvent.userId()));
        loginLogEntity.setUserName(jwtLoginSuccessEvent.username());
        loginLogEntity.setLocation("未知地址");
        loginLogEntity.setUserAgent(jwtLoginSuccessEvent.userAgent());
        loginLogEntity.setDeviceType("");
        loginLogEntity.setBrowser("");
        loginLogEntity.setLoginTime(DateUtil.now());

        return loginLogEntity;
    }

    /**
     * 保存登录日志
     */
    private void saveLoginLog(JwtLoginSuccessEvent jwtLoginSuccessEvent) {
        try {
            LoginLogEntity loginLogEntity = initLoginLogEntity(jwtLoginSuccessEvent);

            LoginLogGateway loginLogGateway = BeanUtils.getBean(LoginLogGateway.class);
            loginLogGateway.save(loginLogEntity);
        } catch (Exception e) {
            log.error("保存登录日志异常: {}", e.getMessage(), e);
        }
    }
}
