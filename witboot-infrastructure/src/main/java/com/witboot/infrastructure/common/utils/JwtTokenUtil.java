package com.witboot.infrastructure.common.utils;

import com.witboot.domain.user.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken生成的工具类
 *
 * @author sunxiaozhi
 */
@Slf4j
@Component
public class JwtTokenUtil {
    private static final String CLAIM_KEY_USERNAME = "username";

    private static final String CLAIM_KEY_CREATED = "created";

    private static final String CLAIM_KEY_USER_ID = "userId";

    /**
     * 令牌自定义标识
     */
    @Value("${jwt.token.tokenHeader}")
    private String tokenHeader;

    /**
     * 令牌秘钥
     */
    @Value("${jwt.token.secret}")
    private String secret;

    /**
     * 令牌有效期（默认7天），也可将token的过期时间交给redis管理
     */
    @Value("${jwt.token.expireTime}")
    private Long expiration;

    /**
     * 加密key
     */
    public SecretKey generateKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    /**
     * 根据负责生成JWT的token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(generateKey())
                .compact();
    }

    /**
     * 从token中获取JWT中的负载
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(generateKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.info("JWT格式验证失败:{}", token);
        }
        return claims;
    }

    /**
     * 生成token的过期时间,返回Date
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从token中获取登录用户名
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.get("username").toString();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     */
    public boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return !expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 根据用户信息生成token
     */
    public String generateToken(UserEntity userEntity) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_USERNAME, userEntity.getUsername());
        claims.put(CLAIM_KEY_USER_ID, userEntity.getId());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 判断token是否可以被刷新
     */
    public boolean canRefresh(String token) {
        return isTokenExpired(token);
    }

    /**
     * 刷新token
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 获取请求token
     *
     * @param request request
     * @return token
     */
    public String getToken(HttpServletRequest request) {
        return request.getHeader(tokenHeader);
    }
}
