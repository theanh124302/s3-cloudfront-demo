package com.example.s3demo.controller;

import com.example.s3demo.service.HtmlFileGenerator;
import com.example.s3demo.service.S3Uploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
public class HtmlUploadController {

    private final S3Uploader s3Uploader;

    @Autowired
    public HtmlUploadController(S3Uploader s3Uploader) {
        this.s3Uploader = s3Uploader;
    }

    @PostMapping("/uploadHtml")
    public String uploadHtml(@RequestBody String htmlContent) throws IOException {
        // Tạo file HTML từ chuỗi text
        File htmlFile = HtmlFileGenerator.generateHtmlFile(htmlContent, "temp.html");

        // Đẩy file lên AWS S3 và lấy URL
        String fileUrl = s3Uploader.uploadFile(htmlFile, "uploads/temp.html");

        // Xóa file tạm sau khi upload
        htmlFile.delete();

        return fileUrl;
    }
}