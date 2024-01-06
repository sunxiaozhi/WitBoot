package com.witboot.infrastructure.filter;

import com.witboot.domain.user.gateway.UserGateway;
import com.witboot.infrastructure.common.utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                //根据用户名获取登录用户信息
                UserDetails userDetails = this.userGateway.loadUserByUsername(username);

                //验证token是否过期
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    //将登录用户保存到安全上下文中
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    log.info("authenticated user:{}", username);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
