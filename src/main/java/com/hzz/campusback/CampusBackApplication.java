package com.hzz.campusback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com/hzz/campusback/mapper")
public class CampusBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusBackApplication.class, args);
    }

}
