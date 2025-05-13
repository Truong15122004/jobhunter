package com.controller;

import com.dto.request.JobRequest;
import com.dto.response.JobResponse;
import com.dto.response.ResultPagination;
import com.entity.JobEntity;
import com.service.JobService;
import com.turkraft.springfilter.boot.Filter;
import com.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @ApiMessage("Tạo công việc thành công")
    @PostMapping
    public ResponseEntity<JobResponse> createJob(@Valid @RequestBody JobRequest jobRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(jobRequest));
    }

    @ApiMessage("Cập nhật công việc thành công")
    @PutMapping
    public ResponseEntity<JobResponse> updateJob(@Valid @RequestBody JobRequest jobRequest) {
        return ResponseEntity.ok().body(jobService.updateJob(jobRequest));
    }

    @ApiMessage("Lấy tất cả công việc thành công")
    @GetMapping
    public ResponseEntity<ResultPagination> getAllJobsWithFilter(
            @Filter Specification<JobEntity> specification, Pageable pageable) {
        return ResponseEntity.ok().body(jobService.getAllJobsWithFilter(specification, pageable));
    }

    @ApiMessage("Xóa công việc thành công")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok().body(null);
    }

}
