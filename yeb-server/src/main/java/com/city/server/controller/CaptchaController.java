package com.city.server.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
public class CaptchaController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @ApiOperation("验证码")
    @GetMapping(value = "/captcha", produces = "image/jpeg")  // 写死，让接口文档也可以看到生成结果
    public void captcha(HttpServletRequest request, HttpServletResponse response){
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String text = defaultKaptcha.createText();
        /*
        * 将验证码放入Session
        * 我的优化
        *   1.减小服务器压力，将验证码放入redis
        *       1.redis中的key就是验证码，方便后续拿取
        *       2.设置过期时间(1分钟)
        *   2.创建线程池，输出验证码的工作另起一个线程
        *       1.不能开启ThreadService服务，开启多线程时，response在controller层才能正常工作
        *
        */

        // request.getSession().setAttribute("captcha", text);
        postCaptcha(request, response, text);
    }


    @Async("taskExecutor")  // 从线程池中获取线程，不会影响主线程
    public void postCaptcha(HttpServletRequest request, HttpServletResponse response, String text){
        redisTemplate.opsForValue().set(text, text, 5, TimeUnit.MINUTES);

        BufferedImage image = defaultKaptcha.createImage(text);

        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
