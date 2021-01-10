package com.blair.blairspring.services;

import com.blair.blairspring.exceptions.CannotDeleteAdminException;
import com.blair.blairspring.model.userschema.User;
import com.blair.blairspring.repositories.userschema.RoleRepository;
import com.blair.blairspring.repositories.userschema.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User registerUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setRoles(Set.of(roleRepository.findByRoleName("ROLE_USER").orElseThrow()));
        return userRepository.save(user);
    }

    public void deleteByUsername(String username) {
        if (username.equals("admin")) {
            throw new CannotDeleteAdminException();
        }
        logger.info("Deleted users: {}", userRepository.deleteByUsername(username));
    }

}
