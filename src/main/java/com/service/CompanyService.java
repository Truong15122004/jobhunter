package com.service;

import com.dto.response.Metaa;
import com.dto.response.ResultPagination;
import com.entity.CompanyEntity;
import com.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyEntity createCompany(CompanyEntity companyEntity) {
        return companyRepository.save(companyEntity);
    }

    public ResultPagination getAllCompany(Specification<CompanyEntity> spec, Pageable pageable) {
        Page<CompanyEntity> page = companyRepository.findAll(spec, pageable);
        ResultPagination rs = new ResultPagination();
        Metaa metaa = new Metaa();
        metaa.setPage(pageable.getPageNumber() + 1);
        metaa.setPageSize(pageable.getPageSize());
        metaa.setPages(page.getTotalPages());
        metaa.setTotal(page.getTotalElements());
        rs.setMeta(metaa);
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
        companyRepository.deleteById(id);
    }

}
