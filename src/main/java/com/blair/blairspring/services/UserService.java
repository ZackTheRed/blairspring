package com.blair.blairspring.services;

import com.blair.blairspring.exceptions.CannotDeleteAdminException;
import com.blair.blairspring.model.userschema.User;
import com.blair.blairspring.repositories.userschema.RoleRepository;
import com.blair.blairspring.repositories.userschema.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService, UserDetailsPasswordService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("User['" + s + "'] not found"));
    }

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String newPassword) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User['" + username + "'] not found"));
        user.setPassword(newPassword);
        return user;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User registerUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setEnabled(true);
        if (user.getRoles() == null) {
            user.setRoles(Set.of(roleRepository.findByRoleName("ROLE_USER").orElseThrow()));
        }
        return userRepository.save(user);
    }

    public void deleteByUsername(String username) {
        if (username.equals("admin")) {
            throw new CannotDeleteAdminException();
        }
        log.info("Deleted users: {}", userRepository.deleteByUsername(username));
    }

}
