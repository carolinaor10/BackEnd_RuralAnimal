package com.project.demo.logic.entity.role;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final TblRoleRepository roleRepository;


    public RoleSeeder(TblRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[] { RoleEnum.USER, RoleEnum.ADMIN, RoleEnum.SUPER_ADMIN };
        Map<RoleEnum, String> roleDescriptionMap = Map.of(
                RoleEnum.USER, "Default user role",
                RoleEnum.ADMIN, "Administrator role",
                RoleEnum.SUPER_ADMIN, "Super Administrator role"
        );

        Arrays.stream(roleNames).forEach((roleName) -> {
            Optional<TblRole> optionalRole = roleRepository.findByTitle(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                TblRole roleToCreate = new TblRole();

                roleToCreate.setTitle(roleName);
                roleToCreate.setDescription(roleDescriptionMap.get(roleName));
                //
                roleRepository.save(roleToCreate);
            });
        });
    }
}
