package com.atsu.service;

import com.atsu.mapper.EmployeeMapper;
import com.atsu.pojo.Employee;
import com.atsu.pojo.EmployeeExample;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname EmployeeServiceImpl
 * @author: 我心
 * @Description:
 * @Date 2022/7/28 17:58
 * @Created by Lenovo
 */
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private SqlSession sessionTemplate;
    //查询所有员工
    @Override
    public List<Employee> getAllEmployee() {

        List<Employee> list = employeeMapper.selectByExample(null);
//        PageInfo<Employee> pageInfo=new PageInfo<>(list,5);
        return list;
    }
    //分页查询员工
    @Override
    public PageInfo<Employee> getEmployeePages(Integer pageNo, Integer navigateSize, Integer pageSize) {
        //开启逻辑分页
        PageHelper.startPage(pageNo,pageSize);
        //查询所有
        List<Employee> list = employeeMapper.selectByExample(null);
        PageInfo<Employee> pageInfo=new PageInfo<>(list,navigateSize);
        return pageInfo;
    }

    @Override
    public Integer addEmployee(Employee employee) {
        return employeeMapper.insertSelective(employee);
    }

    @Override
    public boolean employeeNameisExist(String employeeName) {
        //通过用户名查找员工
        EmployeeExample employeeExample=new EmployeeExample();
        employeeExample.createCriteria().andEmployeeNameEqualTo(employeeName);
        List<Employee> employeeList = employeeMapper.selectByExample(employeeExample);
        //存在则返回真
        if (employeeList.size()>0)
            return true;
        return false;
    }

    @Override
    public Integer updateEmployee(Employee employee) {

        //使用选择性修改，如果为null则不修改
        return  employeeMapper.updateByPrimaryKeySelective(employee);
    }

    @Override
    public Employee getEmployeeById(Integer employeeId) {
        return employeeMapper.selectByPrimaryKey(employeeId);
    }

    @Override
    public Integer deleteEmployeeById(Integer employeeId) {

        return employeeMapper.deleteByPrimaryKey(employeeId);
    }
    @Transactional
    @Override
    public Integer deleteEmployeeList(List<Integer> employeeIdList) {
        //获取批量删除的模板
        EmployeeMapper mapper = sessionTemplate.getMapper(EmployeeMapper.class);
        for (Integer id : employeeIdList) {
            mapper.deleteByPrimaryKey(id);
        }
        return employeeIdList.size();
    }


}
