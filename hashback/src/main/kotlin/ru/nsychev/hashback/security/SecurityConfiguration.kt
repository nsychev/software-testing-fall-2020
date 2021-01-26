package ru.nsychev.hashback.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.web.cors.CorsConfiguration
import ru.nsychev.hashback.service.UserService

@Configuration
@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {
    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var jwtAuthenticationFilter: JwtAuthenticationFilter

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(this.userService);
    }

    override fun configure(http: HttpSecurity) {
        with(http) {
            cors()
                .configurationSource { CorsConfiguration().apply {
                    allowCredentials = true
                    allowedHeaders = listOf("*")
                    allowedMethods = listOf("GET", "POST")
                    allowedOriginPatterns = listOf("*")
                } }
            csrf().disable()
            sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
            exceptionHandling()
                .authenticationEntryPoint(RestAuthenticationEntryPoint())
            addFilterBefore(JwtAuthenticationFilter(), CsrfFilter::class.java)
                .csrf()
                .ignoringAntMatchers("/**")
        }
    }
}
