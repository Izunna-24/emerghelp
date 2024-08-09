package com.emerghelp.emerghelp.security.services;

import com.emerghelp.emerghelp.data.models.User;
import com.emerghelp.emerghelp.security.model.SecureUser;
import com.emerghelp.emerghelp.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userService.getUserByEmail(username);
            return new SecureUser(user);
        }catch (Exception exception){
            throw new UsernameNotFoundException(exception.getMessage());
        }
    }
}
