package sura.com.co.application.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication()
@ComponentScan(basePackages = "sura.com.co")
@EnableReactiveMongoRepositories(basePackages = "sura.com.co")

public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
