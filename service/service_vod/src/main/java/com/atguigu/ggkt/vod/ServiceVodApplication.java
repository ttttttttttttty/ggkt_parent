package com.atguigu.ggkt.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName: ServiceVodApplication
 * Package: com.atguigu.ggkt
 * Description: service_vod启动类
 *
 * @Author:天宇
 * @Create：2023/7/13-17:59
 * @Version: v1.0
 */
@SpringBootApplication
@ComponentScan("com.atguigu.ggkt")
public class ServiceVodApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceVodApplication.class, args);
    }
}
