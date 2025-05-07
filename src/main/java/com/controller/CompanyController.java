package com.controller;

import com.dto.response.ResultPagination;
import com.entity.CompanyEntity;
import com.service.CompanyService;
import com.turkraft.springfilter.boot.Filter;
import com.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/companies")
@RestController
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<CompanyEntity> createCompany(@RequestBody @Valid CompanyEntity company) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(company));

    }

    @GetMapping
    @ApiMessage("Lấy tất cả công ty thành công")
    public ResponseEntity<ResultPagination> getAllCompanies(
            @Filter Specification<CompanyEntity> spec,
            Pageable pageable) {
        return ResponseEntity.ok(companyService.getAllCompany(spec, pageable));
    }

    @PutMapping
    public ResponseEntity<CompanyEntity> updateCompany(@RequestBody @Valid CompanyEntity company) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.updateCompany(company));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCompany(Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok(null);
    }
}
