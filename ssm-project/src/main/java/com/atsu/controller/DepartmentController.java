package com.atsu.controller;

import com.atsu.pojo.Department;
import com.atsu.pojo.Msg;
import com.atsu.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Classname DepartmentController
 * @author: 我心
 * @Description: 处理和部门有关的请求
 * @Date 2022/7/31 19:24
 * @Created by Lenovo
 */
@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    //获取所有部门
    @GetMapping("/allDepartment")
    @ResponseBody
    public Msg getDepartments(){
        //获取部门信息
        List<Department> allDepartments = departmentService.getAllDepartments();
        //创建返回对象
        Msg msg = Msg.successMsg("查询成功！").addAttribute("departmentList", allDepartments);
        return msg;
    }
}
