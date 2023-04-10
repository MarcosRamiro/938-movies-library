package tech.ada.java.movieslibrary.api.system;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import feign.Response;
import feign.Response.Body;
import tech.ada.java.movieslibrary.api.auth.TokenBlocklistRepository;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenBlocklistRepository tokenBlocklistRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String token = header.substring(7);
        this.authenticateUserFromToken(request, token);
        filterChain.doFilter(request, response);
    }

    private void authenticateUserFromToken(HttpServletRequest request, String token) {
        final String username = this.jwtService.extractUsername(token);

        if(this.isTokenInBlockList(token)){
            return;
        }
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.jwtService.isTokenValid(token, userDetails)){
                this.setAuthTokenOnSecurityContext(request, userDetails);
            }
        }
    }

    private boolean isTokenInBlockList(String token) {
        return tokenBlocklistRepository.existsByToken(token);
    }

    private void setAuthTokenOnSecurityContext(HttpServletRequest request, UserDetails userDetails) {
        var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }


}
