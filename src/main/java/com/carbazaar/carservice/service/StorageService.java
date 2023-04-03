package com.carbazaar.carservice.service;

import java.util.List;

public interface StorageService {

    String getSingleFileData(String filePath);

    List<String> getFileData(List<String> filePathList);

}
