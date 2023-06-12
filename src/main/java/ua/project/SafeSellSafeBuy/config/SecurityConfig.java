package ua.project.SafeSellSafeBuy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import ua.project.SafeSellSafeBuy.services.UserDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //configure spring security + config authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/auth/login", "/user/create", "/error",
                        "/**/*.js", "/**/*.css","/**/*.jpg","/**/*.png").permitAll()
                .anyRequest().hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/product/main", true)
                .failureUrl("/auth/login?error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login");
    }


    //config authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


//     WebMvcConfigurationSupport
//    В последней версии Spring Security 6 свойство WebSecurityConfigurerAdapter устарело.
//
//        Вместо этого объявите WebSecurityCustomizer bean-компонент.
//
//@Bean
//public WebSecurityCustomizer ignoringCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("...");
//        }
//
//        Это работает для весенней безопасности 6.0.*
//
//@Bean
//public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//
//        http
//        .csrf()
//        .disable()
//        .authorizeHttpRequests()
//        .requestMatchers(
//        "/home/**",
//        "/login/**",
//        "/account/starter/**",
//        "/register/**",
//        "/plugins/**",
//        "/dist/**",
//        "/js/**",
//        "/**/favicon.ico").permitAll()
//        .and()
//        .httpBasic()
//        .and()
//        .sessionManagement()
//        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        return http.build();
//        }
//
//
//        "/plugins/**",
//        "/dist/**",
//        "/js/**",
//        ... они расположены в resources/
//
//        plugins, dist, js — это названия каталогов с ресурсами