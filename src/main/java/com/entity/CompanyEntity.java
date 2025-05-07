package com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.util.SecurityUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "companies")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @NotBlank(message = "Tên không được để trống")
    String name;

    @Column(columnDefinition = "MEDIUMTEXT")
    String description;

    String address;
    String logo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    Instant createdAt;
    Instant updatedAt;

    String createdBy;
    String updatedBy;

    @PrePersist
    public void handleBeforeCreate() {
        this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent() == true ?
                SecurityUtil.getCurrentUserLogin().get() : "";
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void handelBeforeUpdate() {
        this.updatedAt = Instant.now();
        this.updatedBy = SecurityUtil.getCurrentUserLogin().isPresent() == true ?
                SecurityUtil.getCurrentUserLogin().get() : "";
    }
}
