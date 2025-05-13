package com.controller;

import com.dto.response.FileResponse;
import com.exception.custom.StorageException;
import com.service.FileService;
import com.util.annotation.ApiMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/api/v1/files")
@RestController
public class FileController {
    private final FileService fileService;
    @Value("${hoidanit.upload-file.base-uri}")
    private String baseURI;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @ApiMessage("Upload file thành công")
    @PostMapping
    public ResponseEntity<FileResponse> upload(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("folder") String folder) throws URISyntaxException, IOException {
        //skip validate
        if (file.isEmpty() || file == null) {
            throw new StorageException("File bị rỗng");
        }
        String fileName = file.getOriginalFilename();
        List<String> allowedExtensions = Arrays.asList("pdf", "jpg", "jpeg", "png", "doc", "docx");
        boolean valid = allowedExtensions.stream().anyMatch(item -> fileName.toLowerCase().endsWith(item));
        if (!valid) {
            throw new StorageException("Định dạng file không hợp lệ, chỉ cho phép " + allowedExtensions);
        }

        //create a directory if not exist
        fileService.createUploadFolder(baseURI + folder);


        //store file
        String finalName = fileService.store(file, folder);

        FileResponse fileResponse = new FileResponse(finalName, Instant.now());
        return ResponseEntity.ok(fileResponse);
    }
}
