package com.atsu.service;

import com.atsu.pojo.Department;

import java.util.List;

/**
 * @Classname DepartmentService
 * @author: 我心
 * @Description:
 * @Date 2022/7/31 19:06
 * @Created by Lenovo
 */
public interface DepartmentService {
    //获取所有部门的对象
    List<Department> getAllDepartments();
}
