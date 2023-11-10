package org.emmek.beu2w2p.services;

import com.cloudinary.Cloudinary;
import org.emmek.beu2w2p.entities.User;
import org.emmek.beu2w2p.payloads.UserPostDTO;
import org.emmek.beu2w2p.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class UserServices {
    @Autowired
    UserRepositories userRepository;
    @Autowired
    private Cloudinary cloudinary;

    public User save(UserPostDTO body) throws IOException {
        userRepository.findByUsername(body.email()).ifPresent(a -> {
            throw new BadRequestException("Username " + a.getUsername() + " already exists");
        });
        userRepository.findByEmail(body.email()).ifPresent(a -> {
            throw new BadRequestException("User with email " + a.getEmail() + " already exists");
        });
        User user = new User()
                user.setUsername(body.username());
        user.setName(body.name());
        user.setSurname(body.surname());
        user.setEmail(body.email());

        return userRepository.save(user);
    }


}

