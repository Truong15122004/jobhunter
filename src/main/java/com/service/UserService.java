package com.service;

import com.entity.UserEntity;
import com.exception.custom.IdInvalidException;
import com.exception.custom.NotFoundException;
import com.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity user) {
        if (user.getId() > 1500) {
            throw new IdInvalidException("Id invalid");
        }
        return userRepository.save(user);
    }

    public UserEntity updateUser(UserEntity userRequest) {

        UserEntity user = userRepository.findById(userRequest.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserEntity findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }
}
