package com.demo.citizens_identification.service;

import com.demo.citizens_identification.model.dto.UserDto;

import java.util.UUID;

public interface UserService {

    UserDto getUser(String hash);

    UserDto crateUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    void deleteUserByHash(String hash);

    void deleteUserById(UUID id);
}
