package com.csbaic.jd.service.uploader.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.dto.FileInfo;
import com.csbaic.jd.enums.FileType;
import com.csbaic.jd.service.uploader.FileUploadService;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Service
public class AliOssFileUploadService implements FileUploadService, InitializingBean,  DisposableBean {


    private static final UriComponents BUCKET_ENDPOINT = UriComponentsBuilder
            .fromHttpUrl("https://csbaic-jd-coupon.oss-cn-beijing.aliyuncs.com")
            .build();

    private static final UriComponents ENDPOINT = UriComponentsBuilder
            .fromHttpUrl("https://oss-cn-beijing.aliyuncs.com")
            .build();

    private static final String ACCESS_KEY = "LTAI4Fw9czU1JM7co6vXX2qV";
    private static final String ACCESS_KEY_SECRET = "gdXV8ZrkS4NXyCEfBF2H1ARQGEwSV2";

    private static final String BUCKET_NAME = "csbaic-jd-coupon";
    private static final String SUPER_MEMBER_APPLY_OBJECT_PREFIX = "super-member-apply/";
    private OSS oss;



    @Override
    public void afterPropertiesSet() throws Exception {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ENDPOINT.toUriString();
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。


        // 创建OSSClient实例。
        oss = new OSSClientBuilder().build(endpoint, ACCESS_KEY, ACCESS_KEY_SECRET);

    }

    @Override
    public FileInfo upload(Integer type, MultipartFile multipartFile) {
        FileType fileType = FileType.typeOf(type);
        if(fileType == null){
            throw BizRuntimeException.from(ResultCode.ERROR, "不支持的文件上传类型");
        }

        switch (fileType){

            case SUPER_MEMBER_APPLY:
                return uploadSuperMemberApplyImage(multipartFile);
            default:
                throw BizRuntimeException.from(ResultCode.ERROR, "不支持的文件上传类型");
        }
    }


    public FileInfo uploadSuperMemberApplyImage(MultipartFile file) {

        try{
            // FIXME: 2020/3/25 优化文字名称，防止并发问题
            String oname = file.getOriginalFilename() != null ? file.getOriginalFilename() : "";
            String ext = Files.getFileExtension(oname);
            String filename = DigestUtils.md2Hex((oname + System.currentTimeMillis()).getBytes());

            filename += "." + ext;

            return uploadOnce(BUCKET_NAME, SUPER_MEMBER_APPLY_OBJECT_PREFIX + filename, file);
        }catch (Exception e){
            log.error("", e);
            throw BizRuntimeException.from(ResultCode.ERROR, "文件上传失败");
        }
    }


    /**
     * 上传文件到 OSS
     * @param bucketName
     * @param objectName
     * @param multipartFile
     * @return
     * @throws IOException
     */
    private FileInfo uploadOnce(String bucketName, String objectName, MultipartFile multipartFile) throws IOException {

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, multipartFile.getInputStream());
        oss.putObject(putObjectRequest);

        FileInfo fileInfo = new FileInfo();
        fileInfo.setUri(
                UriComponentsBuilder
                .fromUriString(BUCKET_ENDPOINT.toUriString())
                .path(objectName)
                .toUriString()
        );

        return fileInfo;
    }


    @Override
    public void destroy() throws Exception {


        // 关闭OSSClient。
        oss.shutdown();

    }


}
