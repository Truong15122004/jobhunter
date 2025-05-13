package com.service;

import com.dto.request.JobRequest;
import com.dto.response.JobResponse;
import com.dto.response.ResultPagination;
import com.entity.JobEntity;
import com.entity.SkillEntity;
import com.exception.custom.JobException;
import com.repository.JobRepository;
import com.repository.SkillRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {
    private final SkillRepository skillRepository;
    private final ModelMapper modelMapper;
    private final JobRepository jobRepository;

    public JobService(SkillRepository skillRepository, ModelMapper modelMapper, JobRepository jobRepository) {
        this.skillRepository = skillRepository;
        this.modelMapper = modelMapper;
        this.jobRepository = jobRepository;
    }

    public JobResponse createJob(JobRequest jobRequest) {
        if (jobRequest.getId() != null) {
            throw new JobException("Không được truyền id");
        }
        List<Long> ids = new ArrayList<>();
        jobRequest.getSkills().forEach(skill -> ids.add(skill.getId()));
        List<SkillEntity> existedSkill = skillRepository.findByIdIn(ids);
        JobEntity jobEntity = modelMapper.map(jobRequest, JobEntity.class);
        jobEntity.setSkills(existedSkill);
        JobResponse jobResponse = modelMapper.map(jobRepository.save(jobEntity), JobResponse.class);
        jobResponse.setSkills(existedSkill.stream().map(SkillEntity::getName).collect(Collectors.toList()));
        return jobResponse;
    }

    public JobResponse updateJob(JobRequest jobRequest) {
        JobEntity jobEntity = jobRepository.findById(jobRequest.getId())
                .orElseThrow(() -> new JobException("Công việc không tồn tại"));
        List<Long> isd = new ArrayList<>();
        jobRequest.getSkills().forEach(skill -> isd.add(skill.getId()));
        List<SkillEntity> existedSkill = skillRepository.findByIdIn(isd);
        jobEntity.setSkills(existedSkill);
        jobEntity.setName(jobRequest.getName());
        jobEntity.setDescription(jobRequest.getDescription());
        jobEntity.setActive(jobRequest.isActive());
        jobEntity.setSalary(jobRequest.getSalary());
        jobEntity.setStartDate(jobRequest.getStartDate());
        jobEntity.setEndDate(jobRequest.getEndDate());
        jobEntity.setLevel(jobRequest.getLevel());
        jobEntity.setLocation(jobRequest.getLocation());
        return modelMapper.map(jobRepository.save(jobEntity), JobResponse.class);
    }

    public ResultPagination getAllJobsWithFilter(Specification<JobEntity> specification, Pageable pageable) {
        Page<JobEntity> page = jobRepository.findAll(specification, pageable);
        ResultPagination resultPagination = new ResultPagination();
        ResultPagination.Meta meta = new ResultPagination.Meta();

        List<JobResponse> jobResponseList = page.getContent().stream()
                .map(job -> {
                    JobResponse jobResponse = modelMapper.map(job, JobResponse.class);
                    List<String> skills = job.getSkills().stream()
                            .map(SkillEntity::getName).toList();
                    jobResponse.setSkills(skills);
                    return jobResponse;
                }).toList();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setTotal(page.getTotalElements());
        meta.setPages(page.getTotalPages());
        resultPagination.setMeta(meta);
        resultPagination.setResult(jobResponseList);
        return resultPagination;
    }

    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
}
