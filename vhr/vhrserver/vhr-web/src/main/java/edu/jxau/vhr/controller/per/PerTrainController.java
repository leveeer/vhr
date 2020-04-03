package edu.jxau.vhr.controller.per;

import edu.jxau.vhr.model.Employee;
import edu.jxau.vhr.model.Employeetrain;
import edu.jxau.vhr.model.RespBean;
import edu.jxau.vhr.model.RespPageBean;
import edu.jxau.vhr.service.EmployeeService;
import edu.jxau.vhr.service.PerTrainService;
import edu.jxau.vhr.annotation.OpLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/pertrain")
public class PerTrainController {
    @Autowired
    private PerTrainService perTrainService;

    @Autowired
    private EmployeeService employeeService;

    @OpLog("分页获取员工培训信息")
    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size, Employeetrain employeetrain, @RequestParam(defaultValue = "") String name) {
        return perTrainService.getEmployeetrainByPage(page, size, employeetrain, name);
    }

    @OpLog("根据id获取员工信息")
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id){
        return employeeService.getEmployeeById(id);
    }

    @OpLog("添加员工培训")
    @PostMapping("/")
    public RespBean addPerTrain(@RequestBody Employeetrain employeetrain) {
        if (perTrainService.addPerTrain(employeetrain) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @OpLog("删除员工培训")
    @DeleteMapping("/{id}")
    public RespBean deletePerTrainByEid(@PathVariable Integer id) {
        if (perTrainService.deletePerTrainByEid(id) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
}
