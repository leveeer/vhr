package edu.jxau.vhr.controller.system;

import edu.jxau.vhr.annotation.OpLog;
import edu.jxau.vhr.model.Datasourcefile;
import edu.jxau.vhr.model.RespBean;
import edu.jxau.vhr.model.RespPageBean;
import edu.jxau.vhr.service.SysDataService;
import edu.jxau.vhr.utils.BackupUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/employee/sysdata")
public class SysDataController {

    @Autowired
    private SysDataService sysDataService;

    @OpLog("分页获取数据库备份")
    @GetMapping("/")
    public RespPageBean getDataSourceByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return sysDataService.getDataSourceByPage(page, size);
    }

    @OpLog("备份数据库")
    @PostMapping("/")
    public RespBean addBackUp(@RequestBody Datasourcefile dataSourceFile) {
        System.out.println(dataSourceFile);
        try {
            BackupUtils.doBackup("root","root","vhr",dataSourceFile.getFileurl(),dataSourceFile.getFilename());
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("备份失败,请检查路径是否存在!");
        }
        dataSourceFile.setFileurl(dataSourceFile.getFileurl() + "\\" + dataSourceFile.getFilename());
        if (sysDataService.addDatasourcefile(dataSourceFile) == 1) {
            return RespBean.ok("备份成功!");
        }
        return RespBean.error("备份失败");
    }

    @OpLog("恢复数据库")
    @PostMapping("/doRestore")
    public RespBean doRestore(@RequestParam String fileurl ){
        try {
            BackupUtils.dbRestore("root","root","vhr",fileurl);
        } catch (IOException e) {
            e.printStackTrace();
            return RespBean.error("恢复失败，请检查文件是否存在！");
        }

        return RespBean.ok("恢复成功");
    }

}
