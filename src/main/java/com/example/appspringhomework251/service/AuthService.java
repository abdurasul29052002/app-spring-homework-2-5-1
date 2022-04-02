package com.example.appspringhomework251.service;

import com.example.appspringhomework251.entity.Role;
import com.example.appspringhomework251.entity.User;
import com.example.appspringhomework251.entity.enums.RoleName;
import com.example.appspringhomework251.payload.LoginDto;
import com.example.appspringhomework251.payload.RegisterDto;
import com.example.appspringhomework251.payload.Result;
import com.example.appspringhomework251.repository.RoleRepository;
import com.example.appspringhomework251.repository.UserRepository;
import com.example.appspringhomework251.security.JwtProvider;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    public Result register(RegisterDto registerDto, RoleName roleName) {
        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setEmailCode(UUID.randomUUID().toString());
        Optional<Role> optionalRole = roleRepository.findByRoleName(roleName);
        user.setRole(optionalRole.orElse(null));

        if (sendMessage(user.getEmail(), "http://localhost:8082/api/auth/verify?emailCode=" + user.getEmailCode() + "&email=" + user.getEmail())) {
            userRepository.save(user);
            return new Result("We are sent verification link to your email address. Please verify your account", true);
        }
        return new Result("Something went terribly wrong. Please check the form", false);

    }


    public Result login(LoginDto loginDto, HttpServletResponse httpServletResponse) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            UserDetails user = loadUserByUsername(loginDto.getEmail());
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            ));
            String token = jwtProvider.generateToken(loginDto.getEmail());
            httpServletResponse.setStatus(200);
            httpServletResponse.setHeader("Authorization", token);
            return new Result("Login successfully", true);
        } catch (Exception e) {
            return new Result("Username or password error", false);
        }
    }

    public Result verifyAccount(String emailCode, String email) {
        Optional<User> optionalUser = userRepository.findByEmailCodeAndEmail(emailCode, email);
        if (!optionalUser.isPresent())
            return new Result("This email is not found", false);
        User user = optionalUser.get();
        user.setEmailCode(null);
        user.setEnabled(true);
        if (user.getPassword() == null) {
            String passwordBySystem = String.valueOf(RandomUtils.nextInt(100000, 999999));
            user.setPassword(passwordEncoder.encode(passwordBySystem));
            sendMessage(email, "You are successfully verified your account. This is your default password: " + passwordBySystem + ". Please login and change your password");
            userRepository.save(user);
            return new Result("We are sent default password", true);
        }
        userRepository.save(user);
        return new Result("You are successfully verified your account", true);
    }

    public boolean sendMessage(String email, String text) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("Director");
            simpleMailMessage.setSubject("Verify your account and install your password");
            simpleMailMessage.setText(text);
            simpleMailMessage.setTo(email);
            javaMailSender.send(simpleMailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new BadCredentialsException("User not found");
    }
}
