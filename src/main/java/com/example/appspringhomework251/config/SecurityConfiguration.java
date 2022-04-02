package com.example.appspringhomework251.config;

import com.example.appspringhomework251.entity.enums.RoleName;
import com.example.appspringhomework251.repository.RoleRepository;
import com.example.appspringhomework251.security.JwtFilter;
import com.example.appspringhomework251.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Properties;

@Configuration
@EnableWebSecurity
@EnableJpaAuditing
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthService authService;
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    RoleRepository roleRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/**").hasAnyAuthority(RoleName.DIRECTOR.name(),RoleName.USER.name(),RoleName.HR_MANAGER.name())
                .antMatchers(HttpMethod.PUT,"/api/task/*").hasAnyAuthority(RoleName.DIRECTOR.name(),RoleName.USER.name(),RoleName.HR_MANAGER.name())
                .antMatchers(HttpMethod.POST,"/api/turnstile/*").hasAnyAuthority(RoleName.DIRECTOR.name(),RoleName.USER.name(),RoleName.HR_MANAGER.name())
                .antMatchers(HttpMethod.PUT,"/api/turnstile/*").hasAnyAuthority(RoleName.DIRECTOR.name(),RoleName.USER.name(),RoleName.HR_MANAGER.name())
                .antMatchers(HttpMethod.POST,"/api/**").hasAnyAuthority(RoleName.DIRECTOR.name(),RoleName.HR_MANAGER.name())
                .antMatchers(HttpMethod.PUT,"/api/**").hasAnyAuthority(RoleName.HR_MANAGER.name(),RoleName.DIRECTOR.name())
                .antMatchers("/api/**").hasAuthority(RoleName.DIRECTOR.name())
                .anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(authService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setPort(587);
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setUsername("abdurasulabduraimov22@gmail.com");
        javaMailSender.setPassword("abdurasul29052002");
        Properties properties = javaMailSender.getJavaMailProperties();
        properties.setProperty("mail.transport.protocol","smtp");
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.starttls.enable","true");
        properties.setProperty("mail.debug","true");
        return javaMailSender;
    }
}
