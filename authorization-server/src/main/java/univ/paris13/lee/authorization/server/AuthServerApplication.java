package univ.paris13.lee.authorization.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

    @Bean
	static PasswordEncoder passwordEncoder() {
		// passwords are not stored instead we store passwords hashes to prevent passwords from being stolen
		return new BCryptPasswordEncoder();
	}

}

