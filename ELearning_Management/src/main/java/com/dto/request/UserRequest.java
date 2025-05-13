package com.dto.request;

import com.entity.CompanyEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.util.enums.GenderEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    long id;
    @NotBlank(message = "Tên không được để trống")
    String name;
    String email;
    String password;
    int age;

    @ManyToOne
    @JoinColumn(name = "company_id")
    CompanyEntity company;

    @Enumerated(EnumType.STRING)
    GenderEnum gender;
    String address;
    String refreshToken;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    Instant createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    Instant updatedAt;

    String createdBy;
    String updatedBy;
}
