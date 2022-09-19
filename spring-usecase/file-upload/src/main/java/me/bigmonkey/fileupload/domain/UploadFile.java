package me.bigmonkey.fileupload.domain;

import lombok.Data;

@Data
public class UploadFile {

    // 사용자가 업로드 하는 파일 이름
    private String uploadFileName;
    // 시스템에 저장하는 파일 이름
    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
