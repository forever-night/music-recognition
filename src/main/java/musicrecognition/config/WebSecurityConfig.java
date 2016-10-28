package musicrecognition.config;

import musicrecognition.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    DataSource dataSource;
    
    @Autowired
    UserDetailsService userDetailsService;
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/static/**", "/403").permitAll()
                .antMatchers("/login*", "/register").anonymous()
                .antMatchers(HttpMethod.POST, "/login*", "/register", "/rest/register").anonymous()
                .antMatchers("/add", "/management").hasAuthority(User.Role.ADMIN.toString())
                .antMatchers(HttpMethod.POST, "/upload?add").hasAuthority(User.Role.ADMIN.toString())
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/identify", true)
                .failureUrl("/login?error")
            .and()
                .logout()
                .logoutUrl("/logout").permitAll()
                .logoutSuccessUrl("/login?logout")
            .and()
                .csrf()
            .and()
                .exceptionHandling()
                .accessDeniedPage("/403");
    }
}
