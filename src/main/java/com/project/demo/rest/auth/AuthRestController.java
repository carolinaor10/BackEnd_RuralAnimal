package com.project.demo.rest.auth;

import com.project.demo.logic.entity.auth.AuthenticationService;
import com.project.demo.logic.entity.auth.JwtService;

import com.project.demo.logic.entity.http.GlobalResponseHandler;
import com.project.demo.logic.entity.role.RoleEnum;
import com.project.demo.logic.entity.role.TblRole;
import com.project.demo.logic.entity.role.TblRoleRepository;
import com.project.demo.logic.entity.user.LoginResponse;
import com.project.demo.logic.entity.user.TblUser;
import com.project.demo.logic.entity.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@RequestMapping("/auth")
@RestController
public class AuthRestController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TblRoleRepository roleRepository;



    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public AuthRestController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody TblUser user) {
        TblUser authenticatedUser = authenticationService.authenticate(user);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        Optional<TblUser> foundedUser = userRepository.findByEmail(user.getEmail());

        foundedUser.ifPresent(loginResponse::setAuthUser);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody TblUser user, HttpServletRequest request) {


        // Validate email format
        if (!isValidEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("El correo ingresado no tiene un formato válido.");
        }

        // Check for existing email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este correo ya existe");
        }

        // Validate password
        if (!isValidPassword(user.getPassword())) {
            return ResponseEntity.badRequest().body("La contraseña debe tener al menos 8 caracteres, incluir una letra mayúscula, " +
                    "una letra minúscula, un número y un carácter especial.");
        }

        // Validate age
        if (!isAdult(user.getBirthDate())) {
            return ResponseEntity.badRequest().body("Debes ser mayor de 18 años para registrarte.");
        }

        // Format identification and phone number
        user.setIdentification(formatIdentification(user.getIdentification()));
        user.setPhoneNumber(formatPhoneNumber(user.getPhoneNumber()));

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Validate role existence
        Optional<TblRole> optionalRole = roleRepository.findByTitle(user.getRole().getTitle());
        if (optionalRole.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role not found");
        }
        user.setRole(optionalRole.get());

        // Save user
        TblUser savedUser = userRepository.save(user);
        return new GlobalResponseHandler().handleResponse("User registered successfully", savedUser, HttpStatus.OK, request);
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
        if (identification == null || identification.length() != 9) {
            return identification;
        }
        StringBuilder formattedId = new StringBuilder();
        formattedId.append(identification.charAt(0)).append("-");

        if (identification.length() >= 5) {
            formattedId.append(identification.substring(1, Math.min(5, identification.length()))).append("-");
        }

        if (identification.length() > 5) {
            formattedId.append(identification.substring(5));
        }

        return formattedId.toString();
    }

    public String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() != 8) {
            return phoneNumber;
        }
        return phoneNumber.substring(0, 4) + "-" + phoneNumber.substring(4);
    }
}