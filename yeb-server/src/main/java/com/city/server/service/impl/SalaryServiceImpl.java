package com.city.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.server.dao.mapper.SalaryMapper;
import com.city.server.dao.pojo.Salary;
import com.city.server.service.SalaryService;
import org.springframework.stereotype.Service;

@Service
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper,Salary> implements SalaryService {
}
