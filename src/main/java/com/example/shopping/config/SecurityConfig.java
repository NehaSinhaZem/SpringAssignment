package com.example.shopping.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username,password,enabled "
                        + "from users "
                        + "where username = ?")
                .authoritiesByUsernameQuery("select username,authority "
                        + "from authorities "
                        + "where username = ?");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/products/list").permitAll()
                .antMatchers("/cart/**").hasRole("CUSTOMER")
                .antMatchers("/products/**").hasAnyRole("SUPPLIER","ADMIN")
                .antMatchers("/suppliers/**").hasRole("ADMIN")
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/loginForm").loginProcessingUrl("/authenticateTheUser").defaultSuccessUrl("/home").permitAll()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/home").permitAll()
                .and().exceptionHandling().accessDeniedPage("/access-denied");
    }
}
