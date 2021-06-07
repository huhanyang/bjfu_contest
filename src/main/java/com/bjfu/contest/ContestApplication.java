package com.bjfu.contest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring Boot启动配置类
 * @author warthog
 */
@EnableJpaAuditing
@EnableJpaRepositories
@SpringBootApplication
public class ContestApplication {

    // 分页参数修改
    // todo 注册时所选的学院/专业/年级/班级 可配置化

    public static void main(String[] args) {
        SpringApplication.run(ContestApplication.class, args);
    }

}
