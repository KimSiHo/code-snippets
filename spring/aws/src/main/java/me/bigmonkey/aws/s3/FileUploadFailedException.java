package me.bigmonkey.aws.s3;

public class FileUploadFailedException extends RuntimeException {

    public FileUploadFailedException() {
    }

    public FileUploadFailedException(String message) {
        super(message);
    }

    public FileUploadFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUploadFailedException(Throwable cause) {
        super(cause);
    }
}
