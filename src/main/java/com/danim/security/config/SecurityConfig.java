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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomAuthenticationProvider authProvider;

    @Autowired
    public SecurityConfig(CustomAuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }

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
        http.authorizeRequests()//보호된 리소스 URI에 접근할 수 있는 권한을 설정
                .antMatchers("/member/**").hasRole("ROLE_MEMBER")
                .antMatchers("/shop/checkout").hasRole("ROLE_MEMBER")
                .antMatchers("/admin/**").hasRole("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
                .and().csrf()//csrf 보안 설정을 비활성화
                .disable()//해당 기능을 사용하기 위해서는 프론트단에서 csrf토큰값 보내줘야함
                .addFilter(jwtAuthenticationFilter())//Form Login에 사용되는 custom AuthenticationFilter 구현체를 등록
                .addFilter(jwtAuthorizationFilter())//Header 인증에 사용되는 BasicAuthenticationFilter 구현체를 등록
                .exceptionHandling()
                .accessDeniedPage("/accessDenied")
                .authenticationEntryPoint(authenticationEntryPoint())
        ;
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

    /*
     * Form Login시 걸리는 Filter bean register
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");
        jwtAuthenticationFilter.setUsernameParameter("username");
        jwtAuthenticationFilter.setPasswordParameter("password");

        jwtAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        jwtAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());

        jwtAuthenticationFilter.afterPropertiesSet();

        return jwtAuthenticationFilter;
    }

    /*
     * Filter bean register
     */
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
        JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(authenticationManager());
        return jwtAuthorizationFilter;
    }
}
