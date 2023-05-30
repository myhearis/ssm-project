package com.atsu.service;

import com.atsu.mapper.DepartmentMapper;
import com.atsu.pojo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname DepartmentServiceImpl
 * @author: 我心
 * @Description:
 * @Date 2022/7/31 19:07
 * @Created by Lenovo
 */
@Service
public class DepartmentServiceImpl implements DepartmentService{
    @Autowired
    private DepartmentMapper departmentMapper;
    @Override
    public List<Department> getAllDepartments() {
        List<Department> departments = departmentMapper.selectByExample(null);
        return departments;
    }
}
