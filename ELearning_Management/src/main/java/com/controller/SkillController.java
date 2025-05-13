package com.controller;

import com.dto.request.SkillRequest;
import com.dto.response.ResultPagination;
import com.dto.response.SkillResponse;
import com.entity.SkillEntity;
import com.service.SkillService;
import com.turkraft.springfilter.boot.Filter;
import com.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/skills")
@RestController
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @ApiMessage("Tạo kĩ năng thành công")
    @PostMapping
    public ResponseEntity<SkillResponse> createSkill(@Valid @RequestBody SkillRequest skill) {
        return ResponseEntity.status(HttpStatus.CREATED).body(skillService.createSkill(skill));
    }

    @ApiMessage("Cập nhật kĩ năng thành công")
    @PutMapping
    public ResponseEntity<SkillResponse> updateSkill(@Valid @RequestBody SkillRequest skill) {
        return ResponseEntity.status(HttpStatus.OK).body(skillService.updateSkill(skill));
    }

    @ApiMessage("Lấy tất cả kĩ năng thành công")
    @GetMapping
    public ResponseEntity<ResultPagination> getAllSkills(
            @Filter Specification<SkillEntity> specification, Pageable pageable
    ) {
        return ResponseEntity.ok().body(skillService.getAllSkillWithFilter(specification, pageable));
    }

    @ApiMessage("Xóa kĩ năng thành công")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.ok().body(null);
    }
}
