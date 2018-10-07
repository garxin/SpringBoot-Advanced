package com.atguigu.security.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //定制请求的授权规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");

        /*
         * 开启自动配置的登录功能
         * 效果：如果没有登录或没有权限就会来到登录页面
         * 1.访问/login来到登录页
         * 2.重定向到/login?error表示登录失败
         * 3.默认POST形式的/login请求代表处理登录
         * 4.一旦定制loginPage，那么loginPage的POST请求就是登录
         */
        http.formLogin().usernameParameter("user").passwordParameter("password")
                .loginPage("/userLogin");

        /*
         * 开启自动配置的注销功能
         * 1.访问/logout表示用户注销，清空Session
         * 2.默认注销成功会返回/login?logout页面
         */
        http.logout().logoutSuccessUrl("/");//注销成功以后来到首页

        /*
         * 开启记住我功能
         * 登录成功以后，将Cookie发给浏览器保存，以后访问页面带上这个Cookie，只要通过检查就可以免登录
         * 点击注销会删除Cookie
         */
        http.rememberMe().rememberMeParameter("remember");
    }

    //定义认证规则
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder()).withUser("zhangsan").password(new BCryptPasswordEncoder().encode("123456")).roles("VIP1", "VIP2")
                .and()
                .passwordEncoder(new BCryptPasswordEncoder()).withUser("lisi").password(new BCryptPasswordEncoder().encode("123456")).roles("VIP2", "VIP3")
                .and()
                .passwordEncoder(new BCryptPasswordEncoder()).withUser("wangwu").password(new BCryptPasswordEncoder().encode("123456")).roles("VIP1", "VIP3");
    }

}
