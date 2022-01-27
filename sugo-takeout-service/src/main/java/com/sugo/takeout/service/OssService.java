package com.sugo.takeout.service;


import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

public interface OssService {

   default String upload() {return null; }

   default String upload(@NonNull File file) throws Exception {return null;}

   default String upload(@NonNull byte [] data , @NonNull String contentType) throws Exception {return null;}

   default String upload(@NonNull String url) throws Exception {return null;}

   default String upload(@NonNull InputStream inputStream,@NonNull String contentType) throws Exception {return null;}

   default String upload(@NonNull MultipartFile file){return null;}

}
