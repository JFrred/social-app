package com.example.service;

import com.example.dto.AuthenticationResponse;
import com.example.dto.LoginRequest;
import com.example.dto.RegisterRequest;
import com.example.exception.SpringSocialAppException;
import com.example.model.NotificationEmail;
import com.example.model.User;
import com.example.model.VerificationToken;
import com.example.repo.UserRepository;
import com.example.repo.VerificationTokenRepository;
import com.example.security.JwtProvider;
import com.example.util.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

import static java.time.Instant.now;

@Service
@AllArgsConstructor
@Slf4j  // Logger logger = LoggerFactory.getLogger(AuthService.class);
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepo;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public User getCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(loggedInUser.getName()).get();

        return userRepository.findUserByUsername(user.getUsername())
                .orElseThrow(() -> new SpringSocialAppException("Exception occurred while getting current user"));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setCreated(now());
        user.setEnabled(false);

        userRepository.save(user);
        log.info("User Registered Successfully, Sending Authentication Email");
        String token = generateVerificationToken(user);
        String message = mailContentBuilder.build("Thank you for signing up to Spring Reddit, please click on the below url to activate your account : "
                + Constants.ACTIVATION_EMAIL + "/" + token);

        mailService.sendMail(new NotificationEmail( user.getEmail(), "Please Activate your account", message));
    }

    @Transactional
    void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findUserByUsername(username).
                orElseThrow(() -> new SpringSocialAppException("User not found with username - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepo.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepo.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new SpringSocialAppException("Invalid Token"));
        fetchUserAndEnable(verificationTokenOptional.get());
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
