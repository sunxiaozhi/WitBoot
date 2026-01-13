package com.witboot.common.event;

public record JwtLoginSuccessEvent(Long userId, String username, String ip,  String userAgent) {}
