package univ.paris13.lee.authorization.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AuthenticationManagerConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder encoder;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // this authorization server will create token only for users registered here
        // for simplicity users credentials are stored in memory, production application would store credentials in a database.
        auth.inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("foo").password(encoder.encode("foo")).roles("USER");
    }

}
