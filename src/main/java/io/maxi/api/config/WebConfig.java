package io.maxi.api.config;

import org.springframework.context.annotation.Configuration;


@Configuration
public class WebConfig  {

//    @Bean
//    public HttpMessageConverters fastJsonHttpMessageConverters() {
//        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        List<MediaType> mediaTypes = new ArrayList<>();
//        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        fastJsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
//        fastJsonConfig.setSerializerFeatures(
//                SerializerFeature.DisableCircularReferenceDetect,  //禁用循环引用
//                //SerializerFeature.PrettyFormat,
//                SerializerFeature.WriteDateUseDateFormat,
//                SerializerFeature.IgnoreNonFieldGetter
//        );
//        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
//        HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
//        return new HttpMessageConverters(converter);
//    }
}
