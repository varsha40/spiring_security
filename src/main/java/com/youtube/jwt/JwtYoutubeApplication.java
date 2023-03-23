package com.youtube.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.youtube.jwt")
public class JwtYoutubeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtYoutubeApplication.class, args);
    }

}
