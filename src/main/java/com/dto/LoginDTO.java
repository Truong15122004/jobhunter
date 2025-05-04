package com.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginDTO {
    @NotBlank(message = "Username không được để trống")
    String username;
    @NotBlank(message = "Mật khẩu không được để trống")
    String password;
}
