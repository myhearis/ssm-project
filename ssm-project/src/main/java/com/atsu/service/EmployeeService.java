package com.atsu.service;

import com.atsu.pojo.Employee;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Classname EmployeeService
 * @author: 我心
 * @Description:
 * @Date 2022/7/28 17:57
 * @Created by Lenovo
 */
public interface EmployeeService {
    //查询所有员工
    List<Employee> getAllEmployee();
    //分页查询
    PageInfo<Employee> getEmployeePages(Integer pageNo, Integer navigateSize,Integer pageSize);
    //添加员工
    Integer addEmployee(Employee employee);
    //判断用户名是否存在
    boolean employeeNameisExist(String employeeName);
    //修改用户的方法
    Integer updateEmployee(Employee employee);
    //通过id查询对应的员工信息
    Employee getEmployeeById(Integer employeeId);
    //通过id删除对应的员工
    Integer deleteEmployeeById(Integer employeeId);
    //批量删除对应id的员工
    Integer deleteEmployeeList(List<Integer> employeeIdList);
}
