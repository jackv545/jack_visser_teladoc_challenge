package com.jackvisser.seleniumdemo.framework;

import org.springframework.beans.factory.annotation.Value;

public class TestConfig {
    @Value("${demopage.host}")
    private String demoPageHost;

    public String getDemoPageHost() {
        return demoPageHost;
    }
}
