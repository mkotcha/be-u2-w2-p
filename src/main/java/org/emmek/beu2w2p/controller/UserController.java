package org.emmek.beu2w2p.controller;

import org.emmek.beu2w2p.entities.User;
import org.emmek.beu2w2p.exception.BadRequestException;
import org.emmek.beu2w2p.payloads.UserPostDTO;
import org.emmek.beu2w2p.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServices userService;

    @GetMapping("")
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String sort) {
        return userService.getUsers(page, size, sort);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User postUsers(@RequestBody UserPostDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return userService.save(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
