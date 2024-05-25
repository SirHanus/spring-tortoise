package tortoisemonitor.demo.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.stream.Collectors;

@Component
public class KeycloakClientRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakClientRoleConverter.class);
    private static final String ROLES = "roles";
    private static final String REALM_ACCESS = "realm_access";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> realmAccess = Optional.ofNullable(jwt.getClaimAsMap(REALM_ACCESS))
                .orElseGet(Collections::emptyMap);
        List<?> roles = Optional.ofNullable((List<?>) realmAccess.get(ROLES)).orElseGet(Collections::emptyList);

        Collection<GrantedAuthority> authorities = roles.stream()
                .map(Object::toString)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // Log the roles being extracted
        logger.info("JWT Claims: {}", jwt.getClaims());
        logger.info("Extracted roles: {}", roles);
        logger.info("Granted Authorities: {}", authorities);

        return authorities;
    }
}
