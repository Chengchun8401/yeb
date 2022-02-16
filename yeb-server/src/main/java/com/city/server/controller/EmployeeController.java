package com.city.server.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.city.server.dao.pojo.*;
import com.city.server.service.*;
import com.city.server.vo.Result;
import com.city.server.vo.param.PageParam;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PoliticsStatusService politicsService;

    @Autowired
    private JobLevelService jobLevelService;

    @Autowired
    private NationService nationService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation("获取所有员工")
    @GetMapping("")
    public Result getEmployee(PageParam pageParam){
        return employeeService.getEmployeeByPage(pageParam);
    }

    @ApiOperation("添加员工")
    @PostMapping("")
    public Result addEmployee(@RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }

    @ApiOperation("更新员工信息")
    @PutMapping("")
    public Result updateEmployee(@RequestBody Employee employee){
        return employeeService.updateEmployee(employee);
    }

    @ApiOperation("删除员工")
    @DeleteMapping("/{id}")
    public Result deleteEmployee(@PathVariable Integer id){
        return employeeService.deleteEmployee(id);
    }

    @ApiOperation("获取所有政治面貌")
    @GetMapping("/politics")
    public Result getAllPoliticStatus(){
        return politicsService.getAllPoliticStatus();
    }

    @ApiOperation("获取所有职称")
    @GetMapping("/jobs")
    public Result getAllJobLevels(){
        return jobLevelService.getAllJobLevels();
    }

    @ApiOperation("获取所有民族")
    @GetMapping("/nations")
    public Result getAllNations(){
        return nationService.getAllNations();
    }

    @ApiOperation("获取所有职位")
    @GetMapping("/positions")
    public Result getAllPositions(){
        return positionService.getAllPositions();
    }

    @ApiOperation("获取所有部门")
    @GetMapping("/departments")
    public Result getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @ApiOperation("获取最大工号")
    @GetMapping("/workId")
    public Result getMaxWorkId(){
        return employeeService.getMaxWorkId();
    }

    @ApiOperation("导出员工数据")
    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void exportEmployee(HttpServletResponse response){
        List<Employee> list = employeeService.getEmployee(null);
        // 表名、打开表后左下角表分页名、导入Excel版本 HSSF 03版，兼容性更好
        ExportParams params = new ExportParams("员工表","员工表", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, Employee.class, list);
        ServletOutputStream outputStream = null;
        try{
            // 以流的形式输出
            response.setHeader("content-type","application/octet-stream");
            // 防止中文乱码
            response.setHeader("content-disposition", "attachment;fileName="+ URLEncoder.encode("员工表.xls","UTF-8"));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation("导入员工信息")
    @PostMapping("/import")
    public Result importEmployee(MultipartFile file){
        ImportParams params = new ImportParams();
        // 去掉标题行
        params.setTitleRows(1);

        /*
        *   插入数据时，减少数据库的查询次数（传入nation.name，根据这个获取到nation.id，插入employee的nationId）
        *       1.重写Nation的hashCode和Equals方法   @EqualsAndHashCode(callSuper = false, of = "name")
        *       2.通过传入的name参数，构造Nation对象
        *       3.利用hashCode和Equals方法，获取当前Nation对象在查出List集合中的索引
        *       4.通过索引获取对应Nation对象的id
        *   思考
        *       该方法有些取巧，nation、politics等字段中的值，设置好后一般不经常变化，所以可以使用
        */

        List<Nation> nationsList = nationService.list();
        List<Position> positionList = positionService.list();
        List<PoliticsStatus> politicsList = politicsService.list();
        List<JobLevel> jobLevelList = jobLevelService.list();
        List<Department> departmentList = departmentService.list();

        try {
            List<Employee> list = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, params);
            list.forEach(employee -> {
                // 民族id
                employee.setNationId(nationsList.get(nationsList.indexOf(new Nation(employee.getNation().getName()))).getId());
                // 职位id
                employee.setPosId(positionList.get(positionList.indexOf(new Position(employee.getPosition().getName()))).getId());
                // 政治面貌id
                employee.setPoliticId(politicsList.get(politicsList.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId());
                // 职称id
                employee.setJobLevelId(jobLevelList.get(jobLevelList.indexOf(new JobLevel(employee.getJobLevel().getName()))).getId());
                // 部门id
                employee.setDepartmentId(departmentList.get(departmentList.indexOf(new Department(employee.getDepartment().getName()))).getId());

            });

            if(employeeService.saveBatch(list)){
                return Result.success("批量导入成功",null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.fail(10086,"批量导入失败");

    }

}
