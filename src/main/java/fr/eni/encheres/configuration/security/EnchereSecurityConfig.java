package fr.eni.encheres.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class EnchereSecurityConfig {

    private final String SELECT_USER = """
            SELECT pseudo, mot_de_passe, 1 FROM UTILISATEURS WHERE pseudo = :pseudo
            """;


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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
