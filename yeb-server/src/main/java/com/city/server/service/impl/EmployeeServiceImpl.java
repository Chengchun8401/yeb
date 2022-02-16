package com.city.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.server.dao.mapper.EmployeeMapper;
import com.city.server.dao.mapper.MailLogMapper;
import com.city.server.dao.pojo.Employee;
import com.city.server.dao.pojo.MailLog;
import com.city.server.service.EmployeeService;
import com.city.server.utils.DateConverter;
import com.city.server.vo.MailConstants;
import com.city.server.vo.PageVo;
import com.city.server.vo.Result;
import com.city.server.vo.param.PageParam;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MailLogMapper mailLogMapper;

    @Override
    public Result getEmployeeByPage(PageParam pageParam) {
        PageVo pageVo = new PageVo();
        // 开启分页
        Page<Employee> page = new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
        IPage<Employee> employeeByPage = employeeMapper.getEmployeeByPage(page, pageParam.getEmployee(), DateConverter.dateParse(pageParam.getBeginDateScope()));

        // 设置返回值
        pageVo.setTotal(employeeByPage.getTotal());
        pageVo.setData(employeeByPage.getRecords());

        return Result.success(pageVo);
    }

    @Override
    public Result getMaxWorkId() {
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workID)"));
        // 拿到表中最大的workId + 1
        return Result.success(String.format("%08d",Integer.parseInt(maps.get(0).get("max(workID)").toString()) + 1));
    }

    @Override
    public Result addEmployee(Employee employee) {
        // 处理合同期限，保留两位小数
        long days = employee.getBeginContract().until(employee.getEndContract(), ChronoUnit.DAYS);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days/365.00)));

        if(employeeMapper.insert(employee) > 0){
            /*// 发送入职邮件
            Employee emp = employeeMapper.getEmployee(employee.getId()).get(0);

            // 数据库记录发送的消息
            String msgId = UUID.randomUUID().toString();
            MailLog mailLog = new MailLog();
            mailLog.setMsgId(msgId);
            mailLog.setEid(emp.getId());
            mailLog.setStatus(0);
            mailLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailLog.setRouteKey(MailConstants.MAIL_ROUTER_KEY_NAME);
            mailLog.setCreateTime(LocalDateTime.now());
            mailLog.setUpdateTime(LocalDateTime.now());
            mailLog.setCount(0);
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT));
            mailLogMapper.insert(mailLog);

            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTER_KEY_NAME,emp,new CorrelationData(msgId));*/

            return Result.success("添加成功",null);
        }
        return Result.fail(10086,"添加失败");
    }

    @Override
    public Result updateEmployee(Employee employee) {
        if(employeeMapper.updateById(employee) > 0){
            return Result.success("更新成功",null);
        }
        return Result.fail(10086,"更新失败");
    }

    @Override
    public Result deleteEmployee(Integer id) {
        if(employeeMapper.deleteById(id) > 0){
            return Result.success("删除成功",null);
        }
        return Result.fail(10086,"删除失败");
    }

    @Override
    public List<Employee> getEmployee(Integer id) {
        return employeeMapper.getEmployee(id);
    }

    @Override
    public Result getEmployeeWithSalary(Integer currentPage, Integer pageSize) {
        // 开启分页
        Page<Employee> page = new Page<>(currentPage,pageSize);
        IPage<Employee> employeePage = employeeMapper.getEmployeeWithSalary(page);
        return Result.success(new PageVo(employeePage.getTotal(),employeePage.getRecords()));
    }


}
