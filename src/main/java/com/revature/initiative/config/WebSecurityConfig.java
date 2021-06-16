package com.revature.initiative.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.initiative.security.InMemoryRequestRepository;
import com.revature.initiative.security.JwtProvider;
import com.revature.initiative.security.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@PropertySource("application.properties")
@PropertySource(value = "secret.properties", ignoreResourceNotFound = true)
@EnableWebSecurity
@Configuration
@EnableSwagger2
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper mapper;
    private final JwtProvider jwtProvider;
    private final TokenFilter tokenFilter;

    public WebSecurityConfig(ObjectMapper mapper, JwtProvider jwtProvider, TokenFilter tokenFilter) {
        this.mapper = mapper;
        this.jwtProvider = jwtProvider;
        this.tokenFilter = tokenFilter;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/swagger-ui.html/**", "/configuration/**", "/swagger-resources/**", "/v2/api-docs","/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
        http.csrf().disable().cors().and().authorizeRequests()
                .antMatchers("/oauth2/**", "/login**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestRepository(new InMemoryRequestRepository())
                .and()
                .successHandler(this::successHandler).and()
                .exceptionHandling().authenticationEntryPoint(this::authenticationEntryPoint);
       http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    private void successHandler(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException {
        String token = jwtProvider.generateToken(authentication);
        response.getWriter().write(mapper.writeValueAsString(Collections.singletonMap("accessToken", token)));
    }

    private void authenticationEntryPoint(HttpServletRequest request, HttpServletResponse response,
                                          AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(mapper.writeValueAsString(Collections.singletonMap("error", "Unauthenticated")));
    }

    //access documentation at: http://localhost:8080/swagger-ui.html#/
    @Bean
    public Docket generateUserApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.revature.initiative"))
                .build();
    }
}