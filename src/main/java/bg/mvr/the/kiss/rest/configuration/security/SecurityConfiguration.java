package bg.mvr.the.kiss.rest.configuration.security;

import bg.mvr.the.kiss.rest.configuration.security.jwt.JwtAuthenticationEntryPoint;
import bg.mvr.the.kiss.rest.configuration.security.jwt.JwtTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 21.09.2020.
 * Time: 08:48.
 * Organization: DKIS MOIA.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private CustomUserDetailsService userDetailsService;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JwtTokenVerifier jwtTokenVerifier;

    @Autowired
    public SecurityConfiguration(CustomUserDetailsService userDetailsService,JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,JwtTokenVerifier jwtTokenVerifier) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint=jwtAuthenticationEntryPoint;
        this.jwtTokenVerifier=jwtTokenVerifier;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/sign-in").permitAll()
                .antMatchers(HttpMethod.POST,"/sign-up").permitAll()
                .antMatchers(HttpMethod.POST,"/admin/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/user/**").hasAuthority("USER")
                .antMatchers(HttpMethod.PUT,"/user").hasAuthority("USER")
                .antMatchers(HttpMethod.DELETE,"/user/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/book/**").hasAuthority("USER")
                .antMatchers(HttpMethod.POST, "/book").hasAuthority("USER")
                .antMatchers(HttpMethod.PUT, "/book/**").hasAuthority("USER")
                .antMatchers(HttpMethod.PATCH, "/book/**").hasAuthority("USER")
                .antMatchers(HttpMethod.DELETE, "/book/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenVerifier, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
