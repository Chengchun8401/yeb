package com.city.mail;

import com.city.server.dao.pojo.Employee;
import com.city.server.vo.MailConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
@Slf4j
public class MailReceiver {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private TemplateEngine templateEngine;

    @RabbitListener(queues = MailConstants.MAIL_QUEUE_NAME)
    public void handler(Employee employee){
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);

        try {
            // 发件人
            helper.setFrom(mailProperties.getUsername());
            // 收件人
            helper.setTo(employee.getEmail());
            // 主题
            helper.setSubject("入职欢迎邮件");
            // 发送日期
            helper.setSentDate(new Date());
            // 邮件内容
            Context context = new Context();
            context.setVariable("name",employee.getName());
            context.setVariable("posName",employee.getPosition().getName());
            context.setVariable("jobLevelName",employee.getJobLevel().getName());
            context.setVariable("depName",employee.getDepartment().getName());
            String mail = templateEngine.process("mail", context);
            helper.setText(mail, true);
            // 发送邮件
            mailSender.send(msg);
        } catch (MessagingException e) {
            log.error("邮件发送失败=======>{}", e.getMessage());
        }
    }


}
