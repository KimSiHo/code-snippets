package me.bigmonkey.aws.s3;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/s3")
@RestController
public class S3Controller {

    private final AwsS3Service awsS3Service;

    @PostMapping("/upload")
    public String uploadFile(
        @RequestParam("category") String category,
        @RequestPart(value = "file") MultipartFile multipartFile) {
        return awsS3Service.uploadFileV1(category, multipartFile);
    }
}
