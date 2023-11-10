package org.emmek.beu2w2p.controller;

import org.emmek.beu2w2p.entities.User;
import org.emmek.beu2w2p.exception.BadRequestException;
import org.emmek.beu2w2p.payloads.UserPostDTO;
import org.emmek.beu2w2p.payloads.UserPutDTO;
import org.emmek.beu2w2p.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public User postUsers(@RequestBody @Validated UserPostDTO body, BindingResult validation) {
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

    @PostMapping("/{id}/avatar")
    public String uploadExample(@PathVariable long id, @RequestParam("avatar") MultipartFile body) throws IOException {
        return userService.uploadPicture(id, body);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public User getUserById(@PathVariable long id) {
        try {
            return userService.findById(id);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/{id}")
    public User findByIdAndUpdate(@PathVariable long id, @RequestBody @Validated UserPutDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return userService.findByIdAndUpdate(id, body);
    }
}
