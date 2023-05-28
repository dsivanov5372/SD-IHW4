package com.example.usermicroservice.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.usermicroservice.exception.UserAlreadyRegisteredException;
import com.example.usermicroservice.exception.UserNotFoundException;
import com.example.usermicroservice.exception.WrongPasswordException;
import com.example.usermicroservice.model.AuthDto;
import com.example.usermicroservice.model.JwtResponse;
import com.example.usermicroservice.model.Role;
import com.example.usermicroservice.model.Session;
import com.example.usermicroservice.model.User;
import com.example.usermicroservice.model.UserDto;
import com.example.usermicroservice.repository.SessionRepository;
import com.example.usermicroservice.repository.UserRepository;
import com.example.usermicroservice.security.JwtProvider;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public User registerUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyRegisteredException("Пользователь с таким адресом электронной почты уже существует!");
        }
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UserAlreadyRegisteredException("Пользователь с таким ником уже существует!");
        }

        User user = User.builder()
                        .username(userDto.getUsername())
                        .email(userDto.getEmail())
                        .password(encoder.encode(userDto.getPassword()))
                        .role(Role.CUSTOMER)
                        .build();

        return userRepository.save(user);
    }

    public JwtResponse login(AuthDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail())
                                  .orElseThrow(() -> new UserNotFoundException("Пользователь c таким адрессом электронной почты не найден!"));
        
        if (!encoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new WrongPasswordException("Неверный пароль!");
        }

        String token = jwtProvider.generateAccessToken(user);

        Session session = Session.builder()
                                 .userId(user.getId())
                                 .sessionToken(token)
                                 .expiresAt(LocalDateTime.now().plusHours(1))
                                 .build();
        sessionRepository.save(session);

        return new JwtResponse(token);
    }

    public User getUserInfo(String token) {
        User user = userRepository.findById(jwtProvider.getClaims(token).get("id", Integer.class))
                             .orElseThrow(() -> new UserNotFoundException("Такой пользователь не найден!"));
        user.setPassword(null);
        return user;
    }
}
