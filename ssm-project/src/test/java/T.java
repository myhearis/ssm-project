import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

import com.atsu.mapper.DepartmentMapper;
import com.atsu.mapper.EmployeeMapper;
import com.atsu.pojo.Department;
import com.atsu.pojo.Employee;
import com.atsu.service.EmployeeService;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Classname T
 * @author: 我心
 * @Description:
 * @Date 2022/7/27 12:12
 * @Created by Lenovo
 */

public class T {

    
    private ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
    private EmployeeMapper employeeMapper=context.getBean(EmployeeMapper.class);
  //测试内容:获取数据库连接池
    @Test
    public void myTest2() throws SQLException {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        DruidDataSource dataSource = context.getBean("dataSource", DruidDataSource.class);
        System.out.println(dataSource);
        DruidPooledConnection connection = dataSource.getConnection();
        System.out.println(connection);
    }
  //测试内容:运行逆向工程
    @Test
    public void myTest3() throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //指向逆向工程配置文件
        File configFile = new File(T.class.getResource("generatorConfig.xml").getFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);

    }

  //测试内容:查询员工以及对应的部门信息
    @Test
    public void myTest4(){
        EmployeeMapper employeeMapper = context.getBean(EmployeeMapper.class);
        Employee employee = employeeMapper.selectByPrimaryKeyWithDepartment(1);

        System.out.println(employee.getEmployeeName());
        System.out.println(employee.getDepartment().getDepartmentName());
        List<Employee> employeeList = employee.getDepartment().getEmployeeList();
        DepartmentMapper departmentMapper = context.getBean(DepartmentMapper.class);
        Department department = departmentMapper.selectByPrimaryKeyWithEmployees(1);
        System.out.println(department.getEmployeeList().get(0));
    }
  //测试内容:批量插入员工信息

    @Test
    @Transactional
    public void myTest5() throws SQLException {
        SqlSession sessionTemplate = context.getBean("sessionTemplate", SqlSessionTemplate.class);
        EmployeeMapper mapper = sessionTemplate.getMapper(EmployeeMapper.class);
        //手动开启事务
        DataSourceTransactionManager transactionManager = context.getBean("transactionManager", DataSourceTransactionManager.class);
        transactionManager.getTransaction(new DefaultTransactionDefinition());
        //性别随机数
        Random random=new Random();
        for (int i=0;i<=10000;i++){
            Employee employee=new Employee();
            int sexNum=random.nextInt();
            if (sexNum<0)
                sexNum=-1*sexNum;
            employee.setEmployeeAge((i+sexNum)%50);
            sexNum=sexNum%2;
            if (sexNum==0){
                employee.setEmployeeGender("男");
            }
            else
                employee.setEmployeeGender("女");
            employee.setEmployeeName("员工"+i);

            employee.setEmployeeEmail(i+"fadsfgf@qq.com");
            employee.setDepartmentId(i%3+1);
            //添加员工
            mapper.insertSelective(employee);
        }
        //将数据刷入到数据库
        sessionTemplate.flushStatements();
        sessionTemplate.getConnection().commit();

    }
  //测试内容:此时服务层
    @Test
    public void myTest6(){
        EmployeeService bean = context.getBean(EmployeeService.class);
        Employee employees = employeeMapper.selectByPrimaryKeyWithDepartment(1);
        System.out.println(employees.getDepartment().getDepartmentName());
        List<Employee> employeeList = employeeMapper.selectByExample(null);
        System.out.println(employeeList.get(0).getDepartment());
    }

}
