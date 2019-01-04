package com.yunche.controller;

import com.yunche.dao.DepartmentDao;
import com.yunche.dao.EmployeeDao;
import com.yunche.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * @ClassName: EmployeeController
 * @Description:
 * @author: yunche
 * @date: 2019/01/03
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    DepartmentDao departmentDao;
    @GetMapping("/emps")
    public String list(Model model) {
        model.addAttribute("emps", employeeDao.getAll());
        return "emps/list";
    }


    /**
     * 来到添加页面
     *
     * @return
     */
    @GetMapping("/emp")
    public String toAddPage(Model model) {
        model.addAttribute("departments", departmentDao.getDepartments());
        return "emps/add";
    }

    /**
     * 添加员工
     * SpringMVC自动将请求参数和入参对象的属性进行一一绑定；
     * 要求请求参数的名字和javaBean入参的对象里面的属性名是一样的
     * @return
     */
    @PostMapping("/emp")
    public String addEmployee(Employee employee) {
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    /**
     * 来到修改页面
     *
     * @return
     */
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id, Model model) {
        Employee employee = employeeDao.get(id);
        model.addAttribute("emp", employee);
        model.addAttribute("departments", departmentDao.getDepartments());
        return "emps/add";
    }

    /**
     * 修改员工
     * @param employee
     * @return
     */
    @PutMapping("/emp")
    public String editEmployee(Employee employee) {
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    /**
     * 删除员工
     * @param id
     * @return
     */
    @DeleteMapping("/emp/{id}")
    public String removeEmployee(@PathVariable("id") Integer id) {
        employeeDao.delete(id);
        return "redirect:/emps";
    }
}
