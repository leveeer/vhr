package edu.jxau.vhr.mapper;

import edu.jxau.vhr.model.Employeeec;
import edu.jxau.vhr.model.Oplog;

import java.util.List;

public interface OplogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Oplog record);

    int insertSelective(Oplog record);

    Oplog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Oplog record);

    int updateByPrimaryKey(Oplog record);

    List<Employeeec> getAllLogsByPage(Integer page, Integer size);

    Long getTotal();
}