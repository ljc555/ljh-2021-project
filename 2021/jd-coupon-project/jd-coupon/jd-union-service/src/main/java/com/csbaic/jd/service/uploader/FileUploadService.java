package com.csbaic.jd.service.uploader;

import com.csbaic.jd.dto.FileInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传服务
 */
public interface FileUploadService {


    /**
     * 上传文件
     * @param type
     * @param multipartFile
     * @return
     */
    FileInfo upload(Integer type, MultipartFile multipartFile);


}
