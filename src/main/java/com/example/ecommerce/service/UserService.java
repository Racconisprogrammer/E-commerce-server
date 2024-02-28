package com.example.ecommerce.service;

import com.example.ecommerce.model.User;
import com.example.ecommerce.model.exception.UserException;

public interface UserService {

    User findUserById(Long userId) throws UserException;

    User findUserProfileByJwt(String jwt) throws UserException;
}
