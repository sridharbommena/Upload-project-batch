package com.temp.upload.controller;

import com.temp.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @GetMapping("/delete-all")
    public String getAll()
    {
        uploadService.deleteFiles();
        return "check-logs";
    }

}
