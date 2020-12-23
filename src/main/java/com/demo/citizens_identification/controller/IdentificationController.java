package com.demo.citizens_identification.controller;

import com.demo.citizens_identification.model.dto.UserDto;
import com.demo.citizens_identification.service.IdentificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.demo.citizens_identification.controller.ApiConstants.IDENTIFICATION_MANAGEMENT;
import static com.demo.citizens_identification.controller.ApiConstants.IDENTIFY;
import static com.demo.citizens_identification.controller.ApiConstants.V1_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RequestMapping(V1_PATH + IDENTIFICATION_MANAGEMENT + IDENTIFY)
@RestController
public class IdentificationController {

    private final IdentificationService identificationService;

    public IdentificationController(IdentificationService identificationService) {
        this.identificationService = identificationService;
    }

    @RequestMapping(method = GET)
    public ResponseEntity<UserDto> identifyUser(@RequestParam String hash) {
        UserDto user = identificationService.identifyUser(hash);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
