package com.hdu.iee.lostfound.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Value("${app.upload.max-size:10485760}") // 10MB
    private long maxFileSize;

    @PostMapping("/image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        try {
            System.out.println("收到文件上传请求");
            System.out.println("文件名: " + (file != null ? file.getOriginalFilename() : "null"));
            System.out.println("文件大小: " + (file != null ? file.getSize() : "null"));
            System.out.println("文件类型: " + (file != null ? file.getContentType() : "null"));
            // 验证文件
            if (file.isEmpty()) {
                response.put("error", "文件不能为空");
                return ResponseEntity.badRequest().body(response);
            }

            // 验证文件大小
            if (file.getSize() > maxFileSize) {
                response.put("error", "文件大小不能超过10MB");
                return ResponseEntity.badRequest().body(response);
            }

            // 验证文件类型
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                response.put("error", "只支持图片文件");
                return ResponseEntity.badRequest().body(response);
            }

            // 获取文件扩展名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            // 生成唯一文件名
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String uniqueId = UUID.randomUUID().toString().substring(0, 8);
            String fileName = "lost_item_" + timestamp + "_" + uniqueId + fileExtension;

            // 确保上传目录存在
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
            System.out.println("上传目录路径: " + uploadPath);

            if (!Files.exists(uploadPath)) {
                System.out.println("创建上传目录: " + uploadPath);
                Files.createDirectories(uploadPath);
            }

            if (!Files.isWritable(uploadPath)) {
                throw new IOException("上传目录不可写: " + uploadPath);
            }

            // 保存文件
            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath.toFile());

            // 返回文件URL
            String fileUrl = "/files/" + fileName;
            response.put("url", fileUrl);
            response.put("filename", fileName);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            response.put("error", "文件上传失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}

