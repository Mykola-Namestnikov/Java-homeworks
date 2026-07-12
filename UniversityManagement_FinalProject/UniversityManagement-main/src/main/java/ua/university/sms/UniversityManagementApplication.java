package ua.university.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "ua.university.sms")
@EnableJpaRepositories(basePackages = "ua.university.sms.repository")
@EntityScan(basePackages = "ua.university.sms.model.entity")
public class UniversityManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(UniversityManagementApplication.class, args);
    }
}
