package com.example.demo

import com.nimbusds.jwt.SignedJWT
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector
import java.text.ParseException
import java.util.*

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
  override fun configure(http: HttpSecurity) {
    http.oauth2ResourceServer().opaqueToken().introspector(object : OpaqueTokenIntrospector {
      override fun introspect(token: String?): OAuth2AuthenticatedPrincipal? {
        val jwt = try {
          SignedJWT.parse(token).jwtClaimsSet
        } catch (e: ParseException) {
          return null
        }
        return DefaultOAuth2AuthenticatedPrincipal(
          jwt.claims.mapValues { (_, v) -> if (v is Date) v.toInstant() else v },
          AuthorityUtils.NO_AUTHORITIES
        )
      }
    })
  }
}
