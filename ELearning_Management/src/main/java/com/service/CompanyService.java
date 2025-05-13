package com.service;

import com.dto.response.ResultPagination;
import com.entity.CompanyEntity;
import com.entity.UserEntity;
import com.repository.CompanyRepository;
import com.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyService(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public CompanyEntity createCompany(CompanyEntity companyEntity) {
        return companyRepository.save(companyEntity);
    }

    public ResultPagination getAllCompany(Specification<CompanyEntity> spec, Pageable pageable) {
        Page<CompanyEntity> page = companyRepository.findAll(spec, pageable);
        ResultPagination rs = new ResultPagination();
        ResultPagination.Meta meta = new ResultPagination.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(page.getTotalPages());
        meta.setTotal(page.getTotalElements());
        rs.setMeta(meta);
        rs.setResult(page.getContent());
        return rs;
    }

    public CompanyEntity updateCompany(CompanyEntity companyEntity) {
        CompanyEntity company = companyRepository.findById(companyEntity.getId()).get();
        if (company != null) {
            company.setName(companyEntity.getName());
            company.setAddress(companyEntity.getAddress());
            company.setDescription(companyEntity.getDescription());
            company.setLogo(companyEntity.getLogo());
            return companyRepository.save(company);
        }
        return null;

    }

    public void deleteCompany(Long id) {
        Optional<CompanyEntity> company = companyRepository.findById(id);
        if (company.isPresent()) {
            List<UserEntity> user = userRepository.findByCompany(company.get());
            userRepository.deleteAll(user);
        }
        companyRepository.deleteById(id);
    }

}
