package com.service;

import com.dto.request.UserRequest;
import com.dto.response.Metaa;
import com.dto.response.RestResponse;
import com.dto.response.ResultPagination;
import com.dto.response.UserResponse;
import com.entity.UserEntity;
import com.exception.custom.NotFoundException;
import com.exception.custom.UserException;
import com.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public UserResponse createUser(UserRequest userRequest) throws UserException {
        boolean check = userRepository.existsByEmail(userRequest.getEmail());
        if (check) {
            throw new UserException("Email đã tồn tại");
        }
        UserEntity user = modelMapper.map(userRequest, UserEntity.class);
        userRepository.save(user);
        return modelMapper.map(user, UserResponse.class);
    }

    public UserResponse updateUser(UserRequest userRequest) throws UserException {

        UserEntity user = userRepository.findById(userRequest.getId())
                .orElseThrow(() -> new NotFoundException("Người dùng không tồn tại"));
        user.setName(userRequest.getName());
        user.setGender(userRequest.getGender());
        user.setAge(userRequest.getAge());
        user.setAddress(userRequest.getAddress());

        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }

    public RestResponse deleteUser(Long id) throws UserException {
        if (!userRepository.existsById(id)) {
            throw new UserException("Không tồn tại người dùng");
        }
        userRepository.deleteById(id);

    }

    public UserResponse findUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tồn tại người dùng"));
        return modelMapper.map(user, UserResponse.class);
    }

    public ResultPagination findAllUsers(Specification<UserEntity> specification, Pageable pageable) {
        Page<UserEntity> pageUser = userRepository.findAll(specification, pageable);
        ResultPagination resultPagination = new ResultPagination();
        Metaa metaa = new Metaa();
        metaa.setPage(pageable.getPageNumber() + 1);
        metaa.setPageSize(pageable.getPageSize());
        metaa.setPages(pageUser.getTotalPages());
        metaa.setTotal(pageUser.getTotalElements());
        resultPagination.setMeta(metaa);
        resultPagination.setResult(pageUser.getContent());
        List<UserResponse> responseList = pageUser.getContent().stream()
                .map(userEntity -> modelMapper.map(userEntity, UserResponse.class))
                .toList();
        resultPagination.setResult(responseList);
        return resultPagination;
    }

    public UserEntity handelGetUserByUsername(String username) {
        return userRepository.findByEmail(username);
    }

}
