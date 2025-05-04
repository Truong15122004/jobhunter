package com.service;

import com.entity.CompanyEntity;
import com.repository.CompanyRepository;
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
}
