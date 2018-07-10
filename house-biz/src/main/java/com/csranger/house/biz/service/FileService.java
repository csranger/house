package com.csranger.house.biz.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 * 将用户的头像保存到本地，需要一个路径，这个路径是ngnix静态文件路径
 */
@Service
public class FileService {

    // 将用户的头像保存到本地的路径，这个路径是nginx静态文件路径
    @Value("${file.path}")
    private String filePath;


    /**
     * 上传文件链表返回对应路径
     * 存在数据库里的只是路径，文件保存到本地 filePath
     * @param files
     * @return
     */
    public List<String> getImgPath(List<MultipartFile> files) {
        List<String> paths = Lists.newArrayList();
        files.forEach(file -> {
            File localFile = null;
            // 上传的文件是否为空
            if (!file.isEmpty()) {
                try {
                    localFile =  saveToLocal(file, filePath);     // file指文件 filePath指静态路径  localFile 指返回的路径
                    String path = StringUtils.substringAfterLast(localFile.getAbsolutePath(), filePath);  // substringAfterLast 返回最后的相对路径
                    paths.add(path);
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        return paths;
    }

    /**
     * 将上传的文件保存到本地，即nginx静态资源路径/Users/hailong/opt/imgs，返回最终的相对路径，相对于imgs
     * @param file
     * @param filePath
     * @return
     */
    public File saveToLocal(MultipartFile file, String filePath) throws IOException {
        File newFile = new File(filePath + "/" + Instant.now().getEpochSecond() + "/" + file.getOriginalFilename());
        // 如果文件不存在，创建上级目录
        if (!newFile.exists()) {
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        // 将上传的文件写到新创建的文件
        Files.write(file.getBytes(), newFile);

        return newFile;
    }
}
