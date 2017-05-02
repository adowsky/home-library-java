package com.adowsky;


import com.adowsky.security.DummyOauthAuthenticationProvider;
import com.adowsky.security.HomeLibraryPermissionEvaluator;
import com.adowsky.security.TokenExtractingFilter;
import com.adowsky.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DummyOauthConfiguration extends WebSecurityConfigurerAdapter {

    private final DummyOauthAuthenticationProvider authenticationProvider;
    private final PermissionService permissionService;


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

    @Override
    public void configure(WebSecurity web) throws Exception {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(new HomeLibraryPermissionEvaluator(permissionService));
        web.expressionHandler((SecurityExpressionHandler)expressionHandler);
    }

}
