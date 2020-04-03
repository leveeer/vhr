package edu.jxau.vhr.service;

import edu.jxau.vhr.mapper.PlanMapper;
import edu.jxau.vhr.model.Plan;
import edu.jxau.vhr.model.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaPlanService {

    @Autowired
    PlanMapper planMapper;

    public RespPageBean getAllPlan() {
        List<Plan> data = planMapper.queryAll();
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        return bean;
    }

    public int addPlan(Plan plan) {
        return planMapper.insert(plan);
    }
}
