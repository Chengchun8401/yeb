package com.city.server.config.security.component;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.city.server.dao.pojo.Menu;
import com.city.server.dao.pojo.Role;
import com.city.server.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomerFilter implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 先查询所有菜单以及对应角色，如果请求的url与菜单的url匹配，就把调用该菜单需要的权限角色加入Security的权限配置中
     * @param object
     * @return Collection<ConfigAttribute>
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取请求的url
        String url = ((FilterInvocation) object).getRequestUrl();
        /* 频繁获取，建议走缓存
         *   1. new TypeReference 自定义 parseObject 的转换类型   com.alibaba.fastjson.TypeReference
         *   2. 修改CacheAspect中的固定返回值类型，动态获取原方法的返回值类型
         *        Class<?> returnType = ((MethodSignature) pjp.getSignature()).getMethod().getReturnType();
         */
        List<Menu> menus = JSON.parseObject(JSON.toJSONString(menuService.getMenusWithRole()) , new TypeReference<ArrayList<Menu>>(){});

        for (Menu menu : menus) {
            // 判断请求url与菜单角色是否匹配
            if(antPathMatcher.match(menu.getUrl(), url)){
                String[] str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(str); // 将访问匹配菜单所需的角色加入SecurityConfig的Attributes中
            }
        }
        // 没匹配的url给一个默认的登录角色，登录即可访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
