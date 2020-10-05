package code.example.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author wq
 */
@ComponentScan(basePackages = {"code.example.consumer"})
@SpringBootApplication
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Configuration
    @EnableDubbo(scanBasePackages = {"code.example.consumer.impl"})
    @PropertySource("classpath:/spring/dubbo-consumer.properties")
    @ComponentScan(value = {"code.example.consumer.impl"})
    static class ConsumerConfiguration {

    }
}
