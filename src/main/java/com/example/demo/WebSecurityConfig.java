package com.example.demo;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.oauth2ResourceServer().opaqueToken().introspector(new OpaqueTokenIntrospector() {
      @Override
      public OAuth2AuthenticatedPrincipal introspect(String token) {
        JWTClaimsSet jwt;
        try {
          jwt = SignedJWT.parse(token).getJWTClaimsSet();
        } catch (ParseException e) {
          return null;
        }
        return new DefaultOAuth2AuthenticatedPrincipal(
            jwt.getClaims(),
            AuthorityUtils.NO_AUTHORITIES
        );
      }
    });
  }
}
