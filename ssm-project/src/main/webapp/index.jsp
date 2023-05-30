<%@ page import="com.github.pagehelper.PageInfo" %>
<%@ page import="com.atsu.pojo.Employee" %><%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2022/7/28
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page isELIgnored="false" %>
<html>


<head>
    <!--jQuery引入-->
    <script src="<%=application.getAttribute("baseUrl")%>lib/static/js/jquery.js"></script>

    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    <script type="text/javascript">
        var totalNum;//保存当前的总记录数
        var add_emp_name_is_ok=false;//用于判断新增用户名是否可用
        var add_emp_name_format_is_ok=false;//用于判断当前用户名格式是否正确，格式正确我们才发起ajax请求查询用户名是否重复
        var thisPageNum=0;//保存当前页码，用于修改用户以后更新对应页面的数据
        var thisUpdateEmployeeId=0;//当前正在操作的员工id
        var pageSize=10;//每页展示的记录数
        //ajax请求分页数据：
        function ajax_getPageData_method(pageUrl) {
            //清理旧数据
            clearOldData();
            //发送ajax请求，获取分页数据
            $.ajax({
                type: "GET",
                url: pageUrl,
                data:"pageSize="+pageSize,
                dataType:"json",
                success: function(msg){
                    var pageInfo = msg.dataMap.employeePages;
                    var baseUrl=msg.dataMap.baseUrl;
                    thisPageNum=pageInfo.pageNum;
                    pageSize=pageInfo.pageSize;
                    // console.log(msg);
                    //1、解析员工信息
                    build_employees_table(msg);
                    //2、解析分页信息
                    page_msg(pageInfo);
                    //3、解析导航栏
                    navigate_msg_view(pageInfo,baseUrl);
                    //将当前的总记录数赋值给js全局变量
                    totalNum=pageInfo.total;
                    //给每个员工的编辑按钮添加监听事件
                    add_editorButton_click();
                    //给每个员工的删除按钮添加监听事件
                    add_one_delete_button_event();
                    //判断当前是否为批量选中状态，进行处理
                    checked_all_box($("#checkAll").prop("checked"));
                    //给每个员工的复选框添加监听事件
                    add_emp_checkedBox_click();

                }

            });
        }
        //获取当前页面中所有被选中的员工id
        function willDeleteemployeeIdList() {
           var listBox = $(".checkbox_select:checked");
           var idList=[];
           for(var i=0;i<listBox.length;i++){
              var id = listBox.eq(i).attr("employeeId");
              idList[i]=parseInt(id);
            }
          return idList;

        }

        //批量删除的按钮监点击事件添加
        function add_deleteList_button_click_event() {
            $("#deleteList_btn").click(function () {
                //获取要删除的id集合
                var idList = willDeleteemployeeIdList();
                if (idList.length==0)
                    return;
                //弹出确认框，取消则直接返回
                if (!confirm("确定删除选中的数据吗？"))
                    return;
                //发起ajax请求,设置_method=delete
                $.ajax({
                    url:"${applicationScope.baseUrl}emps/",
                    data:"jsonStr="+JSON.stringify(idList)+"&_method=delete",
                    type:"post",
                    success:function (msg) {
                        alert(msg.returnMsg);
                        //如果删除成功，则
                        if (msg.stateCode==100){
                            //刷新当前页面
                            ajax_getPageData_method("${applicationScope.baseUrl}emps/"+thisPageNum);
                        }
                    }
                });
            })
        }
        //员工复选框的监听事件
        function add_emp_checkedBox_click() {
            $(".checkbox_select").click(function () {
                //如果没选中，则将全选框置为未选中
               if (!$(this).prop("checked")){
                   $("#checkAll").prop("checked",false);
               }
            });
        }
        //请求，并回显部门信息,部门的下拉框对象
        function get_departments_ajax(reqUrl,selectElement) {
            $.ajax({
                type: "GET",
                url: reqUrl,
                dataType:"json",
                success: function(msg){
                    //部门的下拉框对象
                    var department_select=selectElement;
                    //部门集合
                    var departmentList=msg.dataMap.departmentList;
                    $.each(departmentList,function (index,item) {
                        //创建对应的选择
                        var option=$("<option></option>").append(item.departmentName).attr("value",item.departmentId).appendTo(department_select);
                    })

                }

            });
        }
        //清除当前所有分页更新的数据
        function clearOldData() {
            //清除员工列表
            $("#emp_table tbody").empty();
            //清除分页信息
            $("#pagesTextMsg").empty();
            //清除导航栏
            $("#pageNumView").empty();

        }
        //页面加载完成时
        $(function () {
            //发送ajax请求，获取分页数据
            //展示首页的数据
            ajax_getPageData_method("${applicationScope.baseUrl}emps/1");
            //给新增按钮添加点击事件
            $("#addemployeeBtn").click(function () {
                //获取模态框,并设置属性
                //backdrop:点击背景是否删除
                //keyboard:键盘是否可以控制退出
                $('#myModal').modal({
                    backdrop:"static",
                    keyboard:true
                });

            });
            //给批量删除按钮添加点击事件
            add_deleteList_button_click_event();
            //查询部门数据并加入到模态框中
            get_departments_ajax("${applicationScope.baseUrl}department/allDepartment",$("#departmentSelectList"));
            //获取所有部门信息
            get_departments_ajax("${applicationScope.baseUrl}department/allDepartment",$("#departmentSelectList_update"));
            //每页的记录数修改，并刷新页面
            $("#pageSizeBtn").click(function () {
               //获取文本框的内容
                var updatePageSize = $("#pageSizeNum").val();
                //判断该pageSize格式是否正确以及是否合法
                var re=/[0-9]+/;
                if (re.test(updatePageSize)){
                    if (updatePageSize>0&&updatePageSize<totalNum){
                        //进行修改
                        pageSize=updatePageSize;
                        //刷新页面
                        ajax_getPageData_method("${applicationScope.baseUrl}emps/"+thisPageNum);
                    }
                }
            });
            //新增员工提交按钮监听
            $("#sub_add_employee").click(function () {

                var isSubmit=false;
                //对表单数据格式进行校验
                isSubmit = verify_add_employee_data();
                //如果用户名重名，则直接结束
                if (!add_emp_name_is_ok)
                    return true;
                //获取对应的数据
                var params= $("#form_add_employee").serialize();//将表单中的参数序列化为字符串
               //发起ajax请求
                if (isSubmit){
                    $.ajax({
                        type: "post",
                        url: "${applicationScope.baseUrl}emps/",
                        data:params,
                        dataType:"json",

                        success: function(msg){
                            //判断处理状态 100:成功 200：失败
                            if (msg.stateCode==100){
                                //保存成功以后拍，关闭模态框
                                $("#myModal").modal('hide');
                                //显示最后一页的数据
                                //由于我们开启了分页插件的自动判断，超过总页码会总是显示最后一页的信息，因此我们只需要使用大于总页码的页码跳转即可,这里使用总记录数来跳转
                                clearOldData();
                                ajax_getPageData_method("${applicationScope.baseUrl}emps/"+totalNum);
                                //重置模态框数据
                                rest_addEmp_msg();
                            }
                            else {
                                //有哪个就显示哪一个的错误信息
                                var  errorMap=msg.dataMap.errorMap;
                                if (errorMap.employeeEmail!=undefined){
                                    var father=$("#add_emp_email").parent();
                                    var msgSpan=father.children(".help-block");
                                    clear_and_newverify(father,msgSpan,errorMap.employeeEmail,false);
                                }
                                if (errorMap.employeeName!=undefined){
                                    //修改警告样式
                                    var father=$("#add_emp_name").parent();
                                    var msgSpan=father.children(".help-block");
                                    clear_and_newverify(father,msgSpan,errorMap.employeeName,false);
                                }
                                if (errorMap.employeeGender){
                                    var father=$("#gender_div");
                                    var msgSpan=$("#genderMsg");
                                    clear_and_newverify(father,msgSpan,errorMap.employeeGender,false);
                                }


                            }

                        }

                    });
                }



            });

            //给选择所有的复选框添加点击事件
            $("#checkAll").click(function () {
                //获取当前复选框的状态，选中则为true，否则为false
               var ch = $("#checkAll").prop("checked");
               //批量选中或全不选
               checked_all_box(ch);
            })

            //给用户名框添加内容改变事件
            $("#add_emp_name").change(function () {
                var empName = $("#add_emp_name").val();
                //发起ajax请求
                $.ajax({
                    url:"${applicationScope.baseUrl}emps/employeeNameIsExist/"+empName,
                    type:"get",
                    dataType:"json",
                    success:function (msg) {
                        //获取查询的信息
                        var isExist = msg.dataMap.employeeNameisExist;
                        var returnMsg=msg.returnMsg;
                        //获取对应的父对象以及展示信息的span标签
                        var father=$("#add_emp_name").parent();
                        var  msgSpan=father.children(".help-block");
                        clear_and_newverify(father,msgSpan,returnMsg,!isExist);
                        //将当前静态变量修改
                        add_emp_name_is_ok=!isExist;


                    }
                });
            })
        });
        //判断是否批量选中：
        function checked_all_box(isSelectAll) {
            if (isSelectAll){
                $(".checkbox_select").prop("checked",true);
            }
            else{
                $(".checkbox_select").prop("checked",false);
            }
        }
        //单独校验用户名格式
        function verifyEmployeeNameFormat() {
            //正则表达式
            var re_name=/^[\u2E80-\u9FFFa-zA-Z0-9_-]{3,16}$/;//匹配中文英文下划线
            //进行用户名匹配
            if (!re_name.test(name)){
                //修改警告样式
                var father=$("#add_emp_name").parent();
                var msgSpan=father.children(".help-block");
                var msg="不支持的用户名!应为长度大于3的中文或英文字符";
                clear_and_newverify(father,msgSpan,msg,false);
                //修改用户名样式状态
                add_emp_name_format_is_ok=false;
            }
            else {
                var father=$("#add_emp_name").parent();
                var msgSpan=father.children(".help-block");
                var msg="";
                clear_and_newverify(father,msgSpan,msg,true);
                //修改用户名样式状态
                add_emp_name_format_is_ok=true;
            }
        }
        //校验新增员工信息的方法,返回布尔值
        function verify_add_employee_data() {
            var isSubmit=true;
            //正则表达式
            var re_name=/^[\u2E80-\u9FFFa-zA-Z0-9_-]{3,16}$/;//匹配中文英文下划线
            var re_email=/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
            var  re_age=/[0-9]+/;
            //获取表单数据
            var name = $("#add_emp_name").val();
            var gender = $(".radio-inline input:checked").val();
            var age = $("#add_emp_age").val();
            var email = $("#add_emp_email").val();
            var departmentId=$("#departmentSelectList option:checked").val();
            //进行用户名匹配
            if (!re_name.test(name)){
                //修改警告样式
                var father=$("#add_emp_name").parent();
                var msgSpan=father.children(".help-block");
                var msg="不支持的用户名!应为长度大于3的中文或英文字符";
                clear_and_newverify(father,msgSpan,msg,false);
                isSubmit=false;
                //修改用户名样式状态
                add_emp_name_format_is_ok=false;
            }
            else {
                var father=$("#add_emp_name").parent();
                var msgSpan=father.children(".help-block");
                var msg="";
                clear_and_newverify(father,msgSpan,msg,true);
                //修改用户名样式状态
                add_emp_name_format_is_ok=true;
            }
            //性别判断
            if (gender==null||gender==""){
                var father= $("#gender_div");
                var msgSpan=$("#genderMsg");
                var msg="请选择一个性别！";
                clear_and_newverify(father,msgSpan,msg,false);
                isSubmit=false;
            }
            else {
                var father= $("#gender_div");
                var msgSpan=$("#genderMsg");
                var msg="";
                clear_and_newverify(father,msgSpan,msg,true);
            }
            //年龄判断
            if (!re_age.test(age)||age<=0||age>110){

                var father=$("#add_emp_age").parent();
                var msgSpan=father.children(".help-block");
                var msg="请输入正确格式的年龄！要求大于0小于110";
                clear_and_newverify(father,msgSpan,msg,false);
                isSubmit=false;
            }
            else {
                var father=$("#add_emp_age").parent();
                var msgSpan=father.children(".help-block");
                var msg="请输入正确格式的年龄！要求大于0小于110";
                clear_and_newverify(father,msgSpan,msg,true);
            }
            //邮箱判断
            if (!re_email.test(email)){
                var father= $("#add_emp_email").parent();
                var msgSpan=father.children(".help-block");
                var msg="请输入正确的邮箱格式！";
                clear_and_newverify(father,msgSpan,msg,false);
                isSubmit=false;
            }
            else {
                var father= $("#add_emp_email").parent();
                var msgSpan=father.children(".help-block");
                clear_and_newverify(father,msgSpan,msg,true);
            }
            return isSubmit;
        }
        //清除当前样式并展示目前数据校验状态,传入一个父元素，展示信息的父标签,以及是否校验成功
        function clear_and_newverify(father,msgSpan,msg,isSuccess) {
            //清楚当前样式信息
            father.removeClass("has-success has-error")
            //清楚提示信息
            msgSpan.text("");
            //如果校验成功
            if (isSuccess){
                father.addClass("has-success");
            }
            //校验失败
            else {
                father.addClass("has-error");
                //展示信息
                msgSpan.text(msg);
            }
        }
        //重置模态框中的数据，以及当前状态
        function rest_addEmp_msg() {

            var name = $("#add_emp_name").removeClass("has-success has-error").val("");
            //清除单选框选中
            $(".radio-inline input:checked").removeClass("has-success has-error").prop("checked",false);
            var age = $("#add_emp_age").removeClass("has-success has-error").val("");
            var email = $("#add_emp_email").removeClass("has-success has-error").val("");
            //清除表单数据
            $("#form_add_employee").find("*").removeClass("has-success has-error");
            //清除span标签内容
            $(".help-block").text("");
        }
        //解析员工列表
        function build_employees_table(msg) {
            var employeeList = msg.dataMap.employeePages.list;

            //js中的遍历方法
            $.each(employeeList,function (index,item) {
                // alert(item.employeeName);
                //行的dom对象
                var row=$("<tr></tr>");
                //封装对应的列以及数据
                var checkBox_td=$("<td></td>").append($("<input type='checkbox'>").addClass("checkbox_select").attr("employeeId",item.employeeId));
                var employeeId_td=$("<td></td>").append(item.employeeId);
                var employeeName_td=$("<td></td>").append(item.employeeName);
                var employeeGender_td=$("<td></td>").append(item.employeeGender);
                var employeeAge_td=$("<td></td>").append(item.employeeAge);
                var employeeEmail_td=$("<td></td>").append(item.employeeEmail);
                var employeeDepartmentName_td=$("<td></td>").append(item.department.departmentName);
                var button_td=$("<td></td>");
                //编辑按钮生成,并添加当前员工的id到按钮中作为属性
                var editorSpan=$("<span class='glyphicon glyphicon-pencil' aria-hidden='true'></span>");
                var editorButton=$("<button class='btn btn-primary'></button>").append(editorSpan).append(" 编辑").addClass("update_button").attr("employeeId",item.employeeId);
                //删除按钮生成
                var deleteSpan=$("<span class='glyphicon glyphicon-erase' aria-hidden='true'></span>");
                var deleteButton=$("<button class='btn btn-danger'></button>").append(deleteSpan).append(" 删除").addClass("delete_button").attr("employeeId",item.employeeId);
                button_td.append(editorButton).append(deleteButton);
                //将列对象添加到行中
                row.append(checkBox_td)
                    .append(employeeId_td)
                    .append(employeeName_td)
                    .append(employeeGender_td)
                    .append(employeeAge_td)
                    .append(employeeEmail_td)
                    .append(employeeDepartmentName_td)
                    .append(button_td);
                //将该行对象添加到表格体中
                row.appendTo("#emp_table tbody");

            });
        };
        //解析分页信息
        function page_msg(pageInfo) {
            var pages = pageInfo.pages;
            var total=pageInfo.total;
            $("#pagesTextMsg").append("共"+pages+"页，"+total+"条记录;当前为"+pageInfo.pageNum+"页");
        }
        //导航栏
        function navigate_msg_view(pageInfo,baseUrl) {
            var aClass="getPage";//所有页码a标签的class
            var pageNumViewList=$("#pageNumView");
            //获取分页信息
            var pages = pageInfo.pages;//总页数
            //展示导航栏的页码
            //首页
            var firstPage = $("<li></li>").append($("<a></a>").attr("href",baseUrl+"emps/1").append("首页").addClass(aClass)).appendTo(pageNumViewList);
            <%--<li><a href="${applicationScope.baseUrl}emps/1">首页</a></li>--%>
            <%--<li><a href="${applicationScope.baseUrl}emps/${employeePages.pages}">末页</a></li>--%>
            //上一页展示
            if (pageInfo.hasPreviousPage)
                var prePage=$("<li></li>").append($("<a></a>").attr("href",baseUrl+"emps/"+pageInfo.prePage).append("《").addClass(aClass)).appendTo(pageNumViewList);
            $.each(pageInfo.navigatepageNums,function (index,item) {
                var acticeStr="";//用于标识当前的页码
                if (item==pageInfo.pageNum)
                    acticeStr="active";
                //生成页码展示
                var pageNumLi=$("<li></li>").attr("class",acticeStr);
                //页码的a标签
                var page_a=$("<a></a>").attr("href",baseUrl+"emps/"+item).append(item).addClass(aClass);
                pageNumLi.append(page_a);
                //添加到导航栏中
                pageNumLi.appendTo(pageNumViewList);
            });
            //下一页展示
            if (pageInfo.hasNextPage)
                var nextPage=$("<li></li>").append($("<a></a>").attr("href",baseUrl+"emps/"+pageInfo.nextPage).append("》").addClass(aClass)).appendTo(pageNumViewList);
            //末页展示
            var endPage = $("<li></li>").append($("<a></a>").attr("href",baseUrl+"emps/"+pages).append("末页").addClass(aClass).attr("id","endPage")).appendTo(pageNumViewList);
            //给所有的页码标签添加点击事件
            $(".getPage").click(function () {
                //阻止提交
                //获取该标签的href
                var reqUrl = $(this).attr("href");
                //清理旧信息
                clearOldData();
                //获取分页数据并更新页面
                ajax_getPageData_method(reqUrl);
                return false;
            });
        };

        //给每一个编辑按钮添加点击事件
        function add_editorButton_click() {
            $(".update_button").click(function () {
                //获取对应的修改表单
                var update_form = $("#form_update_employee");
                //清除信息
                update_form[0].reset();//每次展示信息前清除表单信息，除了部门的下拉框以外
                //获取当前员工id
                var employeeId = $(this).attr("employeeId");
                alert("正在操作id为:"+employeeId+"的员工");
                //将当前操作的员工id更新到全局变量中
                thisUpdateEmployeeId=employeeId;
                //拉起模态框
                $("#myUpdateModal").modal({
                    backdrop:"static",
                    keyboard:true
                });
                //给当前模态框中的保存按钮添加点击事件
                add_updateModal_savaEvent();
                //发起请求查询信息，并回显
                $.ajax({
                   url:"${applicationScope.baseUrl}emp/"+employeeId,
                   dataType:"json",
                   type:"get",
                   success:function (msg) {
                      //获取员工信息
                       var employee = msg.dataMap.employee;
                       //展示信息
                       $("#update_emp_name").val(employee.employeeName);
                       //选中对应的性别
                        var radio = update_form.find("input[type='radio'][value='"+employee.employeeGender+"']").attr("checked","checked");
                      //显示年龄
                       $("#update_emp_age").val(employee.employeeAge);
                       //显示邮箱
                       $("#update_emp_email").val(employee.employeeEmail);

                       //选中对应的部门
                       var de = $("#departmentSelectList_update").children("option[value='"+employee.departmentId+"']").attr("selected","selected");
                   }
                });

            });
        }
        //给修改员工的模态框的保存按钮添加点击事件,employeeId:当前编辑员工的id
        function add_updateModal_savaEvent() {
            //获取保存按钮
            $("#sub_update_employee").click(function () {
                //获取表单信息
               var params = $("#form_update_employee").serialize();
               //获取要修改的员工id

               //发起ajax请求
                $.ajax({
                   url:"${applicationScope.baseUrl}emp/"+thisUpdateEmployeeId,
                   data:params ,
                   type:"post",
                   dataType:"json" ,
                   success:function (msg) {
                       //如果修改成功
                      if (msg.stateCode==100){
                          //清理旧数据
                          clearOldData();
                          //更新页面信息
                          ajax_getPageData_method("${applicationScope.baseUrl}emps/"+thisPageNum);
                          //关闭修改的模态框
                          $("#myUpdateModal").modal('hide');
                      }
                      //修改失败
                      else {
                          //显示失败信息
                          alert(msg.resultMsg);
                      }
                   }
                });
            });
        }
        //给单一删除按钮添加事件
        function add_one_delete_button_event() {
            $(".delete_button").click(function () {
                //获取该用户的姓名
                var empName=$(this).parent().parent().children().eq(2).html();
                //弹出确认删除框
                var isDelete = confirm("确定删除["+empName+"]吗?");
                //如果不删除，则直接结束
                if (!isDelete)
                    return;
                //获取要删除员工的id
                var delete_emp_id=$(this).attr("employeeId");
                //发起delete请求删除
                $.ajax({
                    url:"${applicationScope.baseUrl}emp/"+delete_emp_id,
                    type:"post",
                    data:"_method=delete",
                    dataType:"json",
                    success:function (msg) {
                        //刷新页面数据
                        ajax_getPageData_method("${applicationScope.baseUrl}emps/"+thisPageNum);
                    }
                });

            })
        }

    </script>
    <title>员工页面</title>
</head>
<body>
<div class="container">
    <%--        标题--%>
    <div class="row">
        <div class="col-xs-12">
            <h1>员工信息</h1>
        </div>
    </div>
        <div class="row">
            <div class="col-xs-12">每页展示的记录数:
               <input type="text" value="10" id="pageSizeNum"><button id="pageSizeBtn">确定</button>
            </div>
        </div>
    <div class="row">
        <div class="col-md-offset-8">
            <button class="btn btn-info"  id="addemployeeBtn">新增</button>
            <button class="btn btn-danger" id="deleteList_btn">删除</button>
        </div>
    </div>
    <%--    显示表格信息--%>
    <div class="row">
        <div class="col-xs-12">
            <table class="table table-hover" id="emp_table">
<%--                表头--%>
                <thead>
                    <tr >
                        <td><input type="checkbox" id="checkAll"></td>
                        <td>#</td>
                        <td>员工姓名</td>
                        <td>性别</td>
                        <td>年龄</td>
                        <td>邮箱</td>
                        <td>部门</td>
                        <td>操作</td>
                    </tr>
                </thead>
<%--      员工信息         --%>
                <tbody id="employeeView">

                </tbody>

            </table>
        </div>

    </div>
    <%--        显示分页信息--%>
    <div class="row">
        <%--            显示分页文字信息--%>
        <div class="col-xs-6" id="pagesTextMsg">

        </div>
        <%--            显示分页条信息--%>
        <div class="col-xs-6">
            <nav aria-label="Page navigation">
                <ul class="pagination" id="pageNumView">

                </ul>
            </nav>
        </div>
    </div>
</div>



    <!-- Modal 修改用户的模态框-->
    <div class="modal fade" id="myUpdateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myUpdateModalLabel">修改员工</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" id="form_update_employee">
                        <%--    修改为put请求--%>
                        <input type="hidden" name="_method" value="PUT">
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">姓名</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="update_emp_name" name="employeeName">
                                <span  class="help-block"></span>
                            </div>

                        </div>
                        <div>
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">性别</label>
                                <div class="col-sm-10" id="gender_div_update">
                                    <label class="radio-inline">
                                        <input type="radio" name="employeeGender" id="update_inlineRadio1" value="男"> 男
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="employeeGender" id="update_inlineRadio2" value="女"> 女
                                    </label>
                                    <span id="genderMsg_update" class="help-block"></span>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label  class="col-sm-2 control-label">年龄</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="update_emp_age" name="employeeAge">
                                <span  class="help-block"></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">邮箱</label>
                            <div class="col-sm-10">
                                <input type="email" class="form-control" id="update_emp_email" placeholder="email" name="employeeEmail">
                                <span  class="help-block"></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">部门</label>
                            <div class="col-sm-10">
                                <select class="form-control" name="departmentId" id="departmentSelectList_update">

                                </select>
                            </div>
                        </div>


                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="sub_update_employee">保存</button>
                </div>
            </div>
        </div>
    </div>


<%--新增用户模态框--%>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">添加员工</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="form_add_employee">
                    <%--    修改为post请求--%>
                    <input type="hidden" name="_method" value="post">
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">姓名</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="add_emp_name" name="employeeName">
                            <span  class="help-block"></span>
                        </div>

                    </div>
                    <div>
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">性别</label>
                            <div class="col-sm-10" id="gender_div">
                                <label class="radio-inline">
                                    <input type="radio" name="employeeGender" id="inlineRadio1" value="男"> 男
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="employeeGender" id="inlineRadio2" value="女"> 女
                                </label>
                                <span id="genderMsg" class="help-block"></span>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label  class="col-sm-2 control-label">年龄</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="add_emp_age" name="employeeAge">
                            <span  class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">邮箱</label>
                        <div class="col-sm-10">
                            <input type="email" class="form-control" id="add_emp_email" placeholder="email" name="employeeEmail">
                            <span  class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">部门</label>
                        <div class="col-sm-10">
                            <select class="form-control" name="departmentId" id="departmentSelectList">

                            </select>
                        </div>
                    </div>


                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="sub_add_employee">保存</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>