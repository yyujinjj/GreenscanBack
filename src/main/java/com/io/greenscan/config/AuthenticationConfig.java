//package com.io.greenscan.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class AuthenticationConfig {
//
//
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .httpBasic().disable()
//                .csrf().disable()
//                .cors().and()
//                .headers().frameOptions().sameOrigin().and()
//                .authorizeRequests()
//                .antMatchers("/h2-console/**").permitAll()
//                .antMatchers("/api/v1/members/login").permitAll()
//                .antMatchers("/api/v1/members/join").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/v1/**").authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilterBefore(new JwtFilter(secretKey), UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//
//
//
//}
