package com.demo.citizens_identification.controller;

import com.demo.citizens_identification.model.dto.UserDto;
import com.demo.citizens_identification.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.demo.citizens_identification.controller.ApiConstants.USER_MANAGEMENT;
import static com.demo.citizens_identification.controller.ApiConstants.V1_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequestMapping(V1_PATH + USER_MANAGEMENT)
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/create", method = POST)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto user = userService.crateUser(userDto);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/update", method = POST)
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        UserDto user = userService.updateUser(userDto);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/delete/{id}", method = DELETE)
    public ResponseEntity<UserDto> deleteUserById(@PathVariable String id) {
        userService.deleteUserById(UUID.fromString(id));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/delete", method = DELETE)
    public ResponseEntity<UserDto> deleteUserByHash(@RequestParam String hash) {
        userService.deleteUserByHash(hash);
        return ResponseEntity.ok().build();
    }
}
