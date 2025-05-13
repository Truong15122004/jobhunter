package com.controller;

import com.dto.request.UserRequest;
import com.dto.response.ResultPagination;
import com.dto.response.UserResponse;
import com.entity.UserEntity;
import com.exception.custom.UserException;
import com.service.UserService;
import com.turkraft.springfilter.boot.Filter;
import com.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/users")
@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @ApiMessage("Tạo người dùng thành công")
    @PostMapping
    public ResponseEntity<UserResponse> createNewUser(@Valid @RequestBody UserRequest newUser) throws UserException {
        String hashPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(hashPassword);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(newUser));
    }

    @ApiMessage("Lấy người dùng thành công")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @ApiMessage("Lấy tất cả người dùng thành công")
    @GetMapping
    public ResponseEntity<ResultPagination> getAllUsers(@Filter Specification<UserEntity> specification, Pageable pageable) {
        return ResponseEntity.ok(userService.findAllUsers(specification, pageable));
    }

    @ApiMessage("Xóa người dùng thành công")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws UserException {
        userService.deleteUser(id);
        return ResponseEntity.ok(null);
    }

    @ApiMessage("Cập nhật người dùng thành công")
    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserRequest user) throws UserException {
        return ResponseEntity.ok(userService.updateUser(user));
    }

}
