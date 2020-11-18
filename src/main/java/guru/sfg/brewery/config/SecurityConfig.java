package guru.sfg.brewery.config;

import guru.sfg.brewery.security.HttpAuthFilter;
import guru.sfg.brewery.security.RestHeaderAuthFilter;
import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by jt on 6/13/20.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {

        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);

        return filter;

    }

    public HttpAuthFilter httpAuthFilter(AuthenticationManager authenticationManager) {

        HttpAuthFilter filter = new HttpAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);

        return filter;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .addFilterBefore(restHeaderAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(httpAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();

        http
                .authorizeRequests(authorize -> {
                    authorize
                            .antMatchers("/h2-console/**").permitAll()
                            .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll();
//                            .mvcMatchers("/brewery/breweries")
//                                .hasAnyRole("ADMIN", "CUSTOMER")
//                            .mvcMatchers(HttpMethod.GET, "/brewery/api/v1/breweries")
//                                .hasAnyRole("ADMIN", "CUSTOMER");
                })
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
        /* Configure access to h2-database*/
        http.headers().frameOptions().sameOrigin();

    }

    @Bean
    PasswordEncoder passwordEncoder() {

        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
////        BCryptPasswordEncoder p = (BCryptPasswordEncoder) getApplicationContext().getBean("passwordEncoder");
//        auth.inMemoryAuthentication()
//                .withUser("spring")
//                .password("{bcrypt}$2a$10$Fj6Dxe/CIlqteHRRncUlre5ZI62nqPHdQA5x8wvqTn6LJhbj/fLua")
//                .roles("ADMIN")
//                .and()
//                .withUser("user")
////                .password( passwordEncoder().encode("pass"))
//                .password("{bcrypt}$2a$10$fQL8gWtX.FnzLTtT0pddl.jKb9ZC4fViZaL0rI91eg7xn61EHikKG")
//                .roles("USER")
//                .and()
//                .withUser("scott")
//                .password("{bcrypt15}$2a$15$/9Uy9SMRHyc9YT9WQl5Da.ztcIPRfVihAJJcF2ja6R.0vqnlqbKie")
//                .roles("CUSTOMER");
//    }


//    protected UserDetailsService userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("spring")
//                .password("guru")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }


}
