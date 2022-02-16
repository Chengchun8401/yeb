package com.city.server;


import com.city.server.dao.mapper.DepartmentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class SecurityTest {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Test
    void demo2(){
        System.out.println(departmentMapper.getAllDepartments(-1));
    }

}
