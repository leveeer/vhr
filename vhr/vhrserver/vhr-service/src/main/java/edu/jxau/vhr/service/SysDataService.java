package edu.jxau.vhr.service;

import edu.jxau.vhr.mapper.DatasourcefileMapper;
import edu.jxau.vhr.model.Datasourcefile;
import edu.jxau.vhr.model.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDataService {

    @Autowired
    DatasourcefileMapper datasourcefileMapper;

    public RespPageBean getDataSourceByPage(Integer page, Integer size) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Datasourcefile> data = datasourcefileMapper.getDataSourceByPage(page, size);
        Long total = datasourcefileMapper.getTotal();
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public int addDatasourcefile(Datasourcefile datasourcefile) {
        return datasourcefileMapper.insert(datasourcefile);
    }
}
