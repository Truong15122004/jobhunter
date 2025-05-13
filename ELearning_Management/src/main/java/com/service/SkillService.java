package com.service;

import com.dto.request.SkillRequest;
import com.dto.response.ResultPagination;
import com.dto.response.SkillResponse;
import com.entity.SkillEntity;
import com.exception.custom.SkillException;
import com.repository.SkillRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {
    private final SkillRepository skillRepository;
    private final ModelMapper modelMapper;

    public SkillService(SkillRepository skillRepository, ModelMapper modelMapper) {
        this.skillRepository = skillRepository;
        this.modelMapper = modelMapper;
    }

    public SkillResponse createSkill(SkillRequest skillRequest) {
        if (skillRepository.existsByName(skillRequest.getName())) {
            throw new SkillException("Kĩ năng đã tồn tại");
        }
        SkillEntity skillEntity = modelMapper.map(skillRequest, SkillEntity.class);
        return modelMapper.map(skillRepository.save(skillEntity), SkillResponse.class);
    }

    public SkillResponse updateSkill(SkillRequest skillRequest) {
        SkillEntity skillEntity = skillRepository.findById(skillRequest.getId())
                .orElseThrow(() -> new SkillException("Kĩ năng không tồn tại"));
        if (skillRepository.existsByNameAndIdNot(skillRequest.getName(), skillRequest.getId())) {
            throw new SkillException("Kĩ năng này đã tồn tại");
        }
        skillEntity.setName(skillRequest.getName());
        return modelMapper.map(skillRepository.save(skillEntity), SkillResponse.class);
    }

    public ResultPagination getAllSkillWithFilter(Specification<SkillEntity> specification, Pageable pageable) {
        Page<SkillEntity> pageSkill = skillRepository.findAll(specification, pageable);
        List<SkillResponse> skillResponses = pageSkill.getContent().stream()
                .map(skillEntity -> modelMapper.map(skillEntity, SkillResponse.class))
                .toList();
        ResultPagination resultPagination = new ResultPagination();
        ResultPagination.Meta meta = new ResultPagination.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(pageSkill.getTotalPages());
        meta.setTotal(pageSkill.getTotalElements());
        resultPagination.setMeta(meta);
        resultPagination.setResult(skillResponses);
        return resultPagination;
    }

    public void deleteSkill(Long id) {
        SkillEntity skillEntity = skillRepository.findById(id)
                .orElseThrow(() -> new SkillException("Kĩ năng không tồn tại"));
        skillEntity.getJobs().forEach(job -> job.getSkills().remove(skillEntity));
        skillRepository.delete(skillEntity);
    }

}
