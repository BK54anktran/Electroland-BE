package fpoly.electroland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ElectrolandApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectrolandApplication.class, args);
	}

}
