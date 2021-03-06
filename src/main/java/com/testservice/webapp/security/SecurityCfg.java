package com.testservice.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityCfg extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetailsService")
    private final UserDetailsService userDetailsService;
    private final AuthEntryPoint authEntryPoint;
    private final JwtTokenRequestFilter jwtRequestFilter;

    public SecurityCfg(UserDetailsService userDetailsService,
                       AuthEntryPoint authEntryPoint,
                       JwtTokenRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.authEntryPoint = authEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private static final String[] COMMON_MATCHER = {
            "/user/update",
            "/vehicle/get/all",
            "/vehicle/get/{id:\\d+}",
            "/user/get/{id:\\d+}",
            "/reservation/getAll",
            "/reservation/getRes/{reservationId:\\d+}",
            "/reservation/getById/{customerId:\\d+}"
    };

    private static final String[] USER_MATCHER = {
            "/user/update",
            "/user/update/password",
            "/reservation/create",
            "/reservation/delete/{id:\\d+}",
            "/reservation/update",
            "/reservation/getReserved"

    };
    private static final String[] ADMIN_MATCHER = {
            "/auth/register",
            "/user/**",
            "/vehicle/**",
            "/reservation/approve"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/login").permitAll()
                .antMatchers(COMMON_MATCHER).hasAnyRole("CUSTOMER", "ADMIN")
                .antMatchers(USER_MATCHER).hasRole("CUSTOMER")
                .antMatchers(ADMIN_MATCHER).hasRole("ADMIN")
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(authEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
