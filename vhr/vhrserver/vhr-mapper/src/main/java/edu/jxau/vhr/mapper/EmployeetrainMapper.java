package edu.jxau.vhr.mapper;

import edu.jxau.vhr.model.Employeetrain;

import java.util.List;

public interface EmployeetrainMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employeetrain record);

    int insertSelective(Employeetrain record);

    Employeetrain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employeetrain record);

    int updateByPrimaryKey(Employeetrain record);

    List<Employeetrain> getEmployeetrainByPage(Integer page, Integer size, Employeetrain employeetrain, String name);

    Long getTotal(Employeetrain employeetrain, String name);
}