package com.witboot.infrastructure.filter;

import com.witboot.client.user.dto.data.ErrorCode;
import com.witboot.domain.user.gateway.UserGateway;
import com.witboot.infrastructure.common.Constants;
import com.witboot.infrastructure.common.exception.WitBootBizException;
import com.witboot.infrastructure.common.utils.JwtTokenUtil;
import com.witboot.infrastructure.common.utils.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Jwt过滤器
 *
 * @author sunxiaozhi
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserGateway userGateway;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    RedisUtil redisUtil;

    @Value("${jwt.token.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.token.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        //从客户端请求中获取JWT
        String authHeader = request.getHeader(this.tokenHeader);

        //该JWT是我们规定的格式，以tokenHead开头
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            //The part after "Bearer "
            String authToken = authHeader.substring(this.tokenHead.length());

            //从JWT中获取用户名
            String username = jwtTokenUtil.getUserNameFromToken(authToken);

            log.info("checking username:{}", username);

            //SecurityContextHolder是SpringSecurity的一个工具类
            //保存应用程序中当前使用人的安全上下文
            SecurityContext securityContext = SecurityContextHolder.getContext();

            if (username != null && securityContext.getAuthentication() == null) {
                //判断authToken是否已失效
                String jwtToken = redisUtil.getCache(Constants.LOGIN_USER_KEY + username);
                if (!StringUtils.equals(authToken, jwtToken)) {
                    throw new WitBootBizException(ErrorCode.B_USER_AUTH_TOKEN_ERROR);
                }

                //根据用户名获取登录用户信息
                UserDetails userDetails = userGateway.loadUserByUsername(username);

                //验证token是否过期
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    //将登录用户保存到安全上下文中
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    securityContext.setAuthentication(authentication);

                    log.info("authenticated user:{}", username);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
