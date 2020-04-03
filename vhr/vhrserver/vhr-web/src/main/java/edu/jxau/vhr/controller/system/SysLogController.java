package edu.jxau.vhr.controller.system;

import edu.jxau.vhr.model.RespPageBean;
import edu.jxau.vhr.service.OplogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee/syslog")
public class SysLogController {
    @Autowired
    private OplogService oplogService;

    @GetMapping("/")
    public RespPageBean getAllLogsByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return oplogService.getAllLogsByPage(page, size);
    }
}
