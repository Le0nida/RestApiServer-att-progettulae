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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SimpleRedirectInvalidSessionStrategy;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Configurazione per gli endpoint che richiedono JWT
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/**").authenticated()
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
                .anyRequest().permitAll()
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
                .accessDeniedPage("/admin/login?error");

        // Configurazione di gestione della sessione
        http
                .sessionManagement()
                .invalidSessionUrl("/session-invalid")
                .maximumSessions(1)
                .expiredUrl("/session-expired")
                .maxSessionsPreventsLogin(true)
                .and()
                .sessionFixation().migrateSession();

        // Configurazione della scadenza della sessione
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .sessionManagement()
                .sessionFixation().none()
                .invalidSessionStrategy(new SimpleRedirectInvalidSessionStrategy("/session-invalid"))
                .maximumSessions(1)
                .expiredUrl("/session-expired")
                .maxSessionsPreventsLogin(true);

        // Configurazione di default per altre richieste
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}