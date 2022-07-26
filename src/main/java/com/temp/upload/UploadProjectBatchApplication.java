package com.temp.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class UploadProjectBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(UploadProjectBatchApplication.class, args);
    }

}
