package com.xtx.reggie_take_out.controller;


import com.xtx.reggie_take_out.common.R;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;

    // 上传照片
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        log.info("上传照片:{}", file.getOriginalFilename());
        // 原始文件名
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 使用uuid重新生成文件名
        String name = UUID.randomUUID().toString() + suffix;
        File dir = new File(basePath);
        // 判断当前目录是否存在
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(basePath + name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(name);
    }

    // 下载照片
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws FileNotFoundException {
        try {
            // 输入流，通过输入流来读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            // 输出流，通过输出流来写回浏览器，在浏览器展示图片
            ServletOutputStream outputStream = response.getOutputStream();


            response.setContentType("img/jpeg");
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
                outputStream.flush();
            }
            fileInputStream.close();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}