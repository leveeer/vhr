package edu.jxau.vhr.controller.basic;

import edu.jxau.vhr.annotation.OpLog;
import edu.jxau.vhr.model.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {
    @OpLog("登录系统")
    @GetMapping("/login")
    public RespBean login() {
        return RespBean.error("尚未登录，请登录!");
    }
}
