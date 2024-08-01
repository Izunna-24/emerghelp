package com.emerghelp.emerghelp.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.emerghelp.emerghelp.dtos.requests.LoginRequest;
import com.emerghelp.emerghelp.dtos.responses.BaseResponse;
import com.emerghelp.emerghelp.dtos.responses.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Collection;

@AllArgsConstructor
public class CustomUsernamePasswordAuthenticationFilter
        extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuthenticationManager authenticationManager;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            InputStream requestBodyStream = request.getInputStream();
            LoginRequest loginRequest = objectMapper
                    .readValue(requestBodyStream, LoginRequest.class);
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticationResult = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);
            return authenticationResult;
        } catch (IOException exception) {
            throw new BadCredentialsException(exception.getMessage());
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String token = generateAccessToken(authResult);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setMessage("Successful Authentication");
        BaseResponse<LoginResponse> authResponse = new BaseResponse<>();
        authResponse.setData(loginResponse);
        authResponse.setCode(HttpStatus.OK.value());
        authResponse.setStatus(true);
        response.getOutputStream().write(objectMapper.writeValueAsBytes(authResponse));
        response.flushBuffer();
        chain.doFilter(request,response);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException exception)
            throws IOException, ServletException {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage(exception.getMessage());
        BaseResponse<LoginResponse> baseResponse = new BaseResponse<>();
        baseResponse.setData(loginResponse);
        baseResponse.setStatus(false);
        baseResponse.setCode(HttpStatus.UNAUTHORIZED.value());
        response.getOutputStream()
                .write(objectMapper.writeValueAsBytes(baseResponse));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.flushBuffer();

    }

    private static String generateAccessToken(Authentication authResult) {
        return JWT.create()
                .withIssuer("emerghelp")
                .withArrayClaim("roles",getClaimsFrom(authResult.getAuthorities()))
                .withExpiresAt(Instant.now().plusSeconds(24 * 60 * 60))
                .sign(Algorithm.HMAC512("secret"));

    }

    private static String[] getClaimsFrom(Collection<? extends GrantedAuthority> authorities){
        return authorities.stream()
                .map((grantedAuthority) -> grantedAuthority.getAuthority())
                .toArray(String[]::new);

    }
}


