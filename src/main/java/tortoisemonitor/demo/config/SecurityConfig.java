package tortoisemonitor.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final KeycloakClientRoleConverter keycloakClientRoleConverter;

    @Autowired
    public SecurityConfig(KeycloakClientRoleConverter keycloakClientRoleConverter) {
        this.keycloakClientRoleConverter = keycloakClientRoleConverter;
    }

//    @Bean
//    public SecurityFilterChain configurePaths(HttpSecurity http)
//            throws Exception {
//
//        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        http.authorizeHttpRequests(authorizeHttpRequests -> {
//            // Add Swagger and public paths to the list of permitted paths
//            authorizeHttpRequests.requestMatchers("/docs.html", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/token").permitAll();
//
//            authorizeHttpRequests.anyRequest().authenticated();
//        });
//
//        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain configurePaths(HttpSecurity http) throws Exception {

        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(authorizeHttpRequests -> {
            // Add Swagger and public paths to the list of permitted paths
            authorizeHttpRequests
                    .requestMatchers("/docs.html", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/token").permitAll()
                    // Allow "user" role to access all GET endpoints
                    .requestMatchers(HttpMethod.GET, "/activityLogs/**").hasAnyRole("user", "owner")
                    .requestMatchers(HttpMethod.GET, "/environment/**").hasAnyRole("user", "owner")
                    .requestMatchers(HttpMethod.GET, "/tortoises/**").hasAnyRole("user", "owner")
                    .requestMatchers(HttpMethod.GET, "/tortoiseHabitats/**").hasAnyRole("user", "owner")
                    // Allow "owner" role to access everything
                    .anyRequest().hasRole("owner");
        });

        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return http.build();
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(keycloakClientRoleConverter);
        return jwtConverter;
    }
}
