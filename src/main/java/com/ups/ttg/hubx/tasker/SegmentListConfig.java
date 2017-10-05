package com.ups.ttg.hubx.tasker;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "segment")
public class SegmentListConfig {

    private List<String> list;

    SegmentListConfig() {
        this.list = new ArrayList<>();
    }

    public List<String> getList() {
        return this.list;
    }
}