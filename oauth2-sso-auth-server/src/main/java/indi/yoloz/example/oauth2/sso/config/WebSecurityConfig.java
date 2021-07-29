package indi.yoloz.example.oauth2.sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author yoloz
 */

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("test").password(passwordEncoder().encode("test")).roles("user")
                .and()
                .withUser("admin").password(passwordEncoder().encode("admin")).roles("admin")
                .and()
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginProcessingUrl("/login")
                .failureForwardUrl("/failLogin")
                .loginPage("/login.html");
//        http.logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/")
//                .deleteCookies("JSESSIONID");

        http.authorizeRequests()
                .antMatchers("/login.html", "fail.html").permitAll()
                .anyRequest()
                .authenticated()
                .and().csrf().disable();// 由于使用的是JWT，我们这里不需要csrf
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
