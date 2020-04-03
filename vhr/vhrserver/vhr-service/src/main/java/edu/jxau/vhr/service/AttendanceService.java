package edu.jxau.vhr.service;

import edu.jxau.vhr.mapper.AttendanceMapper;
import edu.jxau.vhr.model.Attendance;
import edu.jxau.vhr.model.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    AttendanceMapper attendanceMapper;

    public int addClockRecord(Attendance attendance) {
        return attendanceMapper.insertSelective(attendance);
    }

    public RespPageBean getAttendanceByPage(Integer page, Integer size, Attendance attendance, String name) {

        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Attendance> data = attendanceMapper.getAttendByPage(page, size, attendance, name);
        Long total = attendanceMapper.getTotal(attendance, name);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }
}
