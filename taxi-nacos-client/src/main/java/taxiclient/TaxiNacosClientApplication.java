package taxiclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class TaxiNacosClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxiNacosClientApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("nacos项目启动完成！");
            // 在这里可以执行其他的启动任务
        };
    }

}
