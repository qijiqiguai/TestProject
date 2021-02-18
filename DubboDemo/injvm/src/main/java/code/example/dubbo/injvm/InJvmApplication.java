package code.example.dubbo.injvm;


import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author wq
 */
@ComponentScan(basePackages = {"code.example.dubbo.injvm"})
@SpringBootApplication
public class InJvmApplication {
    public static void main(String[] args) {
        SpringApplication.run(InJvmApplication.class, args);
    }

    @Configuration
    @EnableDubbo(scanBasePackages = {"code.example.dubbo.injvm","code.example.extension"})
    @PropertySource("classpath:/dubbo-provider.properties")
    static class ProviderConfiguration {
        @Bean
        public RegistryConfig registryConfig() {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setAddress("zookeeper://127.0.0.1:2181");
            return registryConfig;
        }
    }
}
