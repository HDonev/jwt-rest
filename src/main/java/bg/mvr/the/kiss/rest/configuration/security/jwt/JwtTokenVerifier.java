package bg.mvr.the.kiss.rest.configuration.security.jwt;

import bg.mvr.the.kiss.rest.entities.User;
import bg.mvr.the.kiss.rest.repositories.UserRepository;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 07.10.2020.
 * Time: 14:12.
 * Organization: DKIS MOIA.
 */
@Component
public class JwtTokenVerifier extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private UserRepository userRepository;

    public JwtTokenVerifier(JwtUtils jwtUtils, UserRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        try {
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
                String jwtToken = requestTokenHeader.substring(7);
                String email = jwtUtils.getEmailFromToken(jwtToken);

                if (email != null && jwtToken != null) {

                    User user = userRepository.getUserEntityByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

                    if (jwtUtils.validateToken(jwtToken, user.getUsername())) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }
        } catch (JwtException e) {
            logger.warn("Invalid token: " + e.getMessage());
        } catch (RuntimeException e) {
            logger.warn("Invalid token: " + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
