package fr.eni.encheres.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class EnchereSecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // <----- temporaire
                .authorizeHttpRequests(auth -> {
            auth

                    .requestMatchers(HttpMethod.GET,"/signin").permitAll()
                    .requestMatchers(HttpMethod.POST,"/signin").permitAll()

                    .requestMatchers("/*").permitAll()
                    .requestMatchers("/css/*").permitAll()
                    .requestMatchers("/font/*").permitAll()
                    .requestMatchers("/img/*").permitAll()
                    .anyRequest().denyAll();
        });

        http.formLogin(form -> {
            form.loginPage("/login").permitAll();
//            form.defaultSuccessUrl("/");
        });

        return http.build();
    }
}
