package io.maxi.api.controller;

import io.maxi.api.aop.NoCheckLOgin;
import io.maxi.api.config.RedisSwapper;
import io.maxi.api.config.constant.CacheKeyConstant;
import io.maxi.api.dao.MaxiHomeSlideshowMapper;
import io.maxi.api.entity.MaxiHomeSlideshow;
import io.maxi.api.request.CommonRequest;
import io.maxi.api.response.Result;
import io.maxi.api.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.AnnotatedType;
import java.util.*;

/**
 * @author night
 * @date 2018年4月16日 下午1:22:26
 */

@RestController
@CrossOrigin
@NoCheckLOgin
public class CommonController {
	private static final Logger log = LoggerFactory.getLogger(CommonController.class);


    @Autowired
    MaxiHomeSlideshowMapper maxiHomeSlideshowMapper;

    @Autowired
    RedisSwapper redisSwapper;

    @Value("${spring.datasource.url}")
    String url;


    @RequestMapping("/")
    public void home(HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
        //response.sendRedirect("/my.html");
        request.getRequestDispatcher("/hello").forward(request, response);
    }

    @RequestMapping("/hello")
    public String index() {
        log.info("request path /hello  url:{} ", url);
        return "Hello World";
    }

	@RequestMapping("/error")
	public String error() {
		log.error("request path /error ");
		return "";
	}
	@RequestMapping("/404")
	public String error404() {
		log.error("request path /error ");
		return "";
	}
	@RequestMapping("/500")
	public String error500() {
		log.error("request path /error ");
		return "";
	}


    @RequestMapping("/bean")
    public Map<String, Object> bean() {
		log.info("request path /bean");
		Map<String, Object> m = new HashMap<>();
		m.put("1", 123);
		m.put("value", "value");
		m.put("date", new Date());
		m.put("datestr", DateUtil.formatDate(new Date()));
		return m;
	}

	
	@RequestMapping("/mysql")
	public Result mysql() {
		List<MaxiHomeSlideshow> userList = maxiHomeSlideshowMapper.selectList(null);

		return Result.success(userList);
	}

	
	@RequestMapping("/redis")
	public Result redis() {
		String key = String.valueOf(System.currentTimeMillis());
		String result = redisSwapper.setex(key,key,10);
		log.info("redis result : {} " , result);
		return Result.success(result);
	}

	@RequestMapping("/health")
	public Result health() {
		mysql();
		redis();
		return Result.success();
	}

	
	@RequestMapping("/excep")
	public Result excep() {
		throw  new RuntimeException("exception test");
	}



	@NoCheckLOgin
	@RequestMapping("/cleankey")
	public Result treeCancel(@RequestBody CommonRequest request) {

    	String key = request.getCleanKey();

		//redisSwapper.cleanKey(key);

		redisSwapper.publish(CacheKeyConstant.REDIS_SUB_CLEAN_KEY_ALL,key);

		return Result.success(key);
	}



	public static void main(String[] args) {
		System.out.println(CommonController.class.getDeclaredAnnotation(NoCheckLOgin.class));

		AnnotatedType[] types =  CommonController.class.getAnnotatedInterfaces();
		for (AnnotatedType type : types) {
			System.out.println(type.getType().getTypeName());
		}

	}

}
