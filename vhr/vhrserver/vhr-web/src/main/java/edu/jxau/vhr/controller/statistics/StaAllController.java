package edu.jxau.vhr.controller.statistics;

import edu.jxau.vhr.annotation.OpLog;
import edu.jxau.vhr.model.Department;
import edu.jxau.vhr.model.Employee;
import edu.jxau.vhr.service.DepartmentService;
import edu.jxau.vhr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee/statistics")
public class StaAllController {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    EmployeeService employeeService;


    @OpLog("统计各部门人数")
    @GetMapping("/deps")
    public List<Department> getDnameAndNum() {
        return departmentService.getDnameAndNum();
    }

    @OpLog("统计各学历人数")
    @GetMapping("/degree")
    public List<Employee> getDegreesAndNum() {
        return employeeService.getDegreesAndNum();
    }
}
