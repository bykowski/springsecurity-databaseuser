package pl.bykowski.springsecuritydatabaseuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private AppUserRepo appUserRepo;
    private UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(AppUserRepo appUserRepo,
                             UserDetailsService userDetailsService) {
        this.appUserRepo = appUserRepo;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/test1").authenticated()
                .antMatchers("/test2").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin().permitAll();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        AppUser appUser = new AppUser();
        appUser.setUsername("Admin");
        appUser.setPassword(getPasswordEncoder().encode("Admin"));
        appUserRepo.save(appUser);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
