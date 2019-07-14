package cn.cai.microservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"cn.cai.microservice.dao"})
public class SpringbootRedisCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRedisCacheApplication.class, args);
	}

}
