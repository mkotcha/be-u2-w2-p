package org.emmek.beu2w2p.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.emmek.beu2w2p.entities.User;
import org.emmek.beu2w2p.exception.BadRequestException;
import org.emmek.beu2w2p.exception.NotFoundException;
import org.emmek.beu2w2p.payloads.UserPostDTO;
import org.emmek.beu2w2p.payloads.UserPutDTO;
import org.emmek.beu2w2p.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserServices {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private Cloudinary cloudinary;

    public User save(UserPostDTO body) throws IOException {
        userRepository.findByUsername(body.username()).ifPresent(a -> {
            throw new BadRequestException("Username " + a.getUsername() + " already exists");
        });
        userRepository.findByEmail(body.email()).ifPresent(a -> {
            throw new BadRequestException("User with email " + a.getEmail() + " already exists");
        });
        User user = new User();
        user.setUsername(body.username());
        user.setName(body.name());
        user.setSurname(body.surname());
        user.setEmail(body.email());
        return userRepository.save(user);
    }

    public Page<User> getUsers(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return userRepository.findAll(pageable);
    }

    public User findById(long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        userRepository.delete(user);
    }

    public User findByIdAndUpdate(long id, UserPutDTO body) throws NotFoundException {
        User u = userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        u.setName(body.name());
        u.setSurname(body.surname());
        u.setEmail(body.email());
        return userRepository.save(u);
    }

    public String uploadPicture(long id, MultipartFile file) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        user.setAvatar(url);
        userRepository.save(user);
        return url;
    }
}

