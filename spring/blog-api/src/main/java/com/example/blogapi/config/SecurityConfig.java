package com.example.blogapi.config;

import com.example.blogapi.security.CustomUserDetailsService;
import com.example.blogapi.security.JwtAuthenticationEntryPoint;
import com.example.blogapi.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity

public class SecurityConfig {

    private UserDetailsService userDetailsService;
    private JwtAuthenticationFilter authenticationFilter;


    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    private CustomUserDetailsService customUserDetailsService;

    @Autowired(required = true)
    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthenticationFilter authenticationFilter,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          CustomUserDetailsService customUserDetailsService) {
        this.userDetailsService = userDetailsService;
        this.authenticationFilter = authenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.customUserDetailsService = customUserDetailsService;
    }


    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeHttpRequests((requests) ->
                        {
                            try {
                                requests.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                                        .requestMatchers("/api/auth/**")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated()
                                        .and()
                                        .httpBasic();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                ).exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin();
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();

    }


//    @Bean
//
//    protected UserDetailsService userDetailsService() {
//
//        UserDetails userDetails = User.builder().username("has").password(passwordEncoder().encode("pas")).roles("USER").build();
//        UserDetails adminDetails = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(userDetails, adminDetails);
//
//    }


}
