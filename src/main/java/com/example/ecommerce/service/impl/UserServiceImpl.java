package com.example.ecommerce.service.impl;


import com.example.ecommerce.config.JwtProvider;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.exception.UserException;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    @SneakyThrows
    @Override
    public User findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            return user.get();
        }

        throw new UserException("Not found user with id " + userId);
    }

    @SneakyThrows
    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @SneakyThrows
    @Override
    public User findUserProfileByJwt(String jwt) {
        String email = jwtProvider.getEmailFromToken(jwt);
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        }
        throw new UserException("User not found with jwt " + jwt);
    }

    @SneakyThrows
    @Override
    public User createUser(User user) {
        Optional<User> isEmailExist = findUserByEmail(user.getEmail());

        if (isEmailExist.isPresent()) {
            throw new UserException("Email is already used with another account");
        }
        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createdUser.setFirstName(user.getFirstName());
        createdUser.setLastName(user.getLastName());
        createdUser.setRole(user.getRole());
        createdUser.setMobile(user.getMobile());
        userRepository.save(createdUser);
        return createdUser;
    }
}
