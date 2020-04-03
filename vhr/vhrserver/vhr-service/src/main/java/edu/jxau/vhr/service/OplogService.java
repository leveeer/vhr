package edu.jxau.vhr.service;

import edu.jxau.vhr.mapper.OplogMapper;
import edu.jxau.vhr.model.Employeeec;
import edu.jxau.vhr.model.Oplog;
import edu.jxau.vhr.model.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OplogService {

    @Autowired
    OplogMapper oplogMapper;
    public void insertLog(Oplog oplog){
        oplogMapper.insertSelective(oplog);
    }

    public RespPageBean getAllLogsByPage(Integer page, Integer size) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employeeec> data = oplogMapper.getAllLogsByPage(page, size);
        Long total = oplogMapper.getTotal();
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;

    }
}
