package ru.nsychev.hashback.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.nsychev.hashback.service.AuthService
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter : OncePerRequestFilter() {
    @Autowired
    private lateinit var authService: AuthService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            attachUser(request)
        } catch (e: Exception) {
            logger.error("Could not set user authentication", e)
        }

        filterChain.doFilter(request, response)
    }

    private fun attachUser(request: HttpServletRequest) {
        val header = request.getHeader(AUTHORIZATION)

        if (header == null || header.isEmpty() || !header.startsWith("Bearer ")) {
            return
        }

        val token = header.substring(7)

        if (authService.checkToken(token)) {

        }
    }
}
