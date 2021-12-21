package com.csbaic.jd.controller.app;


import com.csbaic.jd.dto.FileInfo;
import com.csbaic.jd.service.uploader.FileUploadService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@Api(value = "文件上传", tags = "文件上传")
@ResponseResult
public class FileUploadController {

    @Autowired
    private FileUploadService uploader;



    @ApiOperation("上传图片")
    @PostMapping(value = "/app", consumes = "multipart/form-data", produces = "application/json")
    @ResponseBody
    public FileInfo upload(
            @ApiParam(allowableValues = "1（超级会员申请图上传）", example = "1")
            @RequestParam(value = "type") Integer type, @RequestParam(value = "file") MultipartFile file){
        return uploader.upload(type, file);
    }


}
