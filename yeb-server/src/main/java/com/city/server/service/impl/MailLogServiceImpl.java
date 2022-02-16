package com.city.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.server.dao.mapper.MailLogMapper;
import com.city.server.dao.pojo.MailLog;
import com.city.server.service.MailLogService;
import org.springframework.stereotype.Service;

@Service
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements MailLogService {

}
