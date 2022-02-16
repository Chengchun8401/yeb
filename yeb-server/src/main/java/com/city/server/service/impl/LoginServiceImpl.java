package com.city.server.service.impl;


import com.alibaba.fastjson.JSON;
import com.city.server.service.LoginService;
import com.city.server.utils.JWTUtils;
import com.city.server.vo.ErrorCode;
import com.city.server.vo.Result;
import com.city.server.vo.param.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Result login(LoginParam loginParam) {
        /*
         * 1.获取UserDetails
         * 2.拿用户名密码与UserDetails中的信息进行比对
         * 3.密码正确，则登录成功，在security中更新用户对象，并返回一个token给客户端
         * 4.同时 token : user信息  进redis  设置过期时间
         * (下次登录认证时候，先认证token是否合法，再去redis认证是否存在 )
         */

        String code = redisTemplate.opsForValue().get(loginParam.getCode());
        if(!loginParam.getCode().equals(code)){  // 验证码错误
            return Result.fail(ErrorCode.CODE_ERROR.getCode(), ErrorCode.CODE_ERROR.getMsg());
        }

        // 清除使用过的验证码
        redisTemplate.delete(loginParam.getCode());

        String username = loginParam.getUsername();
        String password = loginParam.getPassword();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(userDetails == null){  // 账号不存在
            return Result.fail(ErrorCode.ACCOUNT_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_NOT_EXIST.getMsg());
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())){ // 密码错误
            return Result.fail(ErrorCode.PWD_ERROR.getCode(), ErrorCode.PWD_ERROR.getMsg());
        }

        if(!userDetails.isEnabled()){ // 账号被禁用
            return Result.fail(ErrorCode.ACCOUNT_BANNED.getCode(), ErrorCode.ACCOUNT_BANNED.getMsg());
        }

        // 更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 设置token
        String token = JWTUtils.createToken(userDetails);
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(userDetails), 1, TimeUnit.DAYS);

        return Result.success("登录成功",token);
    }

    /**
     * 退出登录，删除在redis中的token记录
     * @param token
     * @return
     */
    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success("注销成功",null);
    }


}
