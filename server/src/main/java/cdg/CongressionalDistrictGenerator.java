package cdg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class CongressionalDistrictGenerator {

	public static void main(String[] args) {
		SpringApplication.run(CongressionalDistrictGenerator.class, args);
	}
}
