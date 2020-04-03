package edu.jxau.vhr.controller.per;

import edu.jxau.vhr.annotation.OpLog;
import edu.jxau.vhr.model.Employee;
import edu.jxau.vhr.model.Employeeec;
import edu.jxau.vhr.model.RespBean;
import edu.jxau.vhr.model.RespPageBean;
import edu.jxau.vhr.service.EmployeeService;
import edu.jxau.vhr.service.PerEcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/perec")
public class PerEcController {

    @Autowired
    private PerEcService perEcService;

    @Autowired
    private EmployeeService employeeService;

    @OpLog("分页获取员工奖罚信息")
    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size, Employeeec employeeec, @RequestParam(defaultValue = "") String name) {
        return perEcService.getEmployeeEcByPage(page, size, employeeec, name);
    }

    @OpLog("添加员工奖罚")
    @PostMapping("/")
    public RespBean addPerEc(@RequestBody Employeeec employeeec) {
        if (perEcService.addPerec(employeeec) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @OpLog("修改员工奖罚")
    @PutMapping("/")
    public RespBean updatePerEc(@RequestBody Employeeec employeeec) {
        if (perEcService.updatePerec(employeeec) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @OpLog("根据id获取员工信息")
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id){
        return employeeService.getEmployeeById(id);
    }

    @OpLog("删除员工奖罚信息")
    @DeleteMapping("/{id}")
    public RespBean deleteEmpecByEid(@PathVariable Integer id) {
        if (perEcService.deleteEmpecByEid(id) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

}
