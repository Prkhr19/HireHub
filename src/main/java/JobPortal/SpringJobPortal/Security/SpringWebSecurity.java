package JobPortal.SpringJobPortal.Security;

import JobPortal.SpringJobPortal.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringWebSecurity {
    private final UserRepository userRepository;
    private final JwtAuthenticationFilterChain jwtAuthenticationFilterChain;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .addFilterBefore(jwtAuthenticationFilterChain, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET, "/**").permitAll()

                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers( "/jobs/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/recruiter/**").hasAnyRole("RECRUITER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/candidate/**").hasRole("CANDIDATE")
                        .anyRequest().authenticated())
                .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                        //.addFilterBefore(jwtAuthenticationFilterChain, UsernamePasswordAuthenticationFilter.class);

        return http.build();


    }

}
