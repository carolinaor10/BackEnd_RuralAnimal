package com.project.demo.logic.entity.role;

import com.project.demo.logic.entity.user.TblUser;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final TblRoleRepository roleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public AdminSeeder(

            TblRoleRepository roleRepository,

            UserRepository  userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createAdministrator();
    }

    private void createAdministrator() {
        TblUser admin = new TblUser();
        admin.setName("Admin");
        admin.setLastName1("User");
        admin.setEmail("admin.user@gmail.com");
        admin.setPassword("admin123");

        Optional<TblRole> optionalRole = roleRepository.findByTitle(RoleEnum.ADMIN);
        Optional<TblUser> optionalUser = userRepository.findByEmail(admin.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = new TblUser();
        user.setName(admin.getName());
        user.setLastName1(admin.getLastName1());
        user.setEmail(admin.getEmail());
        user.setPassword(passwordEncoder.encode(admin.getPassword()));
        user.setRole(optionalRole.get());

        userRepository.save(user);
    }
}