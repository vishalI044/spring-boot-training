package com.example.ecommerce.utils.constants;

import org.springframework.stereotype.Component;

@Component
public class Constants {
    public String REDIS_SESSION_KEY = "us:l";
    public String SESSION_ID = "session-id";
    public String ADMIN = "admin";
    public String ORDER_STATUS_EXECUTE = "execute";
    public String ORDER_STATUS_DRAFT = "draft";
}
