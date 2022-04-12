package com.hzz.campusback.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDTO {
    // 参数
    private String username;
    // 文件
    private MultipartFile file;
}
