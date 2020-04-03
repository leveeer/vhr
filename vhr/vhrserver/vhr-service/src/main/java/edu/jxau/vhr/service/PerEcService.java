package edu.jxau.vhr.service;

import edu.jxau.vhr.mapper.EmployeeecMapper;
import edu.jxau.vhr.model.Employeeec;
import edu.jxau.vhr.model.RespPageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class PerEcService {

    @Autowired
    EmployeeecMapper employeeecMapper;
    public final static Logger logger = LoggerFactory.getLogger(PerEcService.class);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    DecimalFormat decimalFormat = new DecimalFormat("##.00");

    public RespPageBean getEmployeeEcByPage(Integer page, Integer size, Employeeec employeeec, String name) {
            if (page != null && size != null) {
                page = (page - 1) * size;
            }
            List<Employeeec> data = employeeecMapper.getEmployeeecByPage(page, size, employeeec, name);
            Long total = employeeecMapper.getTotal(employeeec, name);
            RespPageBean bean = new RespPageBean();
            bean.setData(data);
            bean.setTotal(total);
            return bean;
    }

    public int addPerec(Employeeec employeeec) {
        int result = employeeecMapper.insertSelective(employeeec);
        return result;
    }


    public int updatePerec(Employeeec employeeec) {
        return employeeecMapper.updateByPrimaryKeySelective(employeeec);
    }

    public int deleteEmpecByEid(Integer id) {
        return employeeecMapper.deleteByPrimaryKey(id);
    }
}
