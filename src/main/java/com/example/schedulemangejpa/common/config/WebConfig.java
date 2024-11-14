package com.example.schedulemangejpa.common.config;

import com.example.schedulemangejpa.common.filter.LoginFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration을 사용해 필터를 전체 URL에 적용하고 필터의 순서를 설정
@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean loginFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginFilter()); // Filter 등록
        filterRegistrationBean.setOrder(1); // Filter 순서 1 설정
        filterRegistrationBean.addUrlPatterns("/*"); // 전체 URL에 Filter 적용

        return filterRegistrationBean;
    }
}
