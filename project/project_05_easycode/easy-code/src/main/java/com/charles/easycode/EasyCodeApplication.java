package com.charles.easycode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.charles.easycode.dao")
public class EasyCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyCodeApplication.class, args);
    }

}
