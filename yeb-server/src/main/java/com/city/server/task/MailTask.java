package com.city.server.task;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.city.server.dao.pojo.Employee;
import com.city.server.dao.pojo.MailLog;
import com.city.server.service.EmployeeService;
import com.city.server.service.MailLogService;
import com.city.server.vo.MailConstants;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 邮件发送定时任务
 */
@Component
public class MailTask {

    @Autowired
    private MailLogService mailLogService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 邮件发送定时任务
     * 10分钟执行一次
     * 待优化：
     *   频繁操作数据库，降低了服务器性能，有可能造成mysql的堵塞
     */
    @Scheduled(cron = "0 */10 * * * ?")
    public void mailTask(){
        List<MailLog> mailLogs = mailLogService.list(new LambdaQueryWrapper<MailLog>()
                .eq(MailLog::getStatus, 0)
                .lt(MailLog::getTryTime, LocalDateTime.now()));

        mailLogs.forEach(mailLog -> {
            // 如果重试次数不低于既定次数，更新为发送失败状态
            if(mailLog.getCount() >= MailConstants.MAX_TRY_COUNT){
                mailLogService.update(new UpdateWrapper<MailLog>().set("status",MailConstants.FAILURE).eq("msgId",mailLog.getMsgId()));
            }
            mailLogService.update(new UpdateWrapper<MailLog>()
                    .set("count",mailLog.getCount()+1)
                    .set("updateTime",mailLog.getUpdateTime())
                    .set("tryTime",LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT))
                    .eq("msgId",mailLog.getMsgId()));
            Employee emp = employeeService.getEmployee(mailLog.getEid()).get(0);
            // 重新发送邮件
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTER_KEY_NAME, emp, new CorrelationData(mailLog.getMsgId()));
        });
    }

}
