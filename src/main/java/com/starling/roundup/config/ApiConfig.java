package com.starling.roundup.config;

public class ApiConfig {
    private final String baseUrl;
    private final String accessToken;

    public ApiConfig(String baseUrl, String accessToken) {
        this.baseUrl = baseUrl;
        this.accessToken = accessToken;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }
}