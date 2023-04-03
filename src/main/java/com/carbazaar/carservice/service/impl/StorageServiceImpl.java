package com.carbazaar.carservice.service.impl;

import com.carbazaar.carservice.service.StorageService;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StorageServiceImpl implements StorageService {

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    @Autowired
    private Storage storage;

    @Value("${gcs.bucketName}")
    private String bucketName;
    @Value("${gcs.bucket.subdirectory}")
    private String subdirectory;

    public String getSingleFileData(String filePath) {
        try {
            byte[] byteArray = getStorage().get(constructBlobId(bucketName, subdirectory, filePath)).getContent(Blob.BlobSourceOption.generationMatch());
            return Base64.getEncoder().encodeToString(byteArray);
        } catch(Exception e) {
            return filePath;
        }
    }

    private BlobId constructBlobId(String bucketName, @Nullable String subdirectory,
                                   String fileName) {
        return Optional.ofNullable(subdirectory)
                .map(s -> BlobId.of(bucketName, subdirectory + "/" + fileName))
                .orElse(BlobId.of(bucketName, fileName));
    }

    @Cacheable("imageCache")
    public List<String> getFileData(List<String> filePathList) {
        return filePathList.stream().map(this::getSingleFileData).collect(Collectors.toList());
    }
}
