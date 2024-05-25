package tortoisemonitor.demo.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @GetMapping("/token")
    public String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
            return jwt.getTokenValue();
        }
        return "No JWT token found";
    }
}
