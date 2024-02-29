package com.example.ecommerce.service;

import com.example.ecommerce.model.User;
import com.example.ecommerce.model.exception.UserException;

import java.util.Optional;

public interface UserService {

    Optional<User> findUserById(Long userId) throws UserException;

    Optional<User> findUserByEmail(String email) throws UserException;

    Optional<User> findUserProfileByJwt(String jwt) throws UserException;

    User createUser(User user);
}
