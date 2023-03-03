package io.maxi.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.maxi.api.response.Result;
import io.maxi.api.utils.ImageConvertBase64;
import io.maxi.api.utils.SecurityUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author night
 * @date
 */
@RestController
@CrossOrigin
public class ImageController {
    private static final Logger log = LoggerFactory.getLogger(ImageController.class);


    @GetMapping("/image/{name}")
    public String fileDownLoad(HttpServletResponse response, @PathVariable("name") String name){

        if(!name.endsWith(".png")){
            return "404 File Not Exist";
        }

        File file = new File(name);
        if(!file.exists()){
            return "404 File Not Exist";
        }
        try(InputStream inputStream = new FileInputStream(new File(name));
            OutputStream outputStream = response.getOutputStream();) {
            //设置内容类型为下载类型
            response.setContentType("application/x-download");
            //设置请求头 和 文件下载名称
            response.addHeader("Content-Disposition","attachment;filename="+name);
            //用 common-io 工具 将输入流拷贝到输出流
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
        } catch (Exception e) {
            log.error("fileDownLoad error",e);
            return "404 File Not Exist";
        }
        return "Success";
    }


}
