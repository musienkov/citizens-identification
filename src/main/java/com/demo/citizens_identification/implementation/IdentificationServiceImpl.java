package com.demo.citizens_identification.implementation;

import com.demo.citizens_identification.model.dto.UserDto;
import com.demo.citizens_identification.service.IdentificationService;
import com.demo.citizens_identification.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class IdentificationServiceImpl implements IdentificationService {

    private final UserService userService;

    public IdentificationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDto identifyUser(String hash) {
        return userService.getUser(hash);
    }
}
