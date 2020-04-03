package edu.jxau.vhr.controller.system;


import edu.jxau.vhr.model.Server;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("employee/server")
public class ServerController {

    @GetMapping("/")
    public List server() throws Exception {
        Server server = new Server();
        List<Double> data = server.getDashBoardData();
        return data;
    }

    @GetMapping("/getAllInfo")
    public Server getAllInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return server;
    }
}
