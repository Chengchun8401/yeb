package com.city.server.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {

    /**
     * 解析token，返回UserDetails
     * @param token
     * @return
     */
    UserDetails checkToken(String token);

}
