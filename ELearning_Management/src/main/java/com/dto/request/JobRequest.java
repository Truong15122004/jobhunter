package com.dto.request;


import com.util.enums.LevelEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobRequest {
    Long id;
    @NotBlank(message = "Tên công việc không được để trống")
    String name;
    @NotBlank(message = "Tên địa điểm không được để trống")
    String location;

    double salary;
    int quantity;

    @Enumerated(EnumType.STRING)
    LevelEnum level;

    @Column(columnDefinition = "MEDIUMTEXT")
    String description;

    Instant startDate;
    Instant endDate;
    boolean active;


    List<SkillRequest> skills;
}
