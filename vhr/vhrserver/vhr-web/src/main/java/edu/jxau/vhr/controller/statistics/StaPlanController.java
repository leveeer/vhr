package edu.jxau.vhr.controller.statistics;

import edu.jxau.vhr.annotation.OpLog;
import edu.jxau.vhr.model.Plan;
import edu.jxau.vhr.model.RespBean;
import edu.jxau.vhr.model.RespPageBean;
import edu.jxau.vhr.service.StaPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/staplans")
public class StaPlanController {

    @Autowired
    private StaPlanService staPlanService;

    @OpLog("获取所有计划")
    @GetMapping("/")
    public RespPageBean getAllPlan() {
        return staPlanService.getAllPlan();
    }


    @OpLog("添加计划")
    @PostMapping("/")
    public RespBean addPlan(@RequestBody Plan plan) {
        if (staPlanService.addPlan(plan) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }
}
