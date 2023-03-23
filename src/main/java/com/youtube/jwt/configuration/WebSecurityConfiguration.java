package com.youtube.jwt.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private UserDetailsService jwtService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	// @Bean
	// public JwtRequestFilter authenticationJwtRequestFilter() {
	// 	return new JwtRequestFilter();
	// }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

       httpSecurity.cors().and().csrf().disable()
               .authorizeRequests().antMatchers("/registerNewUser", "/authenticate").permitAll()
               .antMatchers(HttpHeaders.ALLOW).permitAll()
               .anyRequest().authenticated()
               .and()
               .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
               .and()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
       
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    
    //     httpSecurity
    // .cors().and().csrf().disable()
    // .authorizeRequests()
    //     .antMatchers("/registerNewUser", "/token/authenticate").permitAll()
    //     .antMatchers(HttpHeaders.ALLOW).permitAll()
    //     .anyRequest().authenticated()
    //     .and()
    // .exceptionHandling()
    //     .authenticationEntryPoint(jwtAuthenticationEntryPoint)
    //     .and()
    // .sessionManagement()
    //     .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


// Add a filter chain that skips the authenticationJwtRequestFilter() filter for the "/registerNewUser" and "/authenticate" endpoints
// httpSecurity
//     .addFilterBefore(new Filter() {
//         @Override
//         public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//             HttpServletRequest httpRequest = (HttpServletRequest) request;
//             if ("/registerNewUser".equals(httpRequest.getRequestURI()) || "/token/authenticate".equals(httpRequest.getRequestURI())) {
//                 // skip the authenticationJwtRequestFilter() filter for these endpoints
//                 chain.doFilter(request, response);
//             } else {
//                 // execute the authenticationJwtRequestFilter() filter for all other requests
//                 authenticationJwtRequestFilter().doFilter(request, response, chain);
//             }
//         }
//     }, UsernamePasswordAuthenticationFilter.class);


    }

//     @Override
//   protected void configure(HttpSecurity http) throws Exception {
//     // @formatter:off
//     http
//         .httpBasic()
//           .disable()
//         .formLogin()
//           .disable()
//         .csrf()
//           .disable()
//         .logout()
//           .disable()  
//         .sessionManagement()
//           .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//           .and()
//         .exceptionHandling()
//         .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//           .and()
//        .addFilterBefore(authenticationJwtRequestFilter(), UsernamePasswordAuthenticationFilter.class)
//        .authorizeRequests()
//         .antMatchers("/ping**")
//         .permitAll()
//         .and()
// .authorizeRequests()
//         .anyRequest()
//         .authenticated();
//   }
    
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/authenticate");                
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(jwtService).passwordEncoder(passwordEncoder());
    }
}
