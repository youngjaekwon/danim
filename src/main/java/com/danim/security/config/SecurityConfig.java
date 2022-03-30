package com.danim.security.config;

import com.danim.security.handler.CustomAuthenticationFailureHandler;
import com.danim.security.handler.CustomAuthenticationSuccessHandler;
import com.danim.security.handler.CustomLogoutSuccessHandler;
import com.danim.security.provider.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .csrf().disable() // csrf를 사용할지 여부
                    .authorizeRequests() // HttpServletRequest에 따라접근을 제한
                    .antMatchers("/member/**").hasRole("MEMBER")
                    .antMatchers("/shop/checkout").hasRole("MEMBER")
                    .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                    .formLogin() // form 기반 로그인 인증 관련하며 HttpSession 이용
                    .loginPage("/accessDenied") // 지정하고 싶은 로그인 페이지 url
                    .loginProcessingUrl("/login")
                    .usernameParameter("id") // 지정하고 싶은 username name 명칭이며, 기본은 username
                    .passwordParameter("password") // 지정하고 싶은 password name 명칭이며, 기본은 password
                    .defaultSuccessUrl("/") // 로그인 성공 시 이동페이지
                    .successHandler(authenticationSuccessHandler())
                    .failureHandler(authenticationFailureHandler())
                    .permitAll() // 모두 접근 허용
                .and()
                    .logout() // 로그아웃
                    .logoutRequestMatcher(new AntPathRequestMatcher(("/member/doLogout"))) // 지정하고 싶은 로그아웃 url
                    .logoutSuccessUrl("/?logout=true") // 성공 시 이동 페이지
                    .logoutSuccessHandler(logoutSuccessHandler())
                    .invalidateHttpSession(true); // 세션 초기화
//                .and()
//                    .exceptionHandling() // 에러 처리
//                    .accessDeniedPage("/error"); // 에러 시 이동할 페이지
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        CustomAuthenticationSuccessHandler successHandler = new CustomAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl("/");
        return successHandler;
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        CustomAuthenticationFailureHandler failureHandler = new CustomAuthenticationFailureHandler();
        failureHandler.setDefaultFailureUrl("/?loginCheck=failed");
        return failureHandler;
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        CustomLogoutSuccessHandler logoutSuccessHandler = new CustomLogoutSuccessHandler();
        logoutSuccessHandler.setDefaultTargetUrl("/?logout=true");
        return logoutSuccessHandler;
    }
}
