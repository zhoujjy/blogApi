package com.zj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//@ServletComponentScan
public class BlogUseApp {
    public static void main(String[] args) {
        SpringApplication.run(BlogUseApp.class,args);
    }
}
