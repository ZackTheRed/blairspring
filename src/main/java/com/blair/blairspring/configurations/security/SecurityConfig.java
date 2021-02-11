package com.blair.blairspring.configurations.security;

import com.blair.blairspring.configurations.RestAuthenticationSuccessHandler;
import com.blair.blairspring.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.http.HttpServletResponse;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Slf4j
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RestAuthenticationSuccessHandler authenticationSuccessHandler;

    private final SimpleUrlAuthenticationFailureHandler authenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler();

    private final AccessDeniedHandler accessDeniedHandler = (request, response, accessDeniedException) -> {
        response.getOutputStream().print("You shall not pass!");
        response.setStatus(403);
    };

    private final AuthenticationEntryPoint restAuthenticationEntryPoint = (httpServletRequest, httpServletResponse, e) ->
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.eraseCredentials(true);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                //.exceptionHandling()
                //.accessDeniedHandler(accessDeniedHandler)
                //.authenticationEntryPoint(restAuthenticationEntryPoint)

                .authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN")
                .mvcMatchers("/", "/home").permitAll()
                .mvcMatchers(HttpMethod.POST, "/users").permitAll()
                .mvcMatchers("/players/**").authenticated()
                .mvcMatchers("/test/**").permitAll()
                .mvcMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //.loginProcessingUrl("/login")
                //.successHandler(authenticationSuccessHandler)
                //.failureHandler(authenticationFailureHandler)
                .and()
                .httpBasic()
                .and()
                .logout();

        http.headers().frameOptions().disable();
        // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setUserDetailsPasswordService(userService);
        return daoAuthenticationProvider;
    }

    /*@Bean
    public ApplicationListener<AuthenticationSuccessEvent> authenticationSuccessListener() {
        return (AuthenticationSuccessEvent event) -> {
            final Authentication auth = event.getAuthentication();

            if (auth instanceof UsernamePasswordAuthenticationToken && auth.getCredentials() != null) {
                final CharSequence clearTextPass = (CharSequence) auth.getCredentials();
                final String newPasswordHash = passwordEncoder.encode(clearTextPass);
                log.info("New password hash {} for user {}", newPasswordHash, auth.getName());
                User principal = (User) auth.getPrincipal();
                principal.setPassword(newPasswordHash);
                userService.registerUser(principal);
                ((UsernamePasswordAuthenticationToken) auth).eraseCredentials();
            }
        };
    }*/

}
