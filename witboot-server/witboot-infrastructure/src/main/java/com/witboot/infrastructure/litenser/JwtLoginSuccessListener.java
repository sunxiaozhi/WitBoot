package com.witboot.infrastructure.litenser;

import cn.hutool.core.date.DateUtil;
import com.witboot.common.core.model.UserAgentInfo;
import com.witboot.common.event.JwtLoginSuccessEvent;
import com.witboot.common.utils.BeanUtils;
import com.witboot.common.utils.UserAgentUtils;
import com.witboot.domain.loginlog.gateway.LoginLogGateway;
import com.witboot.domain.loginlog.model.LoginLogEntity;
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

        String ip = jwtLoginSuccessEvent.ip();
        String userId = String.valueOf(jwtLoginSuccessEvent.userId());
        String userName = jwtLoginSuccessEvent.username();
        String userAgent = jwtLoginSuccessEvent.userAgent();

        UserAgentInfo userAgentInfo = UserAgentUtils.parse(userAgent);
        String os = userAgentInfo.getOs();
        String device = userAgentInfo.getDevice();
        String browser = userAgentInfo.getBrowser();

        loginLogEntity.setIp(ip);
        loginLogEntity.setUserId(userId);
        loginLogEntity.setUserName(userName);
        loginLogEntity.setLocation("未知地址");
        loginLogEntity.setUserAgent(userAgent);
        loginLogEntity.setOs(os);
        loginLogEntity.setDevice(device);
        loginLogEntity.setBrowser(browser);
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
