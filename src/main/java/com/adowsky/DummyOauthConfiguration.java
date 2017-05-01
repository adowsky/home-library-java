package com.adowsky;


import com.adowsky.security.DummyOauthAuthenticationProvider;
import com.adowsky.security.TokenExtractingFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class DummyOauthConfiguration extends WebSecurityConfigurerAdapter {

    private final DummyOauthAuthenticationProvider authenticationProvider;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterAt(new TokenExtractingFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/users/**").permitAll()
                .anyRequest().authenticated();
    }
}
