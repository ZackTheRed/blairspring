package com.blair.blairspring;

import com.blair.blairspring.model.userschema.Role;
import com.blair.blairspring.model.userschema.User;
import com.blair.blairspring.repositories.userschema.RoleRepository;
import com.blair.blairspring.repositories.userschema.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BlairspringApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(BlairspringApplication.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication springApp = new SpringApplication(BlairspringApplication.class);
        springApp.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*logger.info("Selected password encoder: {}", passwordEncoder);
        userRepository.findAll().forEach(user -> {
            user.setPassword(passwordEncoder.encode("doe"));
            userRepository.save(user);
        });*/

        if (userRepository.findByUsername("john").isEmpty()) {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            Role userRole = new Role("ROLE_USER");
            Role adminRole = new Role("ROLE_ADMIN");

            roleRepository.save(userRole);
            roleRepository.save(adminRole);

            User user1 = new User("john", encoder.encode("doe"));
            User user2 = new User("jane", encoder.encode("doe"));

            Set<Role> user1Roles = new HashSet<>();
            user1Roles.add(userRole);

            Set<Role> user2Roles = new HashSet<>();
            user2Roles.add(userRole);
            user2Roles.add(adminRole);

            user1.setRoles(user1Roles);
            user2.setRoles(user2Roles);

            userRepository.save(user1);
            userRepository.save(user2);
        }
    }
}
