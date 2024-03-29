package org.antontech.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.mock;

@Configuration
@Profile("test")
public class AWSTestConfig {
    @Bean
    public AmazonS3 getAmazonS3() {
        return mock(AmazonS3.class);
    }

    @Bean
    public AmazonSimpleEmailService getAmazonSES() {
        return mock(AmazonSimpleEmailService.class);
    }
}
