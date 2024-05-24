package tortoisemonitor.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String OWNER = "owner";
    public static final String USER = "user";
    private final JwtConverter jwtConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz
                // Swagger UI and API docs
                .requestMatchers(HttpMethod.GET, "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/docs.html").permitAll()

                // DELETE endpoints - only accessible by OWNER
                .requestMatchers(HttpMethod.DELETE, "/tortoiseHabitats/**").hasRole(OWNER)
                .requestMatchers(HttpMethod.DELETE, "/activityLogs/**").hasRole(OWNER)
                .requestMatchers(HttpMethod.DELETE, "/environment/**").hasRole(OWNER)
                .requestMatchers(HttpMethod.DELETE, "/tortoises/**").hasRole(OWNER)

                // Statistics endpoints - only accessible by OWNER
                .requestMatchers(HttpMethod.GET, "/statistics/**").permitAll()

                // Other endpoints - accessible by USER
                .requestMatchers(HttpMethod.GET, "/tortoiseHabitats/**").hasRole(USER)
                .requestMatchers(HttpMethod.PUT, "/tortoiseHabitats/**").hasRole(USER)
                .requestMatchers(HttpMethod.POST, "/tortoiseHabitats/**").hasRole(USER)
                .requestMatchers(HttpMethod.GET, "/activityLogs/**").hasRole(USER)
                .requestMatchers(HttpMethod.PUT, "/activityLogs/**").hasRole(USER)
                .requestMatchers(HttpMethod.POST, "/activityLogs/**").hasRole(USER)
                .requestMatchers(HttpMethod.GET, "/environment/**").hasRole(USER)
                .requestMatchers(HttpMethod.PUT, "/environment/**").hasRole(USER)
                .requestMatchers(HttpMethod.POST, "/environment/**").hasRole(USER)
                .requestMatchers(HttpMethod.GET, "/tortoises/**").hasRole(USER)
                .requestMatchers(HttpMethod.PUT, "/tortoises/**").hasRole(USER)
                .requestMatchers(HttpMethod.POST, "/tortoises/**").hasRole(USER)

                // Any other requests require authentication
                .anyRequest().authenticated()
        );

        http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)));

        return http.build();
    }
}
