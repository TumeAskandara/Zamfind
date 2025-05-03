package com.example.zamfind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.zamfind")
public class ZamfindApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZamfindApplication.class, args);
    }
}
