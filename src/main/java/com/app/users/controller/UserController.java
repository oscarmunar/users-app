package com.app.users.controller;

import com.app.users.dto.UserDTO;
import com.app.users.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Operation
    @GetMapping(value = "hello")
    public String getHello() {
        return "Hello Users API!!!";
    }

    @Operation(summary = "Create endpoint")
    @PostMapping(value = "create")
    public ResponseEntity createUser(@RequestBody UserDTO userDto) throws JsonProcessingException {

        return userService.save(userDto);
    }

    @GetMapping(value = "get-user")
    public ResponseEntity getUserById() {
        return null; //ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(1L));
    }





}
