package zerobase.reserve.security;

import com.zerobase.shopping.security.JwtAuthenticationFilter;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter authenticationFilter;



  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
        .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
        .requestMatchers(new AntPathRequestMatcher("/**/signup"))
        .requestMatchers(new AntPathRequestMatcher("/**/signin"))
        .requestMatchers(new AntPathRequestMatcher("/reservation/**"));
  }
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
    http.authorizeHttpRequests(request -> request
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector,"/**")).permitAll()
                .requestMatchers( "/**/signup", "/**/signin").permitAll()
                .anyRequest().authenticated()
        )
        .httpBasic(Customizer.withDefaults())
        .addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }


}
