package com.project.demo.rest.user;

import com.project.demo.logic.entity.http.GlobalResponseHandler;
import com.project.demo.logic.entity.http.Meta;
import com.project.demo.logic.entity.user.TblUser;
import com.project.demo.logic.entity.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserRestController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<TblUser> ordersPage = userRepository.findAll(pageable);
        Meta meta = new Meta(request.getMethod(), request.getRequestURL().toString());
        meta.setTotalPages(ordersPage.getTotalPages());
        meta.setTotalElements(ordersPage.getTotalElements());
        meta.setPageNumber(ordersPage.getNumber() + 1);
        meta.setPageSize(ordersPage.getSize());

        return new GlobalResponseHandler().handleResponse("Order retrieved successfully",
                ordersPage.getContent(), HttpStatus.OK, meta);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> addUser(@RequestBody TblUser user, HttpServletRequest request) {
        if (user.getRole() == null || !(user.getRole().getTitle().equals("ADMIN") ||
                                        user.getRole().getTitle().equals("BUYER") ||
                                        user.getRole().getTitle().equals("SELLER"))) {
            return ResponseEntity.badRequest().body("User expected to have 'ADMIN', 'BUYER' or 'SELLER' role.");
        }

        if (!isValidEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("El correo ingresado no tiene un formato válido.");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Este correo ya existe");
        }

        if (!isValidPassword(user.getPassword())) {
            return ResponseEntity.badRequest().body("La contraseña debe tener al menos 8 caracteres, incluir una letra mayúscula, " +
                    "una letra minúscula, un número y un carácter especial.");
        }

        if (!isAdult(user.getBirthDate())) {
            return ResponseEntity.badRequest().body("El usuario debe ser mayor de 18 años para registrarse.");
        }

        user.setIdentification(formatIdentification(user.getIdentification()));
        user.setPhoneNumber(formatPhoneNumber(user.getPhoneNumber()));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new GlobalResponseHandler().handleResponse("User added successfully",
                user, HttpStatus.OK, request);
    }

    @PostMapping("/autoRegister")
    public ResponseEntity<?> addYourself(@RequestBody TblUser user, HttpServletRequest request) {
        if (user.getRole() == null || !(user.getRole().getTitle().equals("BUYER") ||
                                        user.getRole().getTitle().equals("SELLER"))) {
            return ResponseEntity.badRequest().body("User expected to have 'BUYER' o 'SELLER' role.");
        }

        if (!isValidEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("El correo ingresado no tiene un formato válido.");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Este correo ya existe");
        }

        if (!isValidPassword(user.getPassword())) {
            return ResponseEntity.badRequest().body("La contraseña debe tener al menos 8 caracteres, incluir una letra mayúscula, " +
                    "una letra minúscula, un número y un carácter especial.");
        }

        if (!isAdult(user.getBirthDate())) {
            return ResponseEntity.badRequest().body("Debes ser mayor de 18 años para registrarte.");
        }

        user.setIdentification(formatIdentification(user.getIdentification()));
        user.setPhoneNumber(formatPhoneNumber(user.getPhoneNumber()));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return new GlobalResponseHandler().handleResponse("User added successfully",
                user, HttpStatus.OK, request);
    }


    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody TblUser user, HttpServletRequest request) {
        Optional<TblUser> foundOrder = userRepository.findById(userId);
        if(foundOrder.isPresent()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return new GlobalResponseHandler().handleResponse("TblUser updated successfully",
                    user, HttpStatus.OK, request);
        } else {
            return new GlobalResponseHandler().handleResponse("TblUser id " + userId + " not found"  ,
                    HttpStatus.NOT_FOUND, request);
        }
    }


    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId, HttpServletRequest request) {
        Optional<TblUser> foundOrder = userRepository.findById(userId);
        if(foundOrder.isPresent()) {
            userRepository.deleteById(userId);
            return new GlobalResponseHandler().handleResponse("TblUser deleted successfully",
                    foundOrder.get(), HttpStatus.OK, request);
        } else {
            return new GlobalResponseHandler().handleResponse("Order id " + userId + " not found"  ,
                    HttpStatus.NOT_FOUND, request);
        }
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public TblUser authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (TblUser) authentication.getPrincipal();
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^(?!\\.|-)[a-zA-Z0-9._-]+@[a-zA-Z0-9]+(\\.[a-zA-Z]{2,6})+$";
        return email != null && email.matches(emailRegex);
    }

    public boolean isValidPassword(String password) {
        // Expresión regular para validar la contraseña
        String passwordRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        return password != null && password.matches(passwordRegex);
    }

    public boolean isAdult(LocalDate birthDate) {
        if (birthDate == null) {
            return false; // o puedes lanzar una excepción, dependiendo de tu manejo de errores
        }

        int age = Period.between(birthDate, LocalDate.now()).getYears();
        return age >= 18;
    }

    public String formatIdentification(String identification) {
        if (identification == null || identification.length() != 12) {
            return identification;
        }
        return identification.substring(0, 1) + "-" + identification.substring(1, 5) + "-" + identification.substring(5);
    }

    public String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() != 8) {
            return phoneNumber;
        }
        return phoneNumber.substring(0, 4) + "-" + phoneNumber.substring(4);
    }

}