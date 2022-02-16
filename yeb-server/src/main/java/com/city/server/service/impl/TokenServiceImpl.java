package com.city.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.city.server.dao.pojo.Admin;
import com.city.server.service.TokenService;
import com.city.server.utils.JWTUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public UserDetails checkToken(String token) {
        if(Strings.isBlank(token)){ // token 为空
            return null;
        }

        Map<String, Object> map = JWTUtils.checkToken(token);
        if(map == null){ // token 解析失败
            return null;
        }

        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if(Strings.isBlank(userJson)){ // token已过期
            return null;
        }

        // UserDetails本身是个接口，接收参数的实体类需要实现了UserDetails的实体类，不然参数报空
        UserDetails user = JSON.parseObject(userJson, Admin.class);
        return user;
    }
}
