package indi.yoloz.example.httpformLogin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author yoloz
 * <p>
 * spring security的登陆失败的地址如果你不设置，那么他就会用你的 loginPage 地址并拼接一个error，像我上面配的地址登陆失败后的URL为 login.html?error 。
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 第一部分是formLogin配置段，用于配置登录验证逻辑相关的信息。如：登录页面、登录成功页面、登录请求处理路径等。
     * 第二部分是authorizeRequests配置端，用于配置资源的访问权限。如：登录页面的permitAll开放访问，“/biz1”（业务一页面资源）需要有资源ID为"biz1"的用户才可以访问。
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //表单认证
        http.formLogin()
                .usernameParameter("username")///登录表单form中用户名输入框input的name名，不修改的话默认是username
                .passwordParameter("password")//form中密码输入框input的name名，不修改的话默认是password
                //当发现login时认为是登录需要执行我们自定义的登录逻辑 >里面的url是登录页面表单的提交地址
                .loginProcessingUrl("/login")
                //登录成功后请求地址 请求方法必须是post的 这里是跳转控制器
                .successForwardUrl("/index")
                //登录失败后请求访问的地址 >这里访问的是控制器
                .failureForwardUrl("/failLogin")
                //设置登录页面
                .loginPage("/login.html");
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID");
        //url拦截认证  >所有请求都必须被认证 必须登录后才可以访问
        http.authorizeRequests()
                //设置不需要拦截的页面
                .antMatchers("/login.html").permitAll()
                .antMatchers("/fail.html").permitAll()
                .antMatchers("/biz1").hasAnyAuthority("biz1")  //前面是资源的访问路径、后面是资源的名称或者叫资源ID
                //所有请求都必须被认真,必须登录后才能访问
                .anyRequest().authenticated();

        //关闭csrf防护 >只有关闭了,来自表单的请求
        http.csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("test").password(passwordEncoder().encode("test")).authorities("biz1", "biz2")
                .and()
                .withUser("sys").password(passwordEncoder().encode("sys")).authorities("biz1", "biz2", "syslog", "sysuser")
                .and()
                .passwordEncoder(passwordEncoder());//配置BCrypt加密
    }

    /**
     * 登录页面login.html和控制层Controller登录验证'/login'开放
     * 除此之外，一些静态资源如css、js文件通常也都不需要验证权限，也需要将它们的访问权限开放出来
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/config/**", "/css/**", "/fonts/**", "/img/**", "/js/**");
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
