package edu.jxau.vhr.mapper;

import edu.jxau.vhr.model.Employeeec;

import java.util.List;

public interface EmployeeecMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employeeec record);

    int insertSelective(Employeeec record);

    Employeeec selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employeeec record);

    int updateByPrimaryKey(Employeeec record);

    List<Employeeec> getEmployeeecByPage(Integer page, Integer size, Employeeec employeeec, String name);

    Long getTotal(Employeeec employeeec, String name);
}