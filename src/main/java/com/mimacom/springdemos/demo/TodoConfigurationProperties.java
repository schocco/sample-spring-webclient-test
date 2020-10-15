package com.mimacom.springdemos.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;
import java.net.URL;

@ConfigurationProperties("todo")
public class TodoConfigurationProperties {

    private URL baseUrl;

    public URL getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(URL baseUrl) {
        this.baseUrl = baseUrl;
    }
}
