package io.swagger.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/login", "/css/**").permitAll() // Permetti l'accesso alla pagina di login e ai CSS
                .antMatchers("/admin/**").authenticated() // Richiedi autenticazione per tutte le pagine sotto /admin/
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
                .csrf().disable();
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
}