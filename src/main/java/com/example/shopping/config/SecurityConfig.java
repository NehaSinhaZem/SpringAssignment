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
    @Autowired
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
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        User.UserBuilder userBuilder =User.withDefaultPasswordEncoder();
////        auth.inMemoryAuthentication()
////                .withUser(userBuilder.username("neha").password("s887").roles("USER"))
////                .withUser(userBuilder.username("tara").password("tr67").roles("SUPPLIER","USER"))
////                .withUser(userBuilder.username("mary").password("mary45").roles("ADMIN"));
//        auth.jdbcAuthentication().dataSource(dataSource);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/products/list").permitAll()
                .antMatchers("/products/**").hasAnyRole("SUPPLIER","ADMIN")
                .antMatchers("/suppliers/**").hasRole("ADMIN")
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/loginForm").loginProcessingUrl("/authenticateTheUser").defaultSuccessUrl("/home").permitAll()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/home").permitAll()
                .and().exceptionHandling().accessDeniedPage("/access-denied");
    }
}
