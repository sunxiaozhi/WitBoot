package com.witboot.app.user.executor.query;

import com.witboot.client.user.dto.data.UserErrorCode;
import com.witboot.client.user.dto.query.UserLoginQuery;
import com.witboot.common.utils.JwtTokenUtil;
import com.witboot.domain.user.gateway.UserGateway;
import com.witboot.domain.user.model.UserEntity;
import com.witboot.common.Constants;
import com.witboot.common.event.JwtLoginSuccessEvent;
import com.witboot.common.exception.WitBootBizException;
import com.witboot.common.utils.JakartaServletUtil;
import com.witboot.common.utils.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * UserLoginQueryExe
 *
 * @author sunxiaoizhi
 */
@Component
public class UserLoginQueryExe {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserGateway userGateway;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ApplicationEventPublisher publisher;

    public String execute(UserLoginQuery userLoginQuery) {
        UserEntity userEntity = userGateway.findPasswordInfo(userLoginQuery.getUsername());
        if (Objects.isNull(userEntity)) {
            throw new WitBootBizException(UserErrorCode.B_USER_UNDEFINED);
        }

        // 校验密码是否正确
        if (!userEntity.getPassword().isCorrect(userLoginQuery.getPassword())) {
            throw new WitBootBizException(UserErrorCode.B_USER_PASSWORD_ERROR);
        }

        String jwtToken = jwtTokenUtil.generateToken(String.valueOf(userEntity.getId()), userEntity.getUsername());

        //将jwtToken存进Redis，用于后续登录判断jwtToken的有效性
        redisUtil.setCache(Constants.LOGIN_USER_KEY + userEntity.getUsername(), jwtToken);

        HttpServletRequest request = JakartaServletUtil.getRequest();
        String ip = JakartaServletUtil.getClientIP(request);
        String userAgent = request.getHeader("User-Agent");

        // 发布登录成功事件
        publisher.publishEvent(
                new JwtLoginSuccessEvent(
                        userEntity.getId(),
                        userEntity.getUsername(),
                        ip,
                        userAgent
                )
        );

        return jwtToken;
    }
}
