package com.atsu.pojo;

import javax.validation.constraints.Pattern;

public class Employee {
    private Integer employeeId;
    @Pattern(regexp = "^[\u4e00-\u9fa5a-zA-Z0-9_-]{3,16}$",
            message = "用户名应为中文或英文长度为3-16位")
    private String employeeName;
    @Pattern(regexp = "[男女]",message = "性别错误!")
    private String employeeGender;

    private Integer employeeAge;
    @Pattern(regexp = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$",message = "邮箱格式有误！")
    private String employeeEmail;

    private Integer departmentId;

    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName == null ? null : employeeName.trim();
    }

    public String getEmployeeGender() {
        return employeeGender;
    }

    public void setEmployeeGender(String employeeGender) {
        this.employeeGender = employeeGender == null ? null : employeeGender.trim();
    }

    public Integer getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(Integer employeeAge) {
        this.employeeAge = employeeAge;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail == null ? null : employeeEmail.trim();
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", employeeGender='" + employeeGender + '\'' +
                ", employeeAge=" + employeeAge +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", departmentId=" + departmentId +
                '}';
    }
}