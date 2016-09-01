package com.sap.mentors.lemonaid.client.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.SocialConfigurer;

@Configuration
public class SocialConfiguration {

    @Bean
    public SocialConfigurer socialConfigurerAdapter(DataSource dataSource) {
        return new DatabaseSocialConfigurer(dataSource);
    }

}