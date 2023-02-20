package io.maxi.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@MapperScan(basePackages = { "io.maxi.api.dao" })
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class MaxiSpringBootApplication {


	//设置时区 相差8小时
//	@PostConstruct
//	void started() {
//		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//	}


	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MaxiSpringBootApplication.class);
		app.addListeners(new ApplicationPidFileWriter());
		app.run(args);

	}

}
