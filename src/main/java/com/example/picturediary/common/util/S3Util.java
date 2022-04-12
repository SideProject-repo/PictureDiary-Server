package com.example.picturediary.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.picturediary.common.enums.ErrorCodes;
import com.example.picturediary.common.exception.customerror.CustomError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class S3Util
{
    private static final String bucketName = "giljob";
    private AmazonS3 amazonS3;

    @Autowired
    public S3Util(AmazonS3 amazonS3)
    {
        this.amazonS3 = amazonS3;
    }

    public String fileUpload(MultipartFile file)
    {
        String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());

        try {
            amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);
        }
        catch(Exception e) {
            throw new CustomError(ErrorCodes.FILE_UPLOAD_ERROR);
        }

        return amazonS3.getUrl(bucketName, fileName).toString();
    }
}
