package com.example.demo.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private DataSource dataSource;



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/.well-known/pki-validation/*").permitAll()
				.antMatchers("/assets/**").permitAll()
				.antMatchers("/img/**").permitAll()
				.antMatchers("/*.css").permitAll()
				.antMatchers("/register").permitAll()
				.antMatchers("/testController/**").permitAll()
				.antMatchers("/board/list").permitAll()
				.antMatchers("/sample/list").permitAll()
				.antMatchers("/login").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login") //login form 전송시 오는 곳 -> /login POST
                .defaultSuccessUrl("/sample/list")
                .failureUrl("/login?error")
				.permitAll()
				.and()
			.logout()
                .logoutSuccessUrl("/login?logout")
				.permitAll()
                .and()
            .csrf().disable();
	}

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) 
    throws Exception {
        auth.jdbcAuthentication()
        .dataSource(dataSource)
        .passwordEncoder(passwordEncoder())
        .usersByUsernameQuery("select userid, userpw, enabled "
            + "from securityAdmins "
            + "where userid = ? ")
        .authoritiesByUsernameQuery("select SA.userid, SR.role "
            + " from AdminRoles AR  "
            + " inner join securityAdmins SA"
            + " ON AR.admins_id = SA.id "
            + " inner join securityRoles SR  "
            + " ON AR.roles_id = SR.id "
            + " where SA.userid = ? ");
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




}