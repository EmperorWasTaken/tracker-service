package kaidev.utils

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import java.nio.charset.StandardCharsets
import javax.crypto.spec.SecretKeySpec

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable() // Disabling CSRF as per your requirement
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests { authz -> authz
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/1/actuator/**").permitAll()
                .anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2 -> oauth2.jwt() }
        // Here, configure your JWT converter if needed, for example:
        // .jwtAuthenticationConverter(jwtAuthenticationConverter())

        return http.build()
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        val secretKey = "2k3ISu7FRqmg6XLp+g3ryPN7QlcFQoPlJwvbcsS9pEB44AxBQ8YUeYjiY67Nt0zxydGmvOXfGU8RORnF6C5Jug==".toByteArray(StandardCharsets.UTF_8)
        val secretKeySpec = SecretKeySpec(secretKey, "HMACSHA256")
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).build()
    }

    // Example converter configuration
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val converter = JwtAuthenticationConverter()
        // Configure your converter here
        return converter
    }

    // If you have a custom JWT filter, ensure it's correctly implemented and added
    // For the custom JWT filter, you need a filter class that extends OncePerRequestFilter or similar

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
