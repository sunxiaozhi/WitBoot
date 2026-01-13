package com.witboot.infrastructure.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Map;

/**
 * 请求日志过滤器
 *
 * @author sunxiaozhi
 */
@Slf4j
public class RequestLogFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Wrapper 封装 Request 和 Response
        ContentCachingRequestWrapper cachingRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper cachingResponse = new ContentCachingResponseWrapper(response);

        // 继续执行请求链
        chain.doFilter(cachingRequest, cachingResponse);

        /*
          在请求完成后记录请求、响应日志
         */
        // 请求方法
        String method = request.getMethod();

        // URI
        String uri = request.getRequestURI();

        // 缓存的请求体
        Map<String, String[]> parameterMap = cachingRequest.getParameterMap();
        byte[] requestContent = cachingRequest.getContentAsByteArray();

        log.info("Request => 请求方式：{} 请求链接：{} 请求参数：{} 请求体：{}", method, uri, parameterMap, new String(requestContent));

        // 响应状态
        int status = response.getStatus();

        // 缓存的响应体
        byte[] responseContent = cachingResponse.getContentAsByteArray();

        log.info("Response => 返回状态：{} 响应体：{}", status, new String(responseContent));

        // 把缓存的响应数据，响应给客户端
        cachingResponse.copyBodyToResponse();
    }
}
