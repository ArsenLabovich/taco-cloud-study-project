package com.example.tacocloud.Configurations;

import com.example.tacocloud.Repositories.UserRepository;
import com.example.tacocloud.Security.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Transactional
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByUsername(username);
            if (user != null) return user;
            throw new UsernameNotFoundException("User ‘%s’ not found".formatted(username));
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,UserRepository userRepository,PasswordEncoder passwordEncoder) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        //.requestMatchers(HttpMethod.POST, "/api/ingredients").hasAuthority("SCOPE_writeIngredients")
                        //.requestMatchers(HttpMethod.DELETE, "/api/ingredients").hasAuthority("SCOPE_deleteIngredients")
                        //.requestMatchers(HttpMethod.POST, "/api/orders").hasAuthority("SCOPE_writeOrders")
                        //.requestMatchers(HttpMethod.DELETE, "/api/orders").hasAuthority("SCOPE_deleteOrders")
                        //.requestMatchers(HttpMethod.DELETE, "/api/ingredients").hasRole("ADMIN")
                        //.requestMatchers(HttpMethod.POST,"/api/ingredients").hasRole("ADMIN")
                        //.requestMatchers(HttpMethod.DELETE, "/api/orders").hasRole("ADMIN")
                        //.requestMatchers(HttpMethod.POST,"/api/orders").hasRole("ADMIN")
                        .requestMatchers("/design/**", "/orders/**").hasRole("USER")
                        .requestMatchers("/", "/**").permitAll()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .permitAll()
                )
               .oauth2Login(oauth2 -> oauth2
                       .loginPage("/login")
                       .defaultSuccessUrl("/design", true)
                       .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(oauth2UserService(passwordEncoder,userRepository)))
               )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .cors()
                .and()
                .build();
    }
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

        return request -> {
            OAuth2User oauth2User = delegate.loadUser(request);
            User user = new User(oauth2User.getName(), passwordEncoder.encode(oauth2User.getName()),oauth2User.getName(),"unknown","unknown","unknown","unknown","unknown");
            userRepository.save(user);
            return new DefaultOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                    oauth2User.getAttributes(),
                    "name"
            );
        };
    }

}