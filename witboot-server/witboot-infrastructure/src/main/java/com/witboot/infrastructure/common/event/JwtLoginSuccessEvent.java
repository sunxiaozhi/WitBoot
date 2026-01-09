package com.witboot.infrastructure.common.event;

public record JwtLoginSuccessEvent(Long userId, String username, String ip,  String userAgent) {}
