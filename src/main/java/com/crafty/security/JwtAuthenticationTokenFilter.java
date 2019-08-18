package com.crafty.security;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.crafty.entity.User;
import com.crafty.repository.UserRepository;
import com.crafty.web.RequestData;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = request.getHeader(RequestData.AUTHORIZATION) != null ? request.getHeader(RequestData.AUTHORIZATION) : request.getHeader(RequestData.SWAGGER_AUTHORIZATION);

        if (authToken != null && !authToken.isEmpty()) {
            try {
                RequestData.setAuthorizationToken(authToken);

                JwtClaims claims = jwtTokenUtil.parseToken(authToken);
                RequestData.setClaims(claims);

                JwtUser jwtUser = JwtUserFactory.create(
                    claims.getLoggedInUserId(),
                    claims.getSubject(),
                    claims.getRoles(),
                    claims.getMemberId(),
                    claims.getLastLogoutDate() != null ? Instant.parse(claims.getLastLogoutDate()) : null
                );

                log.info("Checking authentication for {}", jwtUser);
                Optional<User> userOptional = userRepository.findById(claims.getLoggedInUserId());
                if (jwtTokenUtil.validateToken(authToken, jwtUser, userOptional.get().getLastLogoutDate()) && claims.getScope().equals(JwtScopeConstants.ACCESS_TOKEN)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("Authenticated {}, setting security context", jwtUser);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                log.error("Error while checking user authentication.", e);
            }
        } 
//        else if (systemToken != null && systemToken.equals(applicationProperties.getProperty("system.auth.token"))) {
//            try {
//                // Save the system auth token in the request data.
//                RequestData.setSystemAuthorizationToken(systemToken);
//
//                Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//                authorities.add(new SimpleGrantedAuthority("ROLE_SYSTEM"));
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null, null, authorities);
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                log.info("Authenticated system user, setting security context");
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } catch (Exception e) {
//                log.error("Error while checking system authentication.", e);
//            }
//        }

        chain.doFilter(request, response);
    }

}
