package edu.jxau.vhr.mapper;

import edu.jxau.vhr.model.Attendance;

import java.util.List;

public interface AttendanceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Attendance record);

    int insertSelective(Attendance record);

    Attendance selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Attendance record);

    int updateByPrimaryKey(Attendance record);

    List<Attendance> getAttendByPage(Integer page, Integer size, Attendance attendance, String name);

    Long getTotal(Attendance attendance, String name);
}