package com.atsu.controller;

import com.atsu.mapper.EmployeeMapper;
import com.atsu.pojo.Department;
import com.atsu.pojo.Employee;
import com.atsu.pojo.Msg;
import com.atsu.service.DepartmentService;
import com.atsu.service.EmployeeService;
import com.atsu.util.NullUtil;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mysql.cj.xdevapi.JsonArray;
import jdk.nashorn.internal.parser.TokenType;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname EmployeeController
 * @author: 我心
 * @Description:
 * @Date 2022/7/28 17:03
 * @Created by Lenovo
 */
@Controller
public class EmployeeController {
    private static final Integer PAGE_SIZE=10;//默认的分页大小
    private static final Integer NAVIGATE_PAGES=5;//默认分页的导航栏大小
    @Autowired
    EmployeeService employeeService;

    @GetMapping(value = "/emps/{pageNo}")
    @ResponseBody
   //@ResponseBody:将该方法的返回值转化为json对象，作为响应体返回
    public Msg getEmployees1(Model model, @PathVariable("pageNo") Integer pageNo, @RequestParam(value = "pageSize",required = false) Integer pageSize,HttpServletRequest request){
        System.out.println(pageSize);
        ServletContext context = request.getServletContext();
        //获取对应的分页信息
        PageInfo<Employee> employeePages = employeeService.getEmployeePages(pageNo, NAVIGATE_PAGES, NullUtil.getNotNullInt(pageSize,PAGE_SIZE));
        return Msg.successMsg("查询成功！").addAttribute("employeePages",employeePages).addAttribute("baseUrl",context.getAttribute("baseUrl"));
    }
    @PostMapping("/emps/")
    @ResponseBody
    //处理保存员工请求@Valid开启jsr303校验  BindingResult result是校验之后产生的结果对象
    public Msg saveEmployee(@Valid Employee employee, BindingResult result){
        //先判断校验结果,如果失败，则不执行添加操作
        if (result.hasErrors()){
            Map<String,Object> map=new HashMap<>();
            //获取所有属性的错误信息
            List<FieldError> fieldErrors = result.getFieldErrors();
            System.out.println("错误信息");
            for (FieldError error : fieldErrors) {
                System.out.println(error.getField()+":"+error.getDefaultMessage());
                map.put(error.getField(),error.getDefaultMessage());
            }
            //返回执行失败的Msg
            return Msg.failMsg("执行失败").addAttribute("errorMap",map);
        }
        else {
            System.out.println(employee);
            Integer integer = employeeService.addEmployee(employee);
            System.out.println(integer);
            return Msg.successMsg("添加成功！");
        }


    }
    //处理查看当前用户名是否存在的请求
    @GetMapping("/emps/employeeNameIsExist/{employeeName}")
    @ResponseBody
    public Msg employeeNameIsExist(@PathVariable("employeeName") String empName){
        //校验用户名是否符合规则
        Pattern compile = Pattern.compile("^[\u4e00-\u9fa5a-zA-Z0-9_-]{3,16}$");
        Matcher matcher = compile.matcher(empName);

        boolean matches = matcher.matches();//是否符合格式
        String returnMsg="";
        Msg msg=null;
        boolean nameisExist = true;
        //如果格式正确
        if (matches){
           nameisExist = employeeService.employeeNameisExist(empName);
            //如果存在
            if (nameisExist){
                returnMsg="该用户名已经存在!";
            }
            //不存在
            else {
                returnMsg="用户名可用";
            }
        }
        else
            returnMsg="用户名应为中文或英文长度为3-16位";

        msg = Msg.successMsg(returnMsg).addAttribute("employeeNameisExist",nameisExist);
        System.out.println(msg);
        return msg;
    }
    //处理修改用户的请求
    @PutMapping("/emp/{employeeId}")
    @ResponseBody
    public Msg alterEmployee(Employee employee,@PathVariable("employeeId") Integer employeeId) throws Exception {
        Msg msg=Msg.successMsg("修改成功！");
        //将获取的id写入对象中
        employee.setEmployeeId(employeeId);
        System.out.println(employee);
        //执行修改操作
        try {
            employeeService.updateEmployee(employee);
        }
        catch (Exception e){
            //如果出现异常，则返回失败的Msg对象
            msg=Msg.failMsg("修改失败，请检查信息或格式是否正确!");
            throw new Exception();//继续抛异常，给给Spring事务管理器捕获
        }

        return msg;
    }
    //处理查询某个员工的请求
    @GetMapping("/emp/{employeeId}")
    @ResponseBody
    public Msg getEmployeeById(@PathVariable("employeeId") Integer employeeId){
        Msg msg=Msg.successMsg("查询成功");
        Employee employeeById = employeeService.getEmployeeById(employeeId);
        //写入数据
        msg.addAttribute("employee",employeeById);
        return msg;
    }
    //处理删除单个员工的请求
    @DeleteMapping("/emp/{employeeId}")
    @ResponseBody
    public Msg deleteEmpById(@PathVariable("employeeId") Integer employeeId){
        employeeService.deleteEmployeeById(employeeId);
        return Msg.successMsg("删除成功!");
    }
    //处理批量删除请求
    @DeleteMapping("/emps/")
    @ResponseBody
    public Msg deleteList( @RequestParam("jsonStr") String jsonStr){
        ObjectMapper objectMapper=new ObjectMapper();
        //转换对象
        Gson gson=new Gson();

        List<Integer> idList = gson.fromJson(jsonStr, new TypeToken<List<Integer>>() {
        }.getType());
        System.out.println(idList);
        //进行删除
        Integer integer = employeeService.deleteEmployeeList(idList);
        return Msg.successMsg("成功删除"+integer+"条数据");
    }
}
