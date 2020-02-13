package cn.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("cn.demo.mapper*")
public class DemoStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoStartApplication.class, args);
    }

}
