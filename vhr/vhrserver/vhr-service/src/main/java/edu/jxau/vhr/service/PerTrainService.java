package edu.jxau.vhr.service;

import edu.jxau.vhr.mapper.EmployeetrainMapper;
import edu.jxau.vhr.model.Employeetrain;
import edu.jxau.vhr.model.RespPageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerTrainService {
    @Autowired
    EmployeetrainMapper employeetrainMapper;
    public final static Logger logger = LoggerFactory.getLogger(PerEcService.class);

    public RespPageBean getEmployeetrainByPage(Integer page, Integer size, Employeetrain employeetrain, String name) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employeetrain> data = employeetrainMapper.getEmployeetrainByPage(page, size, employeetrain, name);
        Long total = employeetrainMapper.getTotal(employeetrain, name);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public int addPerTrain(Employeetrain employeetrain) {

        return employeetrainMapper.insertSelective(employeetrain);
    }


    public int deletePerTrainByEid(Integer id) {
        return employeetrainMapper.deleteByPrimaryKey(id);

    }
}
