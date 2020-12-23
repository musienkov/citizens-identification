package com.demo.citizens_identification.service;

import com.demo.citizens_identification.model.dto.UserDto;

public interface IdentificationService {

    UserDto identifyUser(String hash);

}
