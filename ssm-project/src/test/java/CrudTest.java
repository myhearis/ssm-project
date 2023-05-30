import com.atsu.pojo.Employee;
import com.atsu.service.EmployeeService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.executor.BatchExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname CrudTest
 * @author: 我心
 * @Description:
 * @Date 2022/7/29 19:11
 * @Created by Lenovo
 */
//导如web支持
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
//导入Spring和Springmvc的配置文件
@ContextConfiguration( locations = {"classpath:applicationContext.xml","classpath:Springmvc.xml"})
public class CrudTest {
    @Autowired
    WebApplicationContext mvcContext;//SpringMVC的ioc容器
    MockMvc mockMvc;//虚拟mvc请求，获取处理结果
    @Autowired
    private EmployeeService employeeService;


    //初始化虚拟mvc请求，每次请求之前都需要初始化
    public void initMockMVC(){
        mockMvc= MockMvcBuilders.webAppContextSetup(mvcContext).build();
    }
  //测试内容:测试分页
    @Test
    public void myTest1(){
        PageInfo<Employee> employeePages = employeeService.getEmployeePages(1, 5, 10);
        List<Employee> list = employeePages.getList();
        for (Employee employee : list) {
            System.out.println(employee.getEmployeeName());
        }
    }
  //测试内容:测试web请求的分页
    @Test
    public void myTest2() throws Exception {
        initMockMVC();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/emps/4")).andReturn();
        MockHttpServletRequest request = mvcResult.getRequest();
        //获取分页对象
        PageInfo<Employee> employeePages = (PageInfo<Employee>) request.getAttribute("employeePages");
        List<Employee> employeeList = employeePages.getList();
        int[] navigatepageNums = employeePages.getNavigatepageNums();
        //展示导航栏
        for (int i=0;i<navigatepageNums.length;i++){
            //判断是否为当前页码
            int pageNum = employeePages.getPageNum();
            if (navigatepageNums[i]==pageNum){
                System.out.print("["+navigatepageNums[i]+"]");
            }
            else
                System.out.print(navigatepageNums[i]);
            if (i+1!=navigatepageNums.length){
                System.out.print(" ");
            }
        }
        System.out.println();

        System.out.println("共有"+employeePages.getPages()+"页");
        //展示员工
        for (Employee employee : employeeList) {
            System.out.println(employee.getEmployeeName());
        }
    }
  //测试内容:测试employeeService
    @Test
    public void myTest3(){
        boolean nameisExist = employeeService.employeeNameisExist("白小纯");
        System.out.println(nameisExist);
    }
  //测试内容:测试后端校验
    @Test
    public void myTest4() throws Exception {
        initMockMVC();

    }
  //测试内容:测试选择性修改员工
    @Test
    public void myTest5(){
        Employee employee=new Employee();
        employee.setEmployeeId(1);
        employee.setEmployeeAge(24);
        System.out.println(employee);
        System.out.println(employeeService.updateEmployee(employee));

    }
  //测试内容:查询单个员工
    @Test
    public void myTest6(){
        Employee employeeById = employeeService.getEmployeeById(1);
        System.out.println(employeeById.getDepartment());
    }
  //测试内容:删除某一个员工
    @Test
    public void myTest7(){
        employeeService.deleteEmployeeById(121);
    }
  //测试内容:测试批量删除
    @Test
    public void myTest8(){
        List<Integer> list=new ArrayList<>();
        list.add(127);
        list.add(133);
        list.add(134);
        employeeService.deleteEmployeeList(list);
    }
}
