package JobPortal.SpringJobPortal.Security;

import JobPortal.SpringJobPortal.Entity.User;
import JobPortal.SpringJobPortal.Repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor


public class JwtAuthenticationFilterChain extends OncePerRequestFilter {

    public final UserRepository userRepository;
    public final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String servletPath = request.getServletPath();

        if (servletPath.startsWith("/v3/api-docs") ||
                servletPath.startsWith("/swagger-ui") ||
                servletPath.contains("swagger")) {

            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("PATH: " + request.getRequestURI());

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = authHeader.substring(7);

        String email = jwtService.extractUserName(jwtToken);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()

            );


            SecurityContextHolder.getContext().setAuthentication(authToken);
            System.out.println(
                    SecurityContextHolder.getContext()
                            .getAuthentication()
                            .getAuthorities()
            );
        }
        filterChain.doFilter(request, response);

    }


}
