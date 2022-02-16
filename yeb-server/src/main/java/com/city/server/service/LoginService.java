package com.city.server.service;

import com.city.server.vo.Result;
import com.city.server.vo.param.LoginParam;

public interface LoginService {

    Result login(LoginParam loginParam);

    Result logout(String token);

}
