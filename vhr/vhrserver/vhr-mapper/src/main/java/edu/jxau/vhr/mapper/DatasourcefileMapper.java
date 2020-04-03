package edu.jxau.vhr.mapper;

import org.apache.ibatis.annotations.Param;
import edu.jxau.vhr.model.Datasourcefile;

import java.util.List;

/**
 * (Datasourcefile)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-07 18:22:55
 */
public interface DatasourcefileMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Datasourcefile queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Datasourcefile> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     * @return 对象列表
     */
    List<Datasourcefile> getDataSourceByPage(Integer page, Integer size);

    /**
     * 新增数据
     *
     * @param datasourcefile 实例对象
     * @return 影响行数
     */
    int insert(Datasourcefile datasourcefile);

    /**
     * 修改数据
     *
     * @param datasourcefile 实例对象
     * @return 影响行数
     */
    int update(Datasourcefile datasourcefile);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    Long getTotal();

}