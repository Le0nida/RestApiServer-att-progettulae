package io.swagger.configuration;

import io.swagger.configuration.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Configurazione per gli endpoint che richiedono JWT
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/**").authenticated() // Esempio di endpoint che richiedono JWT
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getOutputStream().println("{ \"error\": \"Unauthorized - A valid JWT token is required\" }");
                });


        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/login", "/css/**").permitAll() // Permetti l'accesso alla pagina di login e ai CSS
                .antMatchers("/admin/**").authenticated() // Richiedi autenticazione per tutte le pagine sotto /admin/
                .anyRequest().permitAll() // Permetti tutte le altre richieste
                .and()
                .formLogin()
                .loginPage("/admin/login")
                .defaultSuccessUrl("/admin/dashboard", true)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/admin/login?error")
                .and()
                .sessionManagement()
                .invalidSessionUrl("/session-expired")
                .maximumSessions(1)
                .expiredUrl("/session-expired")
                .maxSessionsPreventsLogin(true);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        // Configurazione di un utente in memoria per scopi di test
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("ADMIN")
                .build());
        return manager;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}