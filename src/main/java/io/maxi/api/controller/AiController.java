package io.maxi.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.maxi.api.response.Result;
import io.maxi.api.utils.ImageConvertBase64;
import io.maxi.api.utils.SecurityUtil;
import org.apache.commons.io.FileUtils;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author night
 * @date
 */

@RestController
@CrossOrigin
public class AiController {
    private static final Logger log = LoggerFactory.getLogger(AiController.class);
    @Autowired
    private RestTemplate restTemplate;


    @PostMapping("/api/v1/public/ai/image")
    public Result<List<String>> image(@RequestParam("text") String text, @RequestParam("address") String address, @RequestPart(name="img",required = false) MultipartFile img) {

        // 校验地址点数

        String imageMessage = null;
        if(img == null){
            imageMessage = textToImage(text,address);
        }else{
            imageMessage = imageToImage(text,address,img);
        }
        JSONArray array = JSONObject.parseObject(imageMessage).getJSONArray("artifacts");
        List<String> imageList = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            String base64 = jsonObject.getString("base64");
            Integer seed = jsonObject.getInteger("seed");
            String saveFileName = SecurityUtil.sha256(address+System.currentTimeMillis()+seed)+seed+".png";
            ImageConvertBase64.toImage(base64,new File(saveFileName));
            String host = "https://api.maixtest.shop/image/";
            imageList.add(host+saveFileName);
        }
        return Result.success(imageList);
    }



    @GetMapping("/api/v1/public/ai/points")
    public Result<String> points(@RequestParam("address") String address) {

        return Result.success("10");
    }

    @GetMapping("/api/v1/public/ai/points/consume")
    public Result<String> pointsConsume(@RequestParam("amount") String amount) {

        return Result.success("10");
    }


    public String imageToImage( String text, String address,  MultipartFile img) {
        try {
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            String fileName = address + System.currentTimeMillis() + img.getOriginalFilename();
            FileUtils.writeByteArrayToFile(new File(fileName), img.getBytes());
            FileSystemResource fileResource = new FileSystemResource(new File(fileName));
            param.add("init_image", fileResource);
            String url = "https://api.stability.ai/v1beta/generation/stable-diffusion-512-v2-0/image-to-image";

            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", "application/json");
            headers.add("Authorization", "Bearer sk-HbWRClYFd8sQha81HDXGsyAHfQt7dpx9g5R5wNAf0t499EsA");
            headers.setContentType(MediaType.parseMediaType("multipart/form-data"));

            param.add("init_image_mode", "IMAGE_STRENGTH");
            param.add("image_strength", 0.35);
            param.add("text_prompts[0][text]", "A galactic dog in space");
            param.add("cfg_scale", 7);
            param.add("clip_guidance_preset", "FAST_BLUE");
            param.add("height", 512);
            param.add("width", 512);
            param.add("samples", 2);
            param.add("steps", 11);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(param, headers);
            ResponseEntity<String> result = restTemplate.postForEntity(url, requestEntity, String.class);

            return result.getBody();
        } catch (IOException e) {
            log.error("imageToImage error",e);
        }
        return "";
    }


    public String textToImage( String text,  String address) {
        String url = "https://api.stability.ai/v1beta/generation/stable-diffusion-512-v2-0/text-to-image";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Authorization", "Bearer sk-HbWRClYFd8sQha81HDXGsyAHfQt7dpx9g5R5wNAf0t499EsA");
        headers.setContentType(MediaType.parseMediaType("application/json"));

        JSONObject textObj = new JSONObject();
        textObj.put("text", text);

        JSONArray array = new JSONArray();
        array.add(textObj);

        JSONObject json = new JSONObject();
        json.put("text_prompts", array);
        json.put("cfg_scale", 7);
        json.put("clip_guidance_preset", "FAST_BLUE");
        json.put("height", 512);
        json.put("width", 512);
        json.put("samples", 2);
        json.put("steps", 11);

        HttpEntity<String> formEntity = new HttpEntity<String>(json.toString(), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, formEntity, String.class);

        return result.getBody();
    }


}
