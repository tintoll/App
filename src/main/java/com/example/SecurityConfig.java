package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * Created by tinoll on 2017. 1. 9..
 */
@Configuration
@EnableWebMvcSecurity   //스프링 시큐리티에 관련된 기본적인 사항들(인증 필터등)이 설정
public class SecurityConfig extends WebSecurityConfigurerAdapter{ //상속하면 이미 설정된 기본값에 추가해야 할 값을 오버라이드해서 설정할수 있다

    @Override
     public void configure(WebSecurity web) throws Exception { //특정 요청에 대해서는 시큐리티 설정을 무시하도록 하는 등 전체에 관한 설정을 합니다

        web.ignoring().antMatchers("/webjars/**","/css/**"); //정적리소스에 접근하려면 시큐리티 설정을 무시하도록 합니다
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { //인가 로그인 , 로그아웃을 설정합니다

        http.authorizeRequests()
                .antMatchers("/loginForm").permitAll()
                .anyRequest().authenticated(); //로그인폼을 표시하는 /loginForm에 모든 사용자가 접속할수 있게 합니다 . 그외 경로는 인증없이는 접속할수 없게 합니다
        http.formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/loginForm")
                .failureUrl("/loginForm?error")
                .defaultSuccessUrl("/customers", true)
                .usernameParameter("username").passwordParameter("password")
                .and(); //폼 인증 처리를 유효화 하고 인증처리 경로 로그인 처리경로 로그인 폼 표시경로, 인증에 실패했을때 넘어갈곳 , 인증에 성공했을때 넘어갈곳,사용자 이름과 암호 관련 파라미터 이름을 설정합니다
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))  //문자열로 경로를 지정한 상황에서 로그아웃하려면 POST로 접속해야 합니다
                .logoutSuccessUrl("/loginForm"); //로그아웃 처리경로, 로그아웃이 완료됐을 때 넘어갈 곳을 설정
    }

    @Configuration
    static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter { //인증 처리에 관련된 사항들을 설정하여주는 클래스를 상속합니다

        @Autowired
        UserDetailsService userDetailsService;

        @Bean
        PasswordEncoder passwordEncoder() { //암호를 해시 형식으로 만들기 위한 클래스를 정의합니다
            return new BCryptPasswordEncoder();
        }

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); //암호를 대조할때 사용하는 PasswordEncoder를 설정합니다
        }
    }


}
